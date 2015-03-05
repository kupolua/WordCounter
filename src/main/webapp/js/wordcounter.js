var spinner;
var dataResponse;
var isFilter = false;
var isFilterWords = 0;
var textCount;
var countedWords;
var selectedRows = 10;
var tableSortingFieldParam = 1;
var tableSortingOrderParam = "desc";
var opts;
var target;
var dataErrors;
var dataStatistic;
var errorsMessage = "";
var isErrors = false;
var totalWeigth = 0; //todo spelling totalWeight
var baseWordWeigth = 1.1; //todo spelling totalWeight
var baseCloudCanvas = 15.3;
var percent;

$(document).ready(function() {
    $("#wordCounterForm").submit(function(e){
        e.preventDefault();
    });

    $("#CountWords").click(function(e){
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
        textCount = $("textarea#textCount").val();
        crawlDepth = getCrawlDepth();
        crawlScope = getCrawlScoupe(); //todo remove u
        dataString = "textCount=" + encodeURIComponent(textCount) + "&crawlDepth=" + crawlDepth + "&crawlScope=" + crawlScope;
        //todo implement clearAllData & clear button
//        if($.fn.DataTable.isDataTable('#countedWords')){
//            dataTableDestroy();
//        }
        $.ajax({
            type: "POST",
            url: "./countWords",
            data: dataString,
            dataType: "json",
            success: function(data) {
                dataResponse = data.countedResult;
                dataErrors = data.errors;
                dataStatistic = data.wordStatistic;
                countedWords = getCountedWords(dataResponse, isFilter);

                if (countedWords.length > 0) {
                    setStatusFilterButton(isFilter);
                    displayResponseContainer();
                    if ( $.fn.dataTable.isDataTable( '#countedWords' ) ) {
                        selectedRows = getSelectedRows();
                    }
                    showErrors(dataErrors);
                    writeTable(countedWords, selectedRows);
                    showStatistic(dataStatistic);
                    showWordCloud();
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
            },
            complete: function(){
                $('#CountWords').attr("disabled", false);
                spinner.stop(target);
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
        dataString = "textCount=" + encodeURIComponent(textCount) + "&sortingOrder=" + getSortingOrder()
            + "&isFilterWords=" + isFilterWords + "&crawlDepth=" + crawlDepth + "&crawlScope=" + crawlScope;

        var path = "/WordCounter/downloadPDF";
        requestBinaryCopyOfCalculatedWords(path);
    });

    $("#saveAsXls").click(function(e) {
        dataString = "textCount=" + encodeURIComponent(textCount) + "&sortingOrder=" + getSortingOrder()
            + "&isFilterWords=" + isFilterWords + "&crawlDepth=" + crawlDepth + "&crawlScope=" + crawlScope;

        var path = "/WordCounter/downloadExcel";
        requestBinaryCopyOfCalculatedWords(path);
    });
});

function requestBinaryCopyOfCalculatedWords(path) {
    spinner.spin(target);
    $.fileDownload(path, {httpMethod: "POST", data: dataString})
                           .done(function () { spinner.stop(target); })
                           .fail(function () { alert('File download failed!'); });
}

function runSpinner(isFilter){
    var deferred = $.Deferred();
    var activeTime = 200;
    spinner = new Spinner(opts).spin(target);
    setTimeout(function(){
        setTableContext(isFilter);
        showErrors(dataErrors);
        writeTable(countedWords, selectedRows);
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
    setStatusFilterButton(isFilter);
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

function getFilteredWords(unFilteredWords) {
    var index;
    var copyOfUnfilteredWords = jQuery.extend({}, unFilteredWords);
    var filteringWords = $('#wordsFilter').text().split(' ');

    for (index = 0; index < filteringWords.length; ++index) {
        if (copyOfUnfilteredWords.hasOwnProperty(filteringWords[index])) {
            delete copyOfUnfilteredWords[filteringWords[index]];
        }
    }

    return getMultiArrayOfCalculatedWords(copyOfUnfilteredWords);
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

function setStatusFilterButton(isFilter) {
    $("#noWordCounter").hide();
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

function displayResponseContainer() {
    $("#showFilter").show();
    $("#saveAsPdf").show();
    $("#saveAsXls").show();
    $("#wordCounterResponse").show();
    $("#wordCloud").show();
    $('#countedWords').show();
    $("#messageCounter").hide();
    $('#errorsContainer').text('');
    $('#spoilerStatistic').show();
}

function hideResponseContainer() {
    $("#messageCounter").show();
    $("#buttonGetUnFilterWords").hide();
    $("#buttonGetFilterWords").hide();
    $("#showFilter").hide();
    $("#saveAsPdf").hide();
    $("#saveAsXls").hide();
    $("#wordCounterResponse").hide();
    $("#wordCloud").hide();
    $('#countedWords').hide();
    $('#errorsSpoiler').hide();
    $('#spoilerStatistic').hide();
}

function displayErrorContainer() {
    $("#messageCounter").hide();
    $("#buttonGetUnFilterWords").hide();
    $("#buttonGetFilterWords").hide();
    $("#showFilter").hide();
    $("#saveAsPdf").hide();
    $("#saveAsXls").hide();
    $("#wordCounterResponse").hide();
    $('#countedWords').hide();
    $('#errorsSpoiler').hide();
    $('#spoilerStatistic').hide();
}

function showErrors(dataErrors) {
    if (dataErrors == "") {
        isErrors = false;
    } else {
        $.each(dataErrors, function (key, value) {
            errorsMessage += '<li id=\"liError\">' + value + '</li>';
            isErrors = true;
        });
    }
    if (isErrors){
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

function closeSpoiler() {
    $("spoiler_close").click();
}

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
    normalizationWords();

    var div = $("#wordCloudData");
    var canvas = $("#canvas_cloud").get(0);
        canvas.width  = div.width();
        canvas.height = div.height();
    var constantCanvasSize = canvas.width / baseCloudCanvas * baseWordWeigth;
    var weightFactor = constantCanvasSize * (countedWords.length / totalWeigth) * percent;
    var options = {
        list: countedWords,
        gridSize: 10,
        weightFactor: weightFactor,
        rotateRatio: 0.5
    };
    WordCloud(canvas, options);
}

function showModalWordCloud() {
    var pageSize = getPageSize();
    var cloudCanvasWidth = pageSize[2];
    var cloudCanvasHeight = pageSize[3];
    var resizePercent = 90 / 100;
    var cloudCanvas = $("#canvas_cloudModal").get(0);
        cloudCanvas.width  = cloudCanvasWidth * resizePercent;
        cloudCanvas.height  = cloudCanvasHeight * resizePercent;
    var constantCanvasCloudSize = cloudCanvas.width / baseCloudCanvas * baseWordWeigth;
    var weightFactor = constantCanvasCloudSize * (countedWords.length / totalWeigth) * percent;
    var backgroundColor = $("#osx-modal-data-wordCloud").css("background-color");
    var options = {
        list: countedWords,
        gridSize: 10,
        weightFactor: weightFactor,
        backgroundColor: backgroundColor,
        rotateRatio: 0.5
    };
    WordCloud(cloudCanvas, options);
}

function normalizationWords() {
    countedWords = getCountedWords(dataResponse, true);
    var wordsListLength = countedWords.length;
    var maxWordsList = 100;
    var totalWordsWeigth = 0;
    totalWeigth = 0;
    percent = 0.45;
    for(var i = 0; i < wordsListLength; i++) {
        if(i > maxWordsList) {
            countedWords.splice(i, 1);
            i--;
            wordsListLength--;
        } else {
            totalWordsWeigth += countedWords[i][1];
        }
    }
    var maxWordWeight = countedWords[0][1];
    var minWordWeight = countedWords[wordsListLength - 1][1];
    var numberIntervals = 1 + Math.round(Math.log(countedWords.length, 2));
    var stepLength = (maxWordWeight - minWordWeight) / numberIntervals;
    var intervalParam = [];
    var increasePercent = 50;
    var minWeight = 1;
    for(var i = 0; i < numberIntervals; i++) {
        intervalParam[i] = [];
        if(i == 0) {
            intervalParam[i][0] = minWordWeight + stepLength;
            intervalParam[i][1] = minWeight;
        } else {
            intervalParam[i][0] = intervalParam[i - 1][0] + stepLength;
            intervalParam[i][1] = intervalParam[i - 1][1] + (intervalParam[i - 1][1] * increasePercent / 100);
        }
    }
    for(var i = 0; i < wordsListLength; i++) {
        for(var k = 0; k < numberIntervals; k++) {
            if(countedWords[i][1] <= Math.round(intervalParam[k][0])) {
                countedWords[i][1] = intervalParam[k][1];
                totalWeigth += countedWords[i][1];
                break;
            }
        }
    }
    if(wordsListLength < maxWordsList) {
        percent += ((maxWordsList - wordsListLength) / 100) + 1;
    }
}

function getPageSize() {
    var xScroll, yScroll;

    if (window.innerHeight && window.scrollMaxY) {
        xScroll = document.body.scrollWidth;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else if (document.body.scrollHeight > document.body.offsetHeight){
        xScroll = document.body.scrollWidth;
        yScroll = document.body.scrollHeight;
    } else if (document.documentElement && document.documentElement.scrollHeight > document.documentElement.offsetHeight){ // Explorer 6 strict mode
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