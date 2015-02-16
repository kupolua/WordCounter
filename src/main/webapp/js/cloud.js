var fill = d3.scale.category20b();

var w = 960,
    h = 600;

var words = [],
    max,
    scale = 1,
    complete = 0,
    keyword = "",
    tags,
    fontSize,
    maxLength = 30,
    fetcher,
    statusText = d3.select("#status");

var layout = d3.layout.cloud()
    .timeInterval(10)
    .size([w, h])
    .fontSize(function(d) { return fontSize(+d.value); })
    .text(function(d) { return d.key; })
    .on("word", progress)
    .on("end", draw);

var svg = d3.select("#vis").append("svg")
    .attr("width", w)
    .attr("height", h);

var background = svg.append("g"),
    vis = svg.append("g")
    .attr("transform", "translate(" + [w >> 1, h >> 1] + ")");

var form = d3.select("#form")
    .on("submit", function() {
        parseText("Просто введiть Просто посилання чи текст у посилання форму Просто нижче, натиснiть “Порахувати посилання слова”, і результат не змусить себе чекати.");
      d3.event.preventDefault();
    });

// From Jonathan Feinberg's cue.language, see lib/cue.language/license.txt.
var stopWords = /^(i|me|my|myself|we|us|our|ours|ourselves|you|your|yours|yourself|yourselves|he|him|his|himself|she|her|hers|herself|it|its|itself|they|them|their|theirs|themselves|what|which|who|whom|whose|this|that|these|those|am|is|are|was|were|be|been|being|have|has|had|having|do|does|did|doing|will|would|should|can|could|ought|i'm|you're|he's|she's|it's|we're|they're|i've|you've|we've|they've|i'd|you'd|he'd|she'd|we'd|they'd|i'll|you'll|he'll|she'll|we'll|they'll|isn't|aren't|wasn't|weren't|hasn't|haven't|hadn't|doesn't|don't|didn't|won't|wouldn't|shan't|shouldn't|can't|cannot|couldn't|mustn't|let's|that's|who's|what's|here's|there's|when's|where's|why's|how's|a|an|the|and|but|if|or|because|as|until|while|of|at|by|for|with|about|against|between|into|through|during|before|after|above|below|to|from|up|upon|down|in|out|on|off|over|under|again|further|then|once|here|there|when|where|why|how|all|any|both|each|few|more|most|other|some|such|no|nor|not|only|own|same|so|than|too|very|say|says|said|shall)$/,
    punctuation = new RegExp("[" + unicodePunctuationRe + "]", "g"),
    wordSeparators = /[\s\u3031-\u3035\u309b\u309c\u30a0\u30fc\uff70]+/g,
    discard = /^(@|https?:|\/\/)/,
    htmlTags = /(<[^>]*?>|<script.*?<\/script>|<style.*?<\/style>|<head.*?><\/head>)/g,
    matchTwitter = /^https?:\/\/([^\.]*\.)?twitter\.com/;
//    hashchange("one two two three three three four four four four five five five five five");

function parseText(text) {
  tags = {};
  var cases = {};
  text.split(wordSeparators).forEach(function(word) {
    if (discard.test(word)) return;
    word = word.replace(punctuation, "");
    word = word.substr(0, maxLength);
    cases[word] = word;
    tags[word = word] = (tags[word] || 0) + 1;
  });
  tags = d3.entries(tags).sort(function(a, b) { return b.value - a.value; });
  tags.forEach(function(d) { d.key = cases[d.key]; });
  generate();
}

function generate() {
  layout.font("Impact").spiral("archimedean");

  fontSize = d3.scale["linear"]().range([10, 100]);
  if (tags.length) fontSize.domain([+tags[tags.length - 1].value || 1, +tags[0].value]);
  complete = 0;
  statusText.style("display", null);
  words = [];
  layout.stop().words(tags.slice(0, max = 250)).start();
}

function progress(d) {
  statusText.text(++complete + "/" + max);
}

function draw(data, bounds) {
  statusText.style("display", "none");
  scale = bounds ? Math.min(
      w / Math.abs(bounds[1].x - w / 2),
      w / Math.abs(bounds[0].x - w / 2),
      h / Math.abs(bounds[1].y - h / 2),
      h / Math.abs(bounds[0].y - h / 2)) / 2 : 1;
  words = data;
  var text = vis.selectAll("text")
      .data(words, function(d) { return d.text.toLowerCase(); });
  text.transition()
      .duration(1000)
      .attr("transform", function(d) { return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")"; })
      .style("font-size", function(d) { return d.size + "px"; });
  text.enter().append("text")
      .attr("text-anchor", "middle")
      .attr("transform", function(d) { return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")"; })
      .style("font-size", function(d) { return d.size + "px"; })
      .on("click", function(d) {
        load(d.text);
      })
      .style("opacity", 1e-6)
    .transition()
      .duration(1000)
      .style("opacity", 1);
  text.style("font-family", function(d) { return d.font; })
      .style("fill", function(d) { return fill(d.text.toLowerCase()); })
      .text(function(d) { return d.text; });
  var exitGroup = background.append("g")
      .attr("transform", vis.attr("transform"));
  var exitGroupNode = exitGroup.node();
  text.exit().each(function() {
    exitGroupNode.appendChild(this);
  });
  exitGroup.transition()
      .duration(1000)
      .style("opacity", 1e-6)
      .remove();
  vis.transition()
      .delay(1000)
      .duration(750)
      .attr("transform", "translate(" + [w >> 1, h >> 1] + ")scale(" + scale + ")");
}
