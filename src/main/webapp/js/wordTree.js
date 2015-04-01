var width = 1200;
var height = 700;
$(document).ready(function() {
    var cluster = d3.layout.cluster()
    .size([height, width-900]);
//        .cluster.nodeSize([100, 300]);

    var diagonal = d3.svg.diagonal()
        .projection (function(d) { return [d.y, d.x];});

    var svg = d3.select("#chart").append("svg")
        .attr("width",width)
        .attr("height",height)
        .append("g")
        .attr("transform","translate(250,0)");

//d3.json("dendrogram01.json", function(error, root){   
	var root = {"name": "http://www.yahoo.com http://edition.cnn.com",root: "root","children": [
	    {"name": "http://www.yahoo.com","children": [
            {"name": "https://www.yahoo.com/makers"},
            {"name": "https://www.yahoo.com/style/dancing-with-the-stars-week-c1427831092579.html"},
            {"name": "https://www.yahoo.com/travel"},
            {"name": "https://www.yahoo.com/travel/want-to-retire-in-your-30s-and-travel-the-world-115039314527.html"},
            {"name": "https://www.yahoo.com/style"},
            {"name": "https://www.yahoo.com/movies"},
            {"name": "https://www.yahoo.com/tech/s/access-secret-hidden-menus-iphone-android-phone-140654496.html"},
            {"name": "https://www.yahoo.com/makers"},
            {"name": "https://www.yahoo.com/style/dancing-with-the-stars-week-c1427831092579.html"},
            {"name": "https://www.yahoo.com/travel"},
            {"name": "https://www.yahoo.com/travel/want-to-retire-in-your-30s-and-travel-the-world-115039314527.html"},
            {"name": "https://www.yahoo.com/style"},
            {"name": "https://www.yahoo.com/style/nikki-reed-on-her-most-cherished-clothes-why-115093660963.html"},
            {"name": "https://www.yahoo.com/tech"},
            {"name": "https://www.yahoo.com/style/nikki-reed-on-her-most-cherished-clothes-why-115093660963.html"},
            {"name": "https://www.yahoo.com/tech"},
            { "name": "https://www.yahoo.com/food"}
        ]},
        {"name": "http://edition.cnn.com","children": [
            {"name": "http://edition.cnn.com/tv"},
            {"name": "http://edition.cnn.com/europe/index.html"},
            {"name": "http://edition.cnn.com/2015/04/01/travel/airport-codes/index.html"},
            {"name": "http://edition.cnn.com/us/index.html"},
            {"name": "http://edition.cnn.com/africa/index.html"},
            {"name": "http://edition.cnn.com/tech/index.html"},
            {"name": "http://edition.cnn.com/videos/business/2015/03/30/wbt-intv-croft-nigeria-elections-economy.cnn"},
            {"name": "http://edition.cnn.com/videos/us/2015/03/31/april-fools-day-history-boyette-ls.cnn"},
            {"name": "http://edition.cnn.com/americas/index.html"},
            {"name": "http://edition.cnn.com/tv"},
            {"name": "http://edition.cnn.com/europe/index.html"},
            {"name": "http://edition.cnn.com/2015/04/01/travel/airport-codes/index.html"},
            {"name": "http://edition.cnn.com/us/index.html"},
            {"name": "http://edition.cnn.com/africa/index.html"},
            {"name": "http://edition.cnn.com/tech/index.html"},
            {"name": "http://edition.cnn.com/videos/business/2015/03/30/wbt-intv-croft-nigeria-elections-economy.cnn"},
            {"name": "http://edition.cnn.com/videos/us/2015/03/31/april-fools-day-history-boyette-ls.cnn"},
            {"name": "http://edition.cnn.com/americas/index.html"},
            { "name": "https://www.yahoo.com/food"}
        ]}
    ]};

   var nodes = cluster.nodes(root);    
   var links = cluster.links(nodes);

   var link = svg.selectAll(".link")       
      .data(links)       
      .enter().append("path")       
      .attr("class","link")       
      .attr("d", diagonal);

   var node = svg.selectAll(".node")       
      .data(nodes)       
      .enter().append("g")       
      .attr("class","node")       
      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

   node.append("circle")       
      .attr("r", 4.5);

    var rootLinks = 0;
   var tspan = node.append("text")
      .attr("dx", function(d) { return d.children ? -8 : 8; })
      .attr("dy", 3)
      .style("text-anchor", function(d) { return d.children ? "end" : "start"; })
      .text(function(d){
               if (d.name === "http://edition.cnn.com" || d.name === "http://www.yahoo.com") {
                   return "["+ d.name + "]"
               } else {
                   if(d.root) {
                       rootLinks = d.name.split(' ');
                   }
                   return d.root ? "" : d.name;
               }
       });
    var rootTspanCount = tspan.selectAll("text")
            .attr("root", function(d) {
                return d.name.split(' ');
            });
    var dx = -19;
    var dy = -2;
    for(var i = 0; i < rootLinks.length; i++) {
        tspan.append("tspan")
                .attr("dx", dx)
                .attr("dy", dy)
                .text(function(d){
                    return d.root ? rootLinks[i] : "";
                });
        dx += -100;
        dy += 18;
    }
});