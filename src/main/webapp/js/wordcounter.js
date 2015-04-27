var spinner;
var dataResponse;
var isFilter = false;
var isFilterWords = 0;
var textCount;
var urlCount;
var userRequest;
var countedWords;
var selectedRows = 10;
var tableSortingFieldParam = 1;
var tableSortingOrderParam = "desc";
var opts;
var target;
var dataErrors;
var dataStatistic;
var dataD3;
var dataUrlTree;
var errorsMessage = "";
var isErrors = false;

$(document).ready(function() {
    $("#wordCounterForm").submit(function(e){
        e.preventDefault();
    });

    $("#getCountedWords").click(function(e){
        opts = {
            lines: 11, // The number of lines to draw
            length: 17, // The length of each line
            width: 10, // The line thickness
            radius: 18, // The radius of the inner circle
            corners: 1, // Corner roundness (0..1)
            rotate: 7, // The rotation offset
            direction: 1, // 1: clockwise, -1: counterclockwise
            color: '#b7aeae', // #rgb or #rrggbb or array of colors
            speed: 1.1, // Rounds per second
            trail: 60, // Afterglow percentage
            shadow: false, // Whether to render a shadow
            hwaccel: false, // Whether to use hardware acceleration
            className: 'spinner', // The CSS class to assign to the spinner
            zIndex: 2e9, // The z-index (defaults to 2000000000)
            top: 'auto', // Top position relative to parent
            left: '50%' // Left position relative to parent
        };
        target = document.getElementById('spinnerAnchor');
        textCount = checkAndNeutralizePrefix($("textarea#textCount").val());
        urlCount = checkAndAddPrefix($("textarea#urlCount").val());
        crawlDepth = getCrawlDepth();
        crawlScope = getCrawlScoupe(); //todo remove u
        userRequest = textCount.length > 0 ? textCount : urlCount;
        dataString = "textCount=" + encodeURIComponent(userRequest) + "&crawlDepth=" + crawlDepth + "&crawlScope=" + crawlScope;
        $.ajax({
            type: "POST",
            url: "./countWords",
            data: dataString,
            dataType: "json",
            success: function(data) {
                dataResponse = data.countedResult;
                dataErrors = data.errors;
                dataStatistic = data.wordStatistic;
                dataD3 = data.d3TestData;
                dataUrlTree = data.relatedLinks;
                setStatusFilter();
                countedWords = getCountedWords(dataResponse, isFilter);

                if (countedWords.length > 0) {
                    displayResponseContainer();
                    if ( $.fn.dataTable.isDataTable( '#countedWords' ) ) {
                        selectedRows = getSelectedRows();
                    }
                    showErrors(dataErrors);
                    initFilterContainer();
                    writeTable(countedWords, selectedRows);
                    showStatistic(dataStatistic);
                    showWordCloud();
                    scrollToAnchor("#exportButtons");
                } else {
                    displayErrorContainer();
                    showErrors(dataErrors);
                }
            },
            error: function(jqXHR){
                hideResponseContainer();
                var errorMassage = jQuery.parseJSON(jqXHR.responseText);
                $("#messageCounter").html(errorMassage.respMessage).css("color", "red");
            },
            beforeSend: function(){
                $('#CountWords').attr("disabled", true);
                spinner = new Spinner(opts).spin(target);
                $.blockUI({ message: null });
            },
            complete: function(){
                $('#CountWords').attr("disabled", false);
                spinner.stop(target);
                $.unblockUI();
            }
        });
    });

    $("#buttonGetFilterWords").click(function(e){
        isFilter = true;
        var activSpinner = runSpinner(isFilter);
        activSpinner.done(function(){ spinner.stop(target); });
    });

    $("#buttonGetUnFilterWords").click(function(e){
        isFilter = false;
        var activSpinner = runSpinner(isFilter);
        activSpinner.done(function(){ spinner.stop(target); });
    });

    $("#saveAsPdf").click(function(e) {
        userRequest = textCount.length > 0 ? textCount : urlCount;
        dataString = "textCount=" + encodeURIComponent(userRequest) + "&sortingOrder=" + getSortingOrder()
            + "&isFilterWords=" + isFilterWords + "&crawlDepth=" + crawlDepth + "&crawlScope=" + crawlScope;

        var path = "/WordSurfer/downloadPDF";
        requestBinaryCopyOfCalculatedWords(path);
    });

    $("#saveAsXls").click(function(e) {
        userRequest = textCount.length > 0 ? textCount : urlCount;
        dataString = "textCount=" + encodeURIComponent(userRequest) + "&sortingOrder=" + getSortingOrder()
            + "&isFilterWords=" + isFilterWords + "&crawlDepth=" + crawlDepth + "&crawlScope=" + crawlScope;

        var path = "/WordSurfer/downloadExcel";
        requestBinaryCopyOfCalculatedWords(path);
    });
});

$(window).load(function(){
    var ancorPosition = $("#content1").offset();
    $("#shareBar").css('top', ancorPosition.top);
    $('.deepideaSocial').deepideaSocial();
});
function reloadPage() {
    $("textarea#textCount").val("");
    $("textarea#urlCount").val("");
    location.reload()
}

function scrollToAnchor(wordTable){
    var tag = $(wordTable);
    $('html,body').animate({scrollTop: tag.offset().top}, 2000);
}

function checkAndNeutralizePrefix(requestedText) {
    var correctText = requestedText.replace(/:/gi, ";");
    return correctText;
}

function checkAndAddPrefix(requestedUrls) {
    var prefix = "http://";
    var httpPrefix = "http:";
    var httpsPrefix = "https";
    var splitRequest = requestedUrls.split(/\s+/);
    var correctRequest = "";
    for (var urlIndex = 0; urlIndex < splitRequest.length; urlIndex++) {
        if (splitRequest[urlIndex].trim() != "") {
            if (splitRequest[urlIndex].toLowerCase().substr(0, 5) === httpPrefix || splitRequest[urlIndex].toLowerCase().substr(0, 5) === httpsPrefix) {
                correctRequest += splitRequest[urlIndex] + " ";
            } else {
                correctRequest += prefix + splitRequest[urlIndex] + " ";
            }
        }
    }
    return correctRequest;
}

function clearRequest(idTextarea, idContainer) {
    $("textarea#" + idTextarea).val("");
    $("#" + idContainer).hide();
    $("input[name=crawlDepth]").prop('checked', false);
}

function setWordConnectionData() {
    var isVisualization = true;
    var sortedHeap = getFilteredWords(dataResponse, isVisualization);
    for (var property in dataD3) {
        if (dataD3.hasOwnProperty(property)) {
            dataD3[property] = getFilteredWords(dataD3[property], isVisualization);
        }
    }
    window.localStorage.setItem("sortedHeap", JSON.stringify(sortedHeap));
    window.localStorage.setItem("dataD3", JSON.stringify(dataD3));
    window.localStorage.setItem("relatedLinks", JSON.stringify(dataUrlTree));
}

function setUrlTreeData() {
    var isVisualization = true;
    var sortedHeap = getFilteredWords(dataResponse, isVisualization);

    window.localStorage.setItem("sortedHeap", JSON.stringify(sortedHeap));
    window.localStorage.setItem("dataD3", JSON.stringify(dataD3));
    window.localStorage.setItem("relatedLinks", JSON.stringify(dataUrlTree));
}

function requestBinaryCopyOfCalculatedWords(path) {
    spinner.spin(target);
    $.blockUI({ message: null });
    $.fileDownload(path, {httpMethod: "POST", data: dataString})
                           .done(function () {
                                                spinner.stop(target);
                                                $.unblockUI();
                                             })
                           .fail(function () {
                                                alert('File download failed!');
                                                $.unblockUI();
                                                });
}

function runSpinner(isFilter){
    var deferred = $.Deferred();
    var activeTime = 200;
    spinner = new Spinner(opts).spin(target);
    $.blockUI({ message: null });
    setTimeout(function(){
        $("#filterContainer").appendTo("#initWordsFilter");
        $("#filterShow").appendTo("#initWordsFilter");
        $("#filterContainer").hide();
        setTableContext(isFilter);
        showErrors(dataErrors);
        writeTable(countedWords, selectedRows);
        $.unblockUI();
        deferred.resolve();
    }, activeTime);
    return deferred;
}

function getSortingOrder() {
    var sortedElement = $("th[aria-sort]");
    var sortingOrder = sortedElement.attr("aria-sort");
    var sortingField = sortedElement.index();

    if(sortingField == 0) {
        sortingField = "KEY_";
        tableSortingFieldParam = 0;
    }
    if(sortingField == 1) {
        sortingField = "VALUE_";
        tableSortingFieldParam = 1;
    }

    if(sortingOrder == "ascending") {
        sortingOrder = "ASCENDING";
        tableSortingOrderParam = "asc";
    }
    if(sortingOrder == "descending") {
        sortingOrder = "DESCENDING";
        tableSortingOrderParam = "desc";
    }

    if(sortingField && sortingOrder) {
        return sortingField+sortingOrder;
    } else {
        return "NOT_DEFINED";
    }
}

function setTableContext(isFilter) {
    selectedRows = getSelectedRows();
    countedWords = getCountedWords(dataResponse, isFilter);
    setStatusFilter(isFilter);
    dataTableDestroy();
    displayResponseContainer();
}

function writeTable(countedWords, pageLength) {
    getSortingOrder();
    $('#countedWords').dataTable( {
        "destroy": true,
        "data": countedWords,
        "order": [ parseInt(tableSortingFieldParam), tableSortingOrderParam ],
        "pageLength": parseInt(pageLength),
        "autoWidth": false,
        "deferRender": true,
        "columns": [
            {
                "title": $("#wordsColumNameAnchor").text(),
                "width": "70%"
            },
            {
                "title": $("#countColumNameAnchor").text(),
                "width": "30%"
            }
        ]
    });
    displayFilterContainer();
}

function initFilterContainer() {
    $("#filterContainer").prependTo("#content1");
    $("#filterShow").prependTo("#content1");
    $("#filterContainer").hide();
}

function displayFilterContainer() {
    $("#filterContainer").show();
    $("#filterContainer").appendTo("#countedWords_length").prev();
    $("#filterShow").appendTo("#countedWords_filter").prev();
}

function dataTableDestroy() {
    $('#countedWords').dataTable().fnDestroy();
    $('#countedWords').hide();
}

function getCountedWords(unFilteredWords, isFilter) {
    var countedWordsTable = [];

    if(isFilter) {
        countedWordsTable = getFilteredWords(unFilteredWords);
    } else {
        countedWordsTable = getMultiArrayOfCalculatedWords(unFilteredWords);
    }

    return countedWordsTable;
}

function getFilteredWords(unFilteredWords, isVisualization) {
    var index;
    var copyOfUnfilteredWords = jQuery.extend({}, unFilteredWords);
    var filteringWords = $('#wordsFilter').text().split(' ');

    for (index = 0; index < filteringWords.length; ++index) {
        if (copyOfUnfilteredWords.hasOwnProperty(filteringWords[index])) {
            delete copyOfUnfilteredWords[filteringWords[index]];
        }
    }
    if(isVisualization) {
        return copyOfUnfilteredWords;
    } else {
        return getMultiArrayOfCalculatedWords(copyOfUnfilteredWords);
    }
}

function getMultiArrayOfCalculatedWords(calculatedWords) {
    var countedWordsTable = [];
    var index = 0;

    $.each(calculatedWords, function(key, value) {
        countedWordsTable[index] = [];
        countedWordsTable[index][0] = key;
        countedWordsTable[index][1] = value;
        index++;
    });

    return countedWordsTable;
}

function getSelectedRows() {
    var getSelectedRows = $("select[name] option:selected");
    return getSelectedRows.val();
}

function showFilteredWords() {
    $("input[name=filterCheck]:checkbox:checked").val() ? $("#filterShow").show() : $("#filterShow").hide();
    isFilter = $("input[name=filterCheck]:checkbox:checked").val() ? true : false;
    var activSpinner = runSpinner(isFilter);
    activSpinner.done(function(){ spinner.stop(target); });
}

function showCrawl() {
    $("#crawlContainer").show();
}

function setStatusFilter() {
    isFilterWords = $("input[name=filterCheck]:checkbox:checked").val() ? true : false;
//    isFilter = isFilterWords;
    if(!isFilter) {
        $("#buttonGetUnFilterWords").hide();
        $("#buttonGetFilterWords").show();
        isFilterWords = false;
    } else {
        $("#buttonGetFilterWords").hide();
        $("#buttonGetUnFilterWords").show();
        isFilterWords = true;
    }
}

function displayResponseContainer() { //todo move divs to elementsContainer
    $("#showFilter").show();
//    $("#filterContainerButton").show();
    $("#saveAsPdf").show();
    $("#saveAsXls").show();
    $("#textExport").show();
    $("#wordCounterResponse").show();
    $("#wordCloud").show();
    $('#countedWords').show();
    $("#messageCounter").hide();
    $('#errorsContainer').text('');
    $('#statisticContainer').show();
    $('#statisticTableSpan').show();
    $('#reloadWordCounter').show();
    $('#wordCloudData').show();
    getCrawlDepth() ? $('#wordConnection').show() : $('#wordConnection').hide();
    getCrawlDepth() ? $('#urlTree').show() : $('#urlTree').hide();
}

function hideResponseContainer() {
    $("#messageCounter").show();
    $("#buttonGetUnFilterWords").hide();
    $("#buttonGetFilterWords").hide();
    $("#showFilter").hide();
//    $("#filterContainerButton").hide();
    $("#saveAsPdf").hide();
    $("#saveAsXls").hide();
    $("#textExport").hide();
    $("#wordCounterResponse").hide();
    $("#wordCloud").hide();
    $('#countedWords').hide();
    $('#errorsSpoiler').hide();
    $('#statisticContainer').hide();
    $('#statisticTableSpan').hide();
    $('#reloadWordCounter').hide();
    $('#wordCloudData').hide();
    $('#wordConnection').hide();
}

function displayErrorContainer() {
    $("#messageCounter").hide();
//    $("#buttonGetUnFilterWords").hide();
//    $("#buttonGetFilterWords").hide();
    $("#showFilter").hide();
//    $("#filterContainerButton").hide();
    $("#saveAsPdf").hide();
    $("#saveAsXls").hide();
    $("#textExport").hide();
    $("#wordCounterResponse").hide();
    $('#countedWords').hide();
    $('#errorsSpoiler').hide();
    $('#statisticContainer').hide();
    $('#statisticTableSpan').hide();
    $('#wordCloudData').hide();
    $('#wordConnection').hide();
}

function showErrors(dataErrors) {
    var isIE = /*@cc_on!@*/false || !!document.documentMode;
    if (dataErrors == "") {
        isErrors = false;
    } else {
        $.each(dataErrors, function (key, value) {
            errorsMessage += '<li id=\"liError\">' + value + '</li>';
            isErrors = true;
        });
    }
    if (isErrors){
        if (isIE) {
            $('#errorsSpoiler').css("display", "table");
            $(".spoiler_open").attr("class", "spoiler_openIe");
        }
        $('#errorsSpoiler').show();
        $('#errorsContainer').html(errorsMessage);
        errorsMessage = '';
    } else {
        $('#errorsSpoiler').hide();
    }
}

function showCrawlScope() {
    if($("input[name=crawlDepth]:checkbox:checked").val()) {
        $("#crawlScopeClarification").css("color", "#30b1d9");
        $("input[name=crawlLocalDomain]").prop('disabled', false);
    } else {
        $("input[name=crawlLocalDomain]").prop('disabled', true);
        $("input[name=crawlLocalDomain]").prop('checked', true);
        $("#crawlScopeClarification").css("color", "#534d4d");
    }
}

function showStatistic(dataStatistic) {
    $.each(dataStatistic, function (key, value) {
        $('#' + key).html(value)
    });
}

//function closeSpoiler() {
//    $("spoiler_close").click();
//}

function getCrawlScoupe() {
    if($("input[name=crawlLocalDomain]:checkbox:checked").val()) {
        crawlScope = "true";

    } else {
        crawlScope = "false";
    }
    return crawlScope;
}

function getCrawlDepth() {
    if($("input[name=crawlDepth]:checkbox:checked").val()) {
        crawlScope = 1;
    } else {
        crawlScope = 0;
    }
    return crawlScope;
}

function showWordCloud() {
    var div = $("#wordCloudData");
    var canvas = $("#canvas_cloud").get(0);
        canvas.width  = div.width();
        canvas.height = div.height();
    normalizationWords(canvas);
    var options = {
        list: countedWords,
        gridSize: 1,
        weightFactor: 1,
        rotateRatio: 0.5
    };
    WordCloud(canvas, options);
}

function showModalWordCloud() {
    var pageSize = getPageSize();
    var cloudCanvasWidth = pageSize[2];
    var cloudCanvasHeight = pageSize[3];
    var resizePercent = 93 / 100;
    var cloudCanvas = $("#canvas_cloudModal").get(0);
        cloudCanvas.width  = cloudCanvasWidth * resizePercent;
        cloudCanvas.height  = cloudCanvasHeight * resizePercent;
    var backgroundColor = $("#osx-modal-data-wordCloud").css("background-color");
    normalizationWords(cloudCanvas);
    var options = {
        list: countedWords,
        gridSize: 1,
        weightFactor: 1,
        backgroundColor: backgroundColor,
        rotateRatio: 0.5
    };
    WordCloud(cloudCanvas, options);
}

function normalizationWords(canvas) { //todo refactor code around 10h
    countedWords = getCountedWords(dataResponse, true);
    if(countedWords.length === 0) {
        countedWords = getCountedWords(dataResponse, false);
    }
    var countedWordsLength = countedWords.length;

    var wordsListLength = countedWords.length;
    var maxWordWeight = countedWords[0][1];
    var minWordWeight = countedWords[wordsListLength - 1][1];
    var minWeight = 1;
    var intervalParam = [];
    var numberIntervals = 1;
    if(maxWordWeight - minWordWeight > 1) {
        numberIntervals = 1 + Math.round(Math.log(countedWords.length, 2));
        var stepLength = (maxWordWeight - minWordWeight) / numberIntervals;
        var increasePercent = 50;
        i = 0;
        intervalParam[i] = [];
        intervalParam[i][0] = minWordWeight + stepLength;
        intervalParam[i][1] = minWeight;
        for(i++; i < numberIntervals; i++) {
            intervalParam[i] = [];
            intervalParam[i][0] = intervalParam[i - 1][0] + stepLength;
            intervalParam[i][1] = intervalParam[i - 1][1] + (intervalParam[i - 1][1] * increasePercent / 100);
        }
    } else {
        intervalParam[0] = [minWordWeight, minWeight];
    }

    i = 0;
    for(; i < wordsListLength; i++) {
        for(var k = 0; k < numberIntervals; k++) {
            if(countedWords[i][1] <= intervalParam[k][0]) {
                countedWords[i][1] = intervalParam[k][1];
                break;
            }
        }
    }
    var maxStepSize = 2;
    var increaseFactor = 1.5;
    var amountWordsInInterval = [];
    var indexIntervalFactor = 0;
    var amountIntervalFactor = 0;
    var countWordsInInterval = 0;
    for(i = countedWords.length - 2; i >= 0; i--) {
        countWordsInInterval++;
        if (countedWords[i][1] / countedWords[i + 1][1] > maxStepSize) {
            countedWords[i][1] = countedWords[i + 1][1] * increaseFactor;
        }
        if(countedWords[i][1] != countedWords[i + 1][1]) {
            amountWordsInInterval[indexIntervalFactor] = [countedWords[i + 1][1], countWordsInInterval];
            indexIntervalFactor++;
            countWordsInInterval = 0;
            amountIntervalFactor += countedWords[i + 1][1];
        }
    }
    amountWordsInInterval[indexIntervalFactor] = [countedWords[i + 1][1], ++countWordsInInterval];
    amountIntervalFactor += countedWords[i + 1][1];
    var constantWordLength = 5;
    var constantFontSize = 1;
    var constantWordWidth = 2.4;
    var constantWordHeight = 0.7;
    var constantMinFontSize = 16;
    var constantMinWordLength = 5;
    var wordHeight = 0;
    var canvasSquare = (canvas.width * canvas.height);
    var wordIntervalWidth = 0;
    var wordIntervalHeight = 0;
    var wordsIntervalsSquare = 0;
    var wordIntervalSquare = 0;
    var fontZoomFactor = 0;
    var fontSize = 0;
    var wordWidth = 0;
    var amountWords = 0;
    var usedSquare = 0;
    var minWordsLenth = 7;
    var minFontZoomFactor = 0.8;
    var minWordsLenthFactor = 0.7;
    var i = 0;
    amountWords = getIntervalFactor(amountWordsInInterval, countedWords[i][1]);
    wordWidth = (countedWords[i][0].length * constantWordWidth / constantWordLength);
    wordHeight = constantWordHeight;
    wordsIntervalsSquare = (countedWords[i][1] * 100 / amountIntervalFactor / amountWords) * (canvasSquare - usedSquare) / 100;
    wordIntervalHeight = Math.sqrt(wordsIntervalsSquare / (wordWidth * wordHeight) * Math.pow(wordHeight, 2));
    wordIntervalWidth = constantWordWidth / constantWordHeight * wordIntervalHeight;
    if(wordIntervalWidth > canvas.height) {
        wordIntervalHeight = wordIntervalHeight * (canvas.height / wordIntervalWidth);
        wordIntervalWidth = canvas.height;
    }
    fontZoomFactor = wordIntervalHeight / wordHeight;
    fontSize = constantFontSize * fontZoomFactor;
    usedSquare += wordIntervalWidth * wordIntervalHeight;
    var prevWordFactor = countedWords[i][1];
    if(countedWords[i][1] == 1) {
       minFontZoomFactor = 0.5; //todo count factor with math
    }
    fontSize *= minFontZoomFactor;
    countedWords[i][1] = fontSize;
    var currentFontSize = fontSize;

    i++;
    if(i < countedWordsLength) {
        do {
            if (countedWords[i][1] < prevWordFactor) {
                currentFontSize = currentFontSize / (prevWordFactor / countedWords[i][1]);
                prevWordFactor = countedWords[i][1];
            }
            amountWords = getIntervalFactor(amountWordsInInterval, countedWords[i][1]);
            wordIntervalWidth = (countedWords[i][0].length * constantWordWidth / constantWordLength * currentFontSize / constantFontSize);
            wordIntervalHeight = currentFontSize * constantWordHeight / constantFontSize;
            if (wordIntervalWidth > canvas.height) {
                wordIntervalHeight = wordIntervalHeight * (canvas.height / wordIntervalWidth);
                wordIntervalWidth = canvas.height;
            }
            if(countedWords[i][0].length > minWordsLenth) {
                currentFontSize *= minWordsLenthFactor;
            }
            fontSize = currentFontSize;
            if (fontSize < constantMinFontSize) {
                wordIntervalWidth = (countedWords[i][0].length * constantWordWidth / constantWordLength * constantMinFontSize / constantFontSize);
                wordIntervalHeight = constantMinFontSize * constantWordHeight / constantFontSize;
                fontZoomFactor = wordIntervalHeight / wordHeight;
                fontSize = constantFontSize * fontZoomFactor;
            }
            usedSquare += wordIntervalWidth * wordIntervalHeight;
            countedWords[i][1] = fontSize;
            i++;
            if (i == countedWordsLength) {
                break;
            }
        } while (usedSquare < canvasSquare);
        var removingWord = i;
        for (; i < countedWordsLength; i++) {
            countedWords.splice(removingWord, 1);
        }
    }

}

function getIntervalFactor(amountWordsInInterval, intervalFactor) {
    var k = 0;
    var wordsInIntervalLength = amountWordsInInterval.length;
    var amountWords = 0;
    for(; k < wordsInIntervalLength; k++) {
        if(amountWordsInInterval[k][0] == intervalFactor) {
            amountWords = amountWordsInInterval[k][1];
            break;
        }
    }
    return amountWords;
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
