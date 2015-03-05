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

    $("#getPdf").click("image", "form.pdfDownloadForm", function (e) {
        $.fileDownload($(this).prop('action'), {
            preparingMessageHtml: "We are preparing your report, please wait...",
            failMessageHtml: "There was a problem generating your report, please try again.",
            httpMethod: "POST",
            data: $(this).serialize()
        });
        e.preventDefault();
    });

    $("#getXls").click("image", "form.getXlsForm", function (e) {
        $.fileDownload($(this).prop('action'), {
            preparingMessageHtml: "We are preparing your report, please wait...",
            failMessageHtml: "There was a problem generating your report, please try again.",
            httpMethod: "POST",
            data: $(this).serialize()
        });
    });
});

function setPdfFields() {
    closeSpoiler();
    $("input:hidden[id='pdfTextCount']").attr("value", textCount);
    $("input:hidden[id='pdfSortingOrder']").attr("value", getSortingOrder());
    $("input:hidden[id='pdfIsFilterWords']").attr("value", isFilterWords);
    $("input:hidden[id='pdfCrawlDepth']").attr("value", getCrawlDepth());
    $("input:hidden[id='pdfCrawlScope']").attr("value", getCrawlScoupe());
}

function setXlsFields() {
    closeSpoiler();
    $("input:hidden[id='xlsTextCount']").attr("value", textCount);
    $("input:hidden[id='xlsSortingOrder']").attr("value", getSortingOrder());
    $("input:hidden[id='xlsIsFilterWords']").attr("value", isFilterWords);
    $("input:hidden[id='xlsCrawlDepth']").attr("value", getCrawlDepth());
    $("input:hidden[id='xlsCrawlScope']").attr("value", getCrawlScoupe());
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

    var div = $("#wordCloudData");
    var canvas = $("#canvas_cloud").get(0);
        canvas.width  = div.width();
        canvas.height = div.height();
    normalizationWords(canvas);
    var constantCanvasSize = canvas.width / baseCloudCanvas * baseWordWeigth;
//    var weightFactor = constantCanvasSize * (countedWords.length / totalWeigth) * percent;
//    getWeightFactor(canvas);
//    var weightFactor = getWeightFactor(canvas);
    var weightFactor = 1;
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

//    var constantCanvasCloudSize = cloudCanvas.width / baseCloudCanvas * baseWordWeigth;
//    var weightFactor = constantCanvasCloudSize * (countedWords.length / totalWeigth) * percent;
    var backgroundColor = $("#osx-modal-data-wordCloud").css("background-color");
//    var backgroundColor = "#cb0e15";
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

function normalizationWords(canvas) {
    countedWords = getCountedWords(dataResponse, true);
    var countedWordsLength = countedWords.length;

//    countedWords = [
//        ["word", 665],
//        ["maroko", 665],
//        ["enlargedIndex", 250],
//        ["тексте", 250],
//        ["мінімум", 153],
//        ["новини", 26],
//        ["відео", 13],
//        ["погляд", 13],
//        ["розділу", 12],
//        ["усі", 12],
//        ["ато", 8],
//        ["тсн", 8],
//        ["європи", 8],
//        ["дебальцевого", 8],
//        ["україни", 7],
//        ["онлайн", 7],
//        ["фото", 6],
//        ["загиблих", 6],
//        ["погляди", 5],
//        ["блоги", 5],
//        ["генгам", 5],
//        ["донбасі", 5],
//        ["tchua", 5],
//        ["динамо", 5],
//        ["політика", 5],
//        ["дня", 5],
//        ["україні", 5],
//        ["дніпро", 5],
//        ["суд", 4],
//        ["навальний", 4],
//        ["буде", 4],
//        ["діб", 4],
//        ["ліга", 4],
//        ["лютого", 4],
//        ["світ", 4],
//        ["київ", 4],
//        ["донбас", 4],
//        ["газу", 4],
//        ["дебальцеве", 4],
//        ["леді", 4],
//        ["сил", 4],
//        ["тснua", 4],
//        ["постачання", 4],
//        ["відеоконтенту", 3],
//        ["києва", 3],
//        ["бойовики", 3],
//        ["українські", 3],
//        ["лізі", 3],
//        ["обличчя", 3],
//        ["частина", 3],
//        ["разів", 3],
//        ["життя", 3],
//        ["битву", 3],
//        ["бойовиків", 3],
//        ["росія", 3],
//        ["путіна", 3],
//        ["авто", 3],
//        ["дивіться", 3],
//        ["час", 3],
//        ["чому", 3],
//        ["дебальцевому", 3],
//        ["два", 3],
//        ["сайту", 3],
//        ["сбу", 3],
//        ["сайті", 3],
//        ["виході", 3],
//        ["російських", 3],
//        ["рік", 3],
//        ["генштабі", 3],
//        ["україна", 3],
//        ["олімпіакосом", 3],
//        ["проспорт", 3],
//        ["кажуть", 3],
//        ["поранених", 3],
//        ["citroen", 3],
//        ["суду", 3],
//        ["росії", 3],
//        ["метро", 2],
//        ["жінки", 2],
//        ["бути", 2],
//        ["микола", 2],
//        ["заарештували", 2],
//        ["києві", 2],
//        ["реклама", 2],
//        ["бійців", 2],
//        ["перша", 2],
//        ["туди", 2],
//        ["детальніше", 2],
//        ["наливайченко", 2],
//        ["медики", 1],
//        ["альтернативного", 1],
//        ["сша", 1],
//        ["яременко", 1],
//        ["редакція", 1],
//        ["гроші", 1],
//        ["власні", 1],
//        ["підробляли", 1],
//        ["українська", 1],
//        ["артемівську", 1],
//        ["розповіла", 1],
//        ["української", 1],
//        ["взяти", 1]
//    ];
    var wordsListLength = countedWords.length;
//    var maxWordsList = 1000;
//    var totalWordsWeigth = 0;
//    totalWeigth = 0;
//    percent = 0.45;
//    var i = 0;
//    for(; i < wordsListLength; i++) {
//        if(i > maxWordsList) {
//            countedWords.splice(i, 1);
//            i--;
//            wordsListLength--;
//        } else {
//            totalWordsWeigth += countedWords[i][1];
//        }
//    }
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
//        var amountIntervalsFactor = 0;
        for(i++; i < numberIntervals; i++) {
            intervalParam[i] = [];
            intervalParam[i][0] = intervalParam[i - 1][0] + stepLength;
            intervalParam[i][1] = intervalParam[i - 1][1] + (intervalParam[i - 1][1] * increasePercent / 100);
//            amountIntervalsFactor += intervalParam[i][1];
//            $("#intervals").append("<tr>" +
//                "<td>" + intervalParam[i - 1][0].toFixed(2) + "</td>" +
//                "<td>" + intervalParam[i - 1][1].toFixed(2) + "</td>" +
//                "</tr>");
        }
    } else {
        intervalParam[0] = [minWordWeight, minWeight];
    }

//    $("#intervals").append("<tr>" +
//        "<td>" + intervalParam[numberIntervals - 1][0].toFixed(2) + "</td>" +
//        "<td>" + intervalParam[numberIntervals - 1][1].toFixed(2) + "</td>" +
//    "</tr>");

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
//            amountWordsInInterval[indexIntervalFactor] = 0;
            amountWordsInInterval[indexIntervalFactor] = [countedWords[i + 1][1], countWordsInInterval];
            indexIntervalFactor++;
            countWordsInInterval = 0;
            amountIntervalFactor += countedWords[i + 1][1];
        }
//        wordSquare = (countedWords[i][0].length * constantWordWidth / constantWordLength * countedWords[i][1] / constantFontSize) * (countedWords[i][1] * constantWordHeight / constantFontSize);
//        $("#words").html();
//        $("#words").append("<tr>" +
//            "<td>" + countedWords[i][0].length + "</td>" +
//            "<td>" + countedWords[i][0] + "</td>" +
//            "<td>" + countedWords[i][1].toFixed(2) + "</td>" +
//            "<td>" + wordSquare.toFixed(2) + "</td>" +
//            "</tr>");
    }
    amountWordsInInterval[indexIntervalFactor] = [countedWords[i + 1][1], ++countWordsInInterval];
    amountIntervalFactor += countedWords[i + 1][1];
    var constantWordLength = 5;
    var constantFontSize = 1;
    var constantWordWidth = 2.4;
    var constantWordHeight = 0.7;
    var constantMinFontSize = 18;
//    var constantFontSize = 12;
//    var constantWordWidth = 28.8;
//    var constantWordHeight = 8.4;
    var wordHeight = 0;
//    var canvasSquare = (canvas.width * canvas.height) - (canvas.width * canvas.height * 10 / 100);
    var canvasSquare = (canvas.width * canvas.height);
    var wordIntervalWidth = 0;
    var wordIntervalHeight = 0;
    var wordsIntervalsSquare = 0;
    var wordIntervalSquare = 0;
    var fontZoomFactor = 0;
    var fontSize = 0;
    var wordWidth = 0;
    var amountWords = 0;
    var isMinFontSize = false;
    var usedSquare = 0;
    var minWordFontSize = 1000;
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
    countedWords[i][1] = fontSize;
    var currentFontSize = fontSize;

    i++;
    do {
        if(countedWords[i][1] < prevWordFactor) {
            currentFontSize = currentFontSize / (prevWordFactor / countedWords[i][1]);
            prevWordFactor = countedWords[i][1];
        }
        amountWords = getIntervalFactor(amountWordsInInterval, countedWords[i][1]);
        wordIntervalWidth = (countedWords[i][0].length * constantWordWidth / constantWordLength * currentFontSize / constantFontSize);
        wordIntervalHeight = currentFontSize * constantWordHeight / constantFontSize;
//        wordsIntervalsSquare = (countedWords[i][1] * 100 / amountIntervalFactor / amountWords) * (canvasSquare - usedSquare) / 100;
//        wordIntervalHeight = Math.sqrt(wordsIntervalsSquare / (wordWidth * wordHeight) * Math.pow(wordHeight, 2));
//        wordIntervalWidth = constantWordWidth / constantWordHeight * wordIntervalHeight;
        if(wordIntervalWidth > canvas.height) {
            wordIntervalHeight = wordIntervalHeight * (canvas.height / wordIntervalWidth);
            wordIntervalWidth = canvas.height;
        }
//        fontZoomFactor = wordIntervalHeight / wordHeight;
//        fontSize = constantFontSize * fontZoomFactor;
        fontSize = currentFontSize;
        if(fontSize < constantMinFontSize) {
            wordIntervalWidth = (countedWords[i][0].length * constantWordWidth / constantWordLength * constantMinFontSize / constantFontSize);
            wordIntervalHeight = constantMinFontSize * constantWordHeight /constantFontSize;
            fontZoomFactor = wordIntervalHeight / wordHeight;
            fontSize = constantFontSize * fontZoomFactor;
        }
        usedSquare += wordIntervalWidth * wordIntervalHeight;
        countedWords[i][1] = fontSize;
        i++;
        if(i == countedWordsLength) {
            break;
        }
    } while(usedSquare < canvasSquare);
    var removingWord = i;
    for(; i < countedWordsLength; i++) {
        countedWords.splice(removingWord, 1);
    }

//    for(i = 0; i < countedWords.length; i++) {
//        if(isEmpySquare) {
//            countedWords.splice(i - 1, 1);
//            i--;
//        } else {
//            if (countedWords[i][1] <= amountWordsInInterval[0][0]) { //todo refactor repeat operation
//                wordsIntervalsSquare = (countedWords[i][1] * 100 / amountIntervalFactor) * (canvasSquare - usedSquare) / 100;
//                do {
//                    wordWidth = (countedWords[i][0].length * 28.8 / 8.4);
////                    wordWidth = (countedWords[i][0].length * constantWordWidth / constantWordLength * constantFontSize / constantFontSize);
//                    wordHeight = 8.4;
//                    wordIntervalSquare += wordWidth * wordHeight;
//                    usedSquare += wordIntervalSquare;
//                    countedWords[i][1] = 12;
//                    i++;
//                } while (wordIntervalSquare < wordsIntervalsSquare);
//                isEmpySquare = true;
//            } else {
//                amountWords = getIntervalFactor(amountWordsInInterval, countedWords[i][1]);
//                wordWidth = (countedWords[i][0].length * constantWordWidth / constantWordLength * constantFontSize / constantFontSize);
//                wordHeight = constantWordHeight;
//                wordsIntervalsSquare = (countedWords[i][1] * 100 / amountIntervalFactor / amountWords) * (canvasSquare - usedSquare) / 100;
//                wordIntervalHeight = Math.sqrt(wordsIntervalsSquare / (wordWidth * wordHeight) * Math.pow(wordHeight, 2));
//                wordIntervalWidth = constantWordWidth / constantWordHeight * wordIntervalHeight;
//                usedSquare += wordIntervalWidth * wordIntervalHeight;
//                fontZoomFactor = wordIntervalHeight / wordHeight;
//                fontSize = constantFontSize * fontZoomFactor;
//                countedWords[i][1] = fontSize;
//            }
//        }
//    }
//    countedWords.splice(countedWords.length - 1, 1);
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

function getWeightFactor(canvas) {
    var constantWordLength = 5;
    var constantFontSize = 100;
    var constantWordWidth = 240;
    var constantWordHeight = 70;
    var countWordsLength = countedWords.length;
    var canvasSquare = canvas.width * canvas.height;

    var intervalsSquare = [];
    var intervalIndex = 0;
    var countedWordsLength = countedWords.length;
    var i = 0;
    var intervalWordsSquare = 0;
    totalWordsSquare = 0;
    i++;
    for(; i < countedWordsLength; i++) {
        wordLength = countedWords[i][0].length;
        wordWeight = countedWords[i][1];
        wordWidth = wordLength * constantWordWidth / constantWordLength * wordWeight / constantFontSize;
        wordHeight = wordWeight * constantWordHeight / constantFontSize;
        wordSquare = wordWidth * wordHeight;
        intervalWordsSquare += wordSquare;
        totalWordsSquare += wordSquare;
        if(countedWords[i][1] != countedWords[i - 1][1]) {
            intervalsSquare[intervalIndex] = [countedWords[i][1], intervalWordsSquare];
            intervalIndex++;
            intervalWordsSquare = 0;
        }
    }
    intervalsSquare[intervalIndex - 1] = [countedWords[i - 1][1], intervalWordsSquare];
//    do {

//    } while (canvasSquare > 0);
}
function getWeightFactor_old(canvas) {
    var weightFactor = 0;
    var wordSquare = 0;
    var constantWordLength = 5;
    var constantFontSize = 100;
    var constantWordWidth = 240;
    var constantWordHeight = 70;
//    var wordsLength = countedWords.slice();
    var countWordsLength = countedWords.length;
    var wordWeight = 0;
    var wordLength = 0;
    var wordWidth = 0;
    var wordHeight = 0;
    var totalWordsSquare = 0;
    var totalWordsWidth = 0;
    var totalWordsHeight = 0;
    var correlationАactor = 0;
    var cloudPerimeter = 0;
    var canvasPerimeter = 0;
    var canvasSquare = canvas.width * canvas.height;
    $("#constants").html();
    $("#constants").append(
        "<tr><td>constantWordLength</td><td>" + constantWordLength + "</td></tr>" +
        "<tr><td>constantFontSize</td><td>" + constantFontSize + "</td></tr>" +
        "<tr><td>constantWordWidth</td><td>" + constantWordWidth + "</td></tr>" +
        "<tr><td>constantWordHeight</td><td>" + constantWordHeight + "</td></tr>" +
        "<tr><td>constantWordSquare</td><td>" + constantWordWidth * constantWordHeight + "</td></tr>" +
        "<tr><td>canvas.width</td><td>" + canvas.width + "</td></tr>" +
        "<tr><td>canvas.height</td><td>" + canvas.height + "</td></tr>" +
        "<tr><td>canvasSquare</td><td>" + (canvas.width * canvas.height) + "</td></tr>"
    );

    var normalStepSize = 2;
    var increaseFactor = 1.5;
    var intervalsSquare = [];
    var intervalIndex = 0;
    var countedWordsLength = countedWords.length;
    var i = 0;
    wordLength = countedWords[i][0].length;
    wordWeight = countedWords[i][1];
    wordWidth = wordLength * constantWordWidth / constantWordLength * wordWeight / constantFontSize;
    wordHeight = wordWeight * constantWordHeight / constantFontSize;
    wordSquare = wordWidth * wordHeight;
    totalWordsSquare += wordSquare;
    intervalsSquare[intervalIndex] = [countedWords[i][1], totalWordsSquare];
    intervalIndex++;
    var intervalWordsSquare = 0;
    totalWordsSquare = 0;
    i++;
    for(; i < countedWordsLength; i++) {
        wordLength = countedWords[i][0].length;
        wordWeight = countedWords[i][1];
        wordWidth = wordLength * constantWordWidth / constantWordLength * wordWeight / constantFontSize;
        wordHeight = wordWeight * constantWordHeight / constantFontSize;
        wordSquare = wordWidth * wordHeight;
        intervalWordsSquare += wordSquare;
        totalWordsSquare += wordSquare;
        if(countedWords[i][1] != countedWords[i - 1][1]) {
            intervalsSquare[intervalIndex] = [countedWords[i][1], intervalWordsSquare];
            intervalIndex++;
            intervalWordsSquare = 0;
        }
    }
    intervalsSquare[intervalIndex - 1] = [countedWords[i - 1][1], intervalWordsSquare];
//    do {

//    } while (canvasSquare > 0);
}

