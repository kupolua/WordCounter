var heapKVArray;
var sitesKVArray;
var linksKVArray;
google.load("jquery", "1");
google.setOnLoadCallback(function () {
    initialize().then(
        function (control) {
            doTheTreeViz(control);
        }
    );
});

function drawSelectedDiagram(selectedDiagram) {
    $("#chart").html("");
    initialize(selectedDiagram).then(
        function (control) {
            doTheTreeViz(control);
        }
    );
}

function doTheTreeViz(control) {

    var svg = control.svg;

    var force = control.force;
    force.nodes(control.nodes)
        .links(control.links)
        .start();

    // Update the links
    var link = svg.selectAll("line.link")
        .data(control.links, function (d) {
            return d.key;
        });

    // Enter any new links
    var linkEnter = link.enter().insert("svg:line", ".node")
        .attr("class", "link")
        .attr("x1", function (d) {
            return d.source.x;
        })
        .attr("y1", function (d) {
            return d.source.y;
        })
        .attr("x2", function (d) {
            return d.target.x;
        })
        .attr("y2", function (d) {
            return d.target.y;
        })
        .append("svg:title")
        .text(function (d) {
            return d.target.name + ":" + d.source.name;
        });

    // Exit any old links.
    link.exit().remove();


    // Update the nodes
    var node = svg.selectAll("g.node")
        .data(control.nodes, function (d) {
            return d.key;
        });

    node.select("circle")
        .style("fill", function (d) {
            return getColor(d);
        })
        .attr("r", function (d) {
            return getRadius(d);
        });

    node.selectAll("text")
        .text(function (d) {
            var isShowed = control.data.isShowed;
            return getTotalWordWeight(d, isShowed, control);
        });
        var controlSource = control.source;

    // Enter any new nodes.
    var nodeEnter = node.enter()
        .append("svg:g")
        .attr("class", "node")
        .attr("transform", function (d) {
            return "translate(" + d.x + "," + d.y + ")";
        })
        .on("dblclick", function (d) {
            control.nodeClickInProgress = false;
            if (d.url)window.open(d.url);
        })
        .on("click", function (d) {
            // this is a hack so that click doesnt fire on the1st click of a dblclick
            //if(d.pages) {                                       //single click on pages is off because todo: need a fix
                if (!control.nodeClickInProgress) {
                    control.nodeClickInProgress = true;
                    setTimeout(function () {
                        if (control.nodeClickInProgress) {
                            control.nodeClickInProgress = false;
                            if (control.options.nodeFocus) {
                                d.isCurrentlyFocused = !d.isCurrentlyFocused;
                                doTheTreeViz(makeFilteredData(control));
                            }
                        }
                    }, control.clickHack);
                }
            //}
        })
        .call(force.drag);

    nodeEnter
        .append("svg:circle")
        .attr("r", function (d) {
            return getRadius(d);
        })
        .style("fill", function (d) {
            return getColor(d);
        })
        .on("mouseover", function (d) {
            // enhance all the links that end here
            enhanceNode(d);
        })

        .on("mouseout", function (d) {
            resetNode(d);

        })
        .append("svg:title")
        .text(function (d) {
            return d[control.options.nodeLabel];
        });

    function enhanceNode(selectedNode) {
        var i = 0;
        var l = 0;
        link.filter(function (d) {
            var stroke;
            var strokeSource = d.source.key == selectedNode.key;
            var strokeTarget = d.target.key == selectedNode.key;
            if(strokeSource) {
                stroke = strokeSource;
                var k = i;
                ++l;
            }
            if(strokeTarget) {
                stroke = strokeTarget;
                var k = i;
                ++l;
            }

            i++;
            return stroke;
        })
            .style("stroke", control.options.routeFocusStroke)
            .style("stroke-width", control.options.routeFocusStrokeWidth);

        if (text) {
            text.filter(function (d) {
                return areWeConnected(selectedNode, d);
            })
                .style("fill", control.options.routeFocusStroke);
        }
    }

    function areWeConnected(node1, node2) {
        for (var i = 0; i < control.data.links.length; i++) {
            var lnk = control.data.links[i];
            if ((lnk.source.key === node1.key && lnk.target.key === node2.key) ||
                (lnk.source.key === node2.key && lnk.target.key === node1.key)) return lnk;
        }
        return null;
    }

    function resetNode(selectedNode) {
        link.style("stroke", control.options.routeStroke)
            .style("stroke-width", control.options.routeStrokeWidth);
        if (text) {
            text.style("fill", control.options.routeStroke);
        }
    }

    if (control.options.nodeLabel) {
        // text is done once for shadow as well as for text
        var isShowed = control.data.isShowed;
        var textShadow = nodeEnter.append("svg:text")
            .attr("x", function (d) {
                var x = (d.right || !d.fixed) ?
                    control.options.labelOffset :
                    (-d.dim.width - control.options.labelOffset);
                return x;
            })
            .attr("dy", ".31em")
            .attr("class", "shadow")
            .attr("key", function (d) {
                return getTotalWordWeight(d, isShowed);
            })
            .attr("text-anchor", function (d) {
                return !d.right ? 'start' : 'start';
            })
            .style("font-size", control.options.labelFontSize + "px")
            .text(function (d) {
                return getTotalWordWeight(d, isShowed);
            });

        var text = nodeEnter.append("svg:text")
            .attr("x", function (d) {
                var x = (d.right || !d.fixed) ?
                    control.options.labelOffset :
                    (-d.dim.width - control.options.labelOffset);
                return x;
            })
            .attr("dy", ".35em")
            .attr("class", "text")
            .attr("key", function (d) {
                return getTotalWordWeight(d, isShowed);
            })
            .attr("text-anchor", function (d) {
                return !d.right ? 'start' : 'start';
            })
            .style("font-size", control.options.labelFontSize + "px")
            .text(function (d) {
                return getTotalWordWeight(d, isShowed);
            })

            .on("mouseover", function (d) {
                // enhance all the links that end here
                enhanceNode(d);
                if (!d.pages && d.key.length >= 30) {  //todo: move 30 to global var?
                    if (d.right ) {
                        d3.select(this)
                            .attr("x", function (d) {
                                var keyDim = getKeyDim(control.scratch, d.key);
                                var x = (d.dim.width - keyDim.width) + 20; //todo: remove hardfix (20)
                                //var x = d.dim.width - (d.key.length * 8);
                                return x;
                            })
                            .style('fill', control.options.routeFocusStroke)
                            .text(function (d) {
                                return d.key;
                            });
                    } else {
                        d3.select(this)
                            .style('fill', control.options.routeFocusStroke)
                            .text(function (d) {
                                return d.key;
                            });
                    }
                }
            })

            .on("mouseout", function (d) {
                resetNode(d);
                if (!d.pages) {
                    if (d.right) {
                        d3.select(this)
                            .attr("x", function (d) {
                                var x = control.options.labelOffset;
                                return x;
                            });
                    }
                    if (control.data.isShowed) {
                        d3.select(this)
                            .text(function (d) {
                                var isShowed = true;
                                return getTotalWordWeight(d, isShowed);
                            });
                    } else {
                        d3.select(this)
                            .text(function (d) {
                                return d.name;
                            });
                    }
                }
            });
    }

    // Exit any old nodes.
    node.exit().remove();
    control.link = svg.selectAll("line.link");
    control.node = svg.selectAll("g.node");
    force.on("tick", tick);

    if (control.options.linkName) {
        link.append("title")
            .text(function (d) {
                return d[control.options.linkName];
            });
    }

    function tick() {
        var maxLeftColumSize = 0;
        link.attr("x1", function (d) {
            if(!d.source.right) {
                maxLeftColumSize = maxLeftColumSize < d.source.px ? d.source.px : maxLeftColumSize;
            }
            return d.source.x;
        })
            .attr("y1", function (d) {
                return d.source.y;
            })
            .attr("x2", function (d) {
                return d.target.x;
            })
            .attr("y2", function (d) {
                return d.target.y;
            });
        node.attr("transform", function (d) {
            if(d.px < maxLeftColumSize && !d.fixed) {
                d.px -= 100;
            }
            return "translate(" + d.x + "," + d.y + ")";
        });

    }

    function getRadius(d) {
        return makeRadius(control, d);
    }

    function getColor(d) {
        return control.options.nodeFocus && d.isCurrentlyFocused ? control.options.nodeFocusColor : control.color(d.group);
    }
}

function makeRadius(control, d) {
    var r = control.options.radius * (control.options.nodeResize ? Math.sqrt(d[control.options.nodeResize]) / Math.PI : 1);
    return control.options.nodeFocus && d.isCurrentlyFocused ? control.options.nodeFocusRadius : r;
}

function makeFilteredData(control, selectedNode) {
    // we'll keep only the data where filterned nodes are the source or target
    var newNodes = [];
    var newLinks = [];

    for (var i = 0; i < control.data.links.length; i++) {
        var link = control.data.links[i];
        if (link.target.isCurrentlyFocused) { //todo remove duplicate code to function()
            link.target.name = link.target.key;
            link.source.currentWord = link.target.key;
//            link.source.isShowed = 1;
            link.source.isTarget = true;
            control.data.isShowed = true;
            newLinks.push(link);
            addNodeIfNotThere(link.source, newNodes);
            addNodeIfNotThere(link.target, newNodes);
        }
        if (link.source.isCurrentlyFocused) { //todo remove duplicate code to function()
            link.target.name = link.target.key;
            link.source.currentWord = link.target.key;
//            link.source.isShowed = 1;
            link.source.isSource = true;
            link.source.isShowWeight = true;
            control.data.isShowed = true;
            newLinks.push(link);
            addNodeIfNotThere(link.source, newNodes);
            addNodeIfNotThere(link.target, newNodes);
        }
    }

    // if none are selected reinstate the whole dataset
    if (newNodes.length > 0) {
        control.links = newLinks;
        control.nodes = newNodes;
    }
    else {
        control.nodes = control.data.nodes;
        control.links = control.data.links;
        control.data.isShowed = false;
    }
    return control;

    function addNodeIfNotThere(node, nodes) {
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].key == node.key) return i;
        }
        return nodes.push(node) - 1;
    }
}

function getTotalWordWeight(d, isShowed, control) {
    var word = d.name;
    var isShowWeight = d.isShowWeight;
    var currentWord = d.currentWord;
    var text;
    var totalWordWeight;

    if(isShowWeight) {
        if(d.isSource){
            text = d.name;
        } else {
            text = d.name + " (" + d.wordWeight + ")";
        }
    } else {
        if(isShowed) {
            for (var index = 0; index < sitesKVArray.length; index++) {
                if (sitesKVArray[index].key === d.key) {
                    for (var i in sitesKVArray[index].value) {
                        if(i == currentWord) {
                            totalWordWeight = sitesKVArray[index].value[i];
                            break;
                        }
                    }
                }
            }
            if(d.right) {
                text = "(" + totalWordWeight + ") " + d.name;
            } else {
                text = d.name + " (" + totalWordWeight + ")";
            }
        } else {
            text = d.name;
        }
    }

    return text;
}

function getKeyDim(scratch, key) {
    var word = key;
    scratch.empty();
    scratch.append(document.createTextNode(word));
    return {width: scratch.outerWidth()};
}

function getPixelDims(scratch, t, isShowWeight) {
    // scratch is an elemen with the correct styling, t is the text to be counted in pixels
    var word = isShowWeight ? t + "()" : t + "(        )";
    scratch.empty();
    scratch.append(document.createTextNode("(33)"+t));                  //HARDFIX todo: invent something
    return {width: scratch.outerWidth(), height: scratch.outerHeight() * 1.5};
}

function initialize(selectedDiagram) {

    var initPromise = $.Deferred();
    var control = {};
    control.divName = "#chart";

    //some basic options
    var newoptions = {
        nodeLabel: "label",
        nodeResize: "count", height: 900,
        nodeFocus: true, radius: 3, charge: -2500
    };
    // defaults
    control.options = $.extend({
        stackHeight: 12,
        radius: 5,
        fontSize: 14,
        labelFontSize: 18,
        labelLineSpacing: 2.5,
        nodeLabel: null,
        markerWidth: 0,
        markerHeight: 0,
        width: $(control.divName).outerWidth(),
        gap: 1.5,
        nodeResize: "",
        linkDistance: 200,
        charge: 3000,
        styleColumn: null,
        styles: null,
        linkName: null,
        nodeFocus: true,
        nodeFocusRadius: 25,
        nodeFocusColor: "FireBrick",
        labelOffset: 20,
        gravity: .5,
        routeFocusStroke: "FireBrick",
        routeFocusStrokeWidth: 3,
        circleFill: "Black",
        routeStroke: "Black",
        routeStrokeWidth: 1,
        minHeight: 500,
        height: $(control.divName).outerHeight()

    }, newoptions);

    var options = control.options;
    options.gap = options.gap * options.radius;
    control.width = options.width;
    control.height = options.height;
    // this is an element that can be used to determine the width of a text label

    control.scratch = $(document.createElement('span'))
        .addClass('shadow')
        .css('display', 'none')
        .css("font-size", control.options.labelFontSize + "px");
    $('body').append(control.scratch);

    getTheData(control, selectedDiagram).then(function (data) {

        control.data = data;
        control.nodes = data.nodes;
        control.links = data.links;
        control.color = d3.scale.category20();
        control.clickHack = 200;

        control.svg = d3.select(control.divName)
            .append("svg:svg")
            .attr("width", control.width)
            .attr("height", control.height);

        control.force = d3.layout.force().
            size([control.width, control.height])
            .linkDistance(control.options.linkDistance)
            .linkStrength(0.1)
            //.friction(0.3)
            //.chargeDistance(-5000)
            //.alpha(-300)
            .charge(control.options.charge)
            .gravity(control.options.gravity);

        initPromise.resolve(control);
    });
    return initPromise.promise();
}

function getTheData(control, selectedDiagram) {
    var massage = $.Deferred();

    var maxWords = 7;
    var newData = [];
    var wordsIndex;
    var sitesIndex;
    var sortedHeap = JSON.parse(window.localStorage.getItem("sortedHeap"));
    var dataBySites = JSON.parse(window.localStorage.getItem("dataD3"));
    var dataByLinks = JSON.parse(window.localStorage.getItem("relatedLinks"));
    sitesKVArray = d3.entries(dataBySites);
    heapKVArray = d3.entries(sortedHeap);
    linksKVArray = d3.entries(dataByLinks);

    if(selectedDiagram && selectedDiagram != "originalRequest") {
        sitesKVArray = getFilteredData(selectedDiagram)[0];
        heapKVArray = getFilteredData(selectedDiagram)[1];
    } else {
        sitesKVArray = d3.entries(dataBySites);
        heapKVArray = d3.entries(sortedHeap);
        linksKVArray = d3.entries(dataByLinks);
        if(selectedDiagram != "originalRequest") {
            linksKVArray.forEach(putLinksToPage);
        }
    }


    for (var index = 0; index < maxWords; index++) {
        newData[index] = {name: heapKVArray[index].key, isShowWeight: true, wordWeight:heapKVArray[index].value, key: heapKVArray[index].key, pages: []};
    }

    for (wordsIndex = 0; wordsIndex < maxWords; wordsIndex++) {
        var word = newData[wordsIndex].key;
        for (sitesIndex = 0; sitesIndex < sitesKVArray.length; sitesIndex++) {
            if (sitesKVArray[sitesIndex].value.hasOwnProperty(word)) {
                var currentSiteWordsList = sitesKVArray[sitesIndex].value;
                var pageName = sitesKVArray[sitesIndex].key;
                if (sitesKVArray[sitesIndex].key.length >= 30) { //todo: move 30 to global var?
                    pageName = parseUri(sitesKVArray[sitesIndex].key).host + "..." + sitesKVArray[sitesIndex].key.substr(-7);
                }
                newData[wordsIndex].pages.push({
                    name: pageName,
                    isShowWeight: false,
                    wordWeight: currentSiteWordsList[word],
                    key: sitesKVArray[sitesIndex].key,
                    url: sitesKVArray[sitesIndex].key
                });
            }
        }
    }

    var newCount = 0;

    for (var index = 0; index < newData.length; index++) {
        for (var index2 = 0; index2 < newData[index].pages.length; index2++) {
            newCount += newData[index].pages[index2].wordWeight;
        }
        newData[index].count = newCount;                                                        //size of central circles
        newCount = 0;
    }

    normalizeRadiusOfCentralCircles(newData, control);

    massage.resolve(dataMassage(control, newData));
    return massage.promise();
}

function normalizeRadiusOfCentralCircles(data, control) {
    var amount = 0;
    for (var index = 0; index < data.length; index++) {
        if (amount < data[index].count) {
            amount = data[index].count;
        }
    }

    if (amount >= 3000 && amount <= 60000) {
        control.options.radius = 0.5;
    }
    if (amount > 60000) {
        control.options.radius = 0.2;
    }

}
function getFilteredData(selectedDiagram) {
    var dataContainer;
    var countedWords = [];
    var numberWords = 7;
    var maxWords;
    var sites = [];
    var words = [];

    linksKVArray.forEach(function(d) {
        if(d.key === selectedDiagram) {
            dataContainer = d.value;
        }
    });
    var i = 0;
    dataContainer.forEach(function(d) {
        var currentUrl;
        sitesKVArray.forEach(function(s) {
            if(s.key === d) {
                currentUrl = s.value;
                sites[i] = s;
            }
        });
        i++;
        maxWords = d3.entries(currentUrl);
        maxWords.forEach(function(c) {
            countedWords[c.key] = countedWords[c.key] ? countedWords[c.key] + c.value : c.value;
        });
    });

    var totalCount = d3.entries(countedWords);

    for(var i = 0; i < numberWords; i++) {
        var maxKey = 0;
        var maxValue = 0;

        totalCount.forEach(function(w) {
            if(i == 0) {
                maxKey = maxValue < w.value ? w.key : maxKey;
                maxValue = maxValue < w.value ? w.value : maxValue;
                words[i] = {"key":maxKey, "value":maxValue};
            } else {
                maxKey = maxValue < w.value && w.value < words[i - 1].value ? w.key : maxKey;
                maxValue = maxValue < w.value && w.value < words[i - 1].value ? w.value : maxValue;
                words[i] = {"key":maxKey, "value":maxValue};
            }

        });
    }
    return [sites, words];
}

function putLinksToPage(value, index, dataByLinks) {
    var cssId = index + 3;
    $("#splitDiagrams").append("<div class=\"" + cssId + "\" onClick=\"drawSelectedDiagram('"+ value.key +"')\">" + value.key + "</div>");
    $(".urlContainer div:nth-child(" + cssId + ")")
        .css("padding-left", "5px")
        .css("padding-bottom", "10px")
        .css("float", "left");
    $("." + cssId).hover(function(e) {
            $(this).css("color",e.type === "mouseenter"?"#970E11":"#30b1d9")
        }
    )
}

function parseUri (str) {
    var	o   = parseUri.options,
        m   = o.parser[o.strictMode ? "strict" : "loose"].exec(str),
        uri = {},
        i   = 14;

    while (i--) uri[o.key[i]] = m[i] || "";

    uri[o.q.name] = {};
    uri[o.key[12]].replace(o.q.parser, function ($0, $1, $2) {
        if ($1) uri[o.q.name][$1] = $2;
    });

    return uri;
}

parseUri.options = {
    strictMode: false,
    key: ["source","protocol","authority","userInfo","user","password","host","port","relative","path","directory","file","query","anchor"],
    q:   {
        name:   "queryKey",
        parser: /(?:^|&)([^&=]*)=?([^&]*)/g
    },
    parser: {
        strict: /^(?:([^:\/?#]+):)?(?:\/\/((?:(([^:@]*)(?::([^:@]*))?)?@)?([^:\/?#]*)(?::(\d*))?))?((((?:[^?#\/]*\/)*)([^?#]*))(?:\?([^#]*))?(?:#(.*))?)/,
        loose:  /^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/)?((?:(([^:@]*)(?::([^:@]*))?)?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/
    }
};

function dataMassage(control, data) {

    var ind = data, nodes = [], links = [];
    // the tags are to be circles
    for (var i = 0; i < ind.length; i++) {
        ind[i].isCurrentlyFocused = false;
        nodes.push(ind[i]);
        // add links to pages
        for (var j = 0; j < ind[i].pages.length; j++) {
            //push this page as a node
            var node = findOrAddPage(ind[i].pages[j], nodes);
            node.isCurrentlyFocused = false;
            // create a link
            var link = {source: node, target: ind[i], key: node.key + "_" + ind[i].key};
            links.push(link);
        }
    }

    // sort nodes alpha
    nodes.sort(function (a, b) {                                                        // calculating position of pages
        return a.key < b.key ? -1 : (a.key == b.key ? 0 : 1 );
    });

    var currentHeight = 0;
    if(nodes.length < 100) {
        var pageSize = getPageSize();
        currentHeight = pageSize[3] / (nodes.length / 2);
    }

    control.pageCount = 0;
    control.pageRectSize = {width: 0, height: currentHeight, radius: 0};
    for (var i = 0; i < nodes.length; i++) {
        page = nodes[i];
        page.group = 0;
        page.dim = getPixelDims(control.scratch, page.name, page.isShowWeight);
        if (page.fixed) {
            control.pageCount++;
            // this will calculate the width/height in pixels of the largest label
            control.pageRectSize.width = Math.max(control.pageRectSize.width, page.dim.width);
            control.pageRectSize.height = Math.max(control.pageRectSize.height, page.dim.height);
            control.pageRectSize.radius = Math.max(control.pageRectSize.radius, makeRadius(control, page));
            page.group = 1;
        }

    }
    var options = control.options;

    // we're going to fix the nodes that are pages into two columns
    var totalHeight = 0;
    for (var i = 0, c = 0; i < nodes.length; i++) {
        var page = nodes[i];
        // x based on right or left column

        if (page.fixed) {
            page.right = (c >= control.pageCount / 2);
            // y dimension calc same for each column
            page.y = ((c % (control.pageCount / 2)) + .5) * (control.pageRectSize.height);
            if(page.right) {
                totalHeight = page.y;
            }
            page.x = page.right ?
                control.width - control.pageRectSize.width - options.labelOffset :
                page.dim.width + options.labelOffset;
            c++;
        }
    }
    control.height = totalHeight + control.pageRectSize.height;

    return {nodes: nodes, links: links};
}

function findOrAddPage(page, nodes) {                                               // size of left/right circles
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].key === page.key) {
            nodes[i].count++;
            return nodes[i];
        }
    }
    page.fixed = true;
    page.count = 0;
    return nodes[nodes.push(page) - 1];
}

function getPageSize() {
    var xScroll, yScroll;

    if (window.innerHeight && window.scrollMaxY) {
        xScroll = document.body.scrollWidth;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else if (document.body.scrollHeight > document.body.offsetHeight){
        xScroll = document.body.scrollWidth;
        yScroll = document.body.scrollHeight;
    } else if (document.documentElement && document.documentElement.scrollHeight > document.documentElement.offsetHeight){
        xScroll = document.documentElement.scrollWidth;
        yScroll = document.documentElement.scrollHeight;
    } else {
        xScroll = document.body.offsetWidth;
        yScroll = document.body.offsetHeight;
    }

    var windowWidth, windowHeight;
    if (self.innerHeight) {
        windowWidth = self.innerWidth;
        windowHeight = self.innerHeight;
    } else if (document.documentElement && document.documentElement.clientHeight) {
        windowWidth = document.documentElement.clientWidth;
        windowHeight = document.documentElement.clientHeight;
    } else if (document.body) {
        windowWidth = document.body.clientWidth;
        windowHeight = document.body.clientHeight;
    }

    if(yScroll < windowHeight){
        pageHeight = windowHeight;
    } else {
        pageHeight = yScroll;
    }

    if(xScroll < windowWidth){
        pageWidth = windowWidth;
    } else {
        pageWidth = xScroll;
    }
    return [pageWidth,pageHeight,windowWidth,windowHeight];
}