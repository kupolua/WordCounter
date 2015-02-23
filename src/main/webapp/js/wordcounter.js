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
        crawlLevel = $("#crawler option:selected").val();
        if($("input[name=crawlLocalDomain]:checkbox:checked").val()) {
            crawlScope = "true";
        } else {
            crawlScope = "false";
        }
        dataString = "textCount=" + encodeURIComponent(textCount) + "&crawlLevel=" + crawlLevel + "&crawlScope=" + crawlScope;

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
    $("input:hidden[id='pdfCrawlLevel']").attr("value", $("#crawler option:selected").val());
    $("input:hidden[id='pdfCrawlScope']").attr("value", $("input[name=crawlLocalDomain]:checkbox").val());
    alert($("#crawler option:selected").val() + " : " + $("input[name=crawlLocalDomain]:checkbox").val());
}

function setXlsFields() {
    closeSpoiler();
    $("input:hidden[id='xlsTextCount']").attr("value", textCount);
    $("input:hidden[id='xlsSortingOrder']").attr("value", getSortingOrder());
    $("input:hidden[id='xlsIsFilterWords']").attr("value", isFilterWords);
    $("input:hidden[id='xlsCrawlLevel']").attr("value", $("#crawler option:selected").val());
    $("input:hidden[id='xlsCrawlScope']").attr("value", $("input[name=crawlLocalDomain]:checkbox").val());
    alert($("#crawler option:selected").val() + " : " + $("input[name=crawlLocalDomain]:checkbox").val());
}

function runSpinner(isFilter){
    var deferred = $.Deferred();
    var activeTime = 200;
    spinner = new Spinner(opts).spin(target);
    setTimeout(function(){
        setTableContext(isFilter);
        showErrors(dataErrors);
        writeTable(countedWords, selectedRows);
        showWordCloud(countedWords);
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

function getCountedWords(unFilteredWords, isFilter) {
    var index;
    var countedWordsTable = [];
    var isFound = false;
    var isFilteredWord = 0;

    if(isFilter) {
        var wordsFilter = $('#wordsFilter').text().split(' ');
    }

    $.each(unFilteredWords, function(key, value) {
        if(!isFilter) {
            countedWordsTable[isFilteredWord] = [];
            isFound = true;
        } else {
            index = wordsFilter.indexOf(key);
            if (index < 0) {
                countedWordsTable[isFilteredWord] = [];
                isFound = true;
            } else {
                isFound = false;
            }
        }
        if(isFound) {
            countedWordsTable[isFilteredWord][0] = key;
            countedWordsTable[isFilteredWord][1] = value;
            isFilteredWord++;
        }
    });
    return countedWordsTable;
}

function dataTableDestroy() {
    $('#countedWords').dataTable().fnDestroy();
    $('#countedWords').hide();
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

function showStatistic(dataStatistic) {
    $.each(dataStatistic, function (key, value) {
        $('#' + key).html(value)
    });
}

function closeSpoiler() {
    $("spoiler_close").click();
}

function showWordCloud() {
    countedWords = getCountedWords(dataResponse, true);
    var div = $("#wordCloudData");
    var canvas = $("#canvas_cloud").get(0);
        canvas.width  = div.width();
        canvas.height = div.height();
    var gridSize = Math.round(10 * canvas.width / 1024) * 2;
    var weightFactor = 100 / countedWords[0][1] * (0.6);
    if(weightFactor < 1) {
        weightFactor += 0.1;
    }

    var options = {
        list: countedWords,
//        list: [
//            ["word", "27"],
//            ["тексте", "27"],
//            ["мінімум", "27"],
//            ["новини", "18"],
//            ["відео", "18"],
//            ["погляд", "18"],
//            ["розділу", "18"],
//            ["усі", "18"],
//            ["ато", "9"],
//            ["тсн", "9"],
//            ["європи", "9"],
//            ["дебальцевого", "9"],
//            ["україни", "9"],
//            ["онлайн", "9"],
//            ["фото", "9"],
//            ["загиблих", "9"],
//            ["погляди", "9"],
//            ["блоги", "9"],
//            ["генгам", "9"],
//            ["донбасі", "9"],
//            ["tchua", "9"],
//            ["динамо", "9"],
//            ["політика", "9"],
//            ["дня", "9"],
//            ["україні", "9"],
//            ["дніпро", "9"],
//            ["суд", "9"],
//            ["навальний", "9"],
//            ["буде", "9"],
//            ["діб", "9"],
//            ["ліга", "9"],
//            ["лютого", "9"],
//            ["світ", "9"],
//            ["київ", "9"],
//            ["донбас", "9"],
//            ["газу", "9"],
//            ["дебальцеве", "9"],
//            ["леді", "9"],
//            ["сил", "9"],
//            ["тснua", "9"],
//            ["постачання", "9"],
//            ["відеоконтенту", "9"],
//            ["києва", "9"],
//            ["бойовики", "9"],
//            ["українські", "9"],
//            ["лізі", "9"],
//            ["обличчя", "9"],
//            ["частина", "9"],
//            ["разів", "9"],
//            ["життя", "9"],
//            ["битву", "9"],
//            ["бойовиків", "9"],
//            ["росія", "9"],
//            ["путіна", "9"],
//            ["авто", "9"],
//            ["дивіться", "9"],
//            ["час", "9"],
//            ["чому", "9"],
//            ["дебальцевому", "9"],
//            ["два", "9"],
//            ["сайту", "9"],
//            ["сбу", "9"],
//            ["сайті", "9"],
//            ["виході", "9"],
//            ["російських", "9"],
//            ["рік", "9"],
//            ["генштабі", "9"],
//            ["україна", "9"],
//            ["олімпіакосом", "9"],
//            ["проспорт", "9"],
//            ["кажуть", "9"],
//            ["поранених", "9"],
//            ["citroen", "9"],
//            ["суду", "9"],
//            ["росії", "9"],
//            ["метро", "9"],
//            ["жінки", "9"],
//            ["бути", "9"],
//            ["микола", "9"],
//            ["заарештували", "9"],
//            ["києві", "9"],
//            ["реклама", "9"],
//            ["бійців", "9"],
//            ["перша", "9"],
//            ["туди", "9"],
//            ["детальніше", "9"],
//            ["наливайченко", "9"],
//            ["медики", "9"],
//            ["альтернативного", "9"],
//            ["сша", "9"],
//            ["яременко", "9"],
//            ["редакція", "9"],
//            ["гроші", "9"],
//            ["власні", "9"],
//            ["підробляли", "9"],
//            ["українська", "9"],
//            ["артемівську", "9"],
//            ["розповіла", "9"],
//            ["української", "9"],
//            ["взяти", "9"]
//        ],
        gridSize: gridSize,
        weightFactor: weightFactor,
        minSize: 10,
        rotateRatio: 0.5
    }
    WordCloud(canvas, options);
}

function showModalWordCloud() {
//    countedWords = getCountedWords(dataResponse, true);
    countedWordsBig = [
        ["word", 665],
        ["тексте", 507],
        ["мінімум", 253],
        ["новини", 26],
        ["відео", 13],
        ["погляд", 13],
        ["розділу", 12],
        ["усі", 12],
        ["ато", 8],
        ["тсн", 8],
        ["європи", 8],
        ["дебальцевого", 8],
        ["україни", 7],
        ["онлайн", 7],
        ["фото", 6],
        ["загиблих", 6],
        ["погляди", 5],
        ["блоги", 5],
        ["генгам", 5],
        ["донбасі", 5],
        ["tchua", 5],
        ["динамо", 5],
        ["політика", 5]
    ];
    countedWordsMiddle = [
        ["дня", 5],
        ["україні", 5],
        ["дніпро", 5],
        ["суд", 4],
        ["навальний", 4],
        ["буде", 4],
        ["діб", 4],
        ["ліга", 4],
        ["лютого", 4],
        ["світ", 4],
        ["київ", 4],
        ["донбас", 4],
        ["газу", 4],
        ["дебальцеве", 4],
        ["леді", 4],
        ["сил", 4],
        ["тснua", 4],
        ["постачання", 4],
        ["відеоконтенту", 3],
        ["києва", 3],
        ["бойовики", 3],
        ["українські", 3],
        ["лізі", 3],
        ["обличчя", 3],
        ["частина", 3],
        ["разів", 3],
        ["життя", 3],
        ["битву", 3],
        ["бойовиків", 3],
        ["росія", 3],
        ["путіна", 3],
        ["авто", 3],
        ["дивіться", 3],
        ["час", 3],
        ["чому", 3],
        ["дебальцевому", 3],
        ["два", 3],
        ["сайту", 3],
        ["сбу", 3],
        ["сайті", 3],
        ["виході", 3],
        ["російських", 3],
        ["рік", 3],
        ["генштабі", 3],
        ["україна", 3],
        ["олімпіакосом", 3],
        ["проспорт", 3],
        ["кажуть", 3],
        ["поранених", 3],
        ["citroen", 3],
        ["суду", 3],
        ["росії", 3],
        ["метро", 2]
    ];
    countedWordsSmall = [
        ["жінки", 2],
        ["бути", 2],
        ["микола", 2],
        ["заарештували", 2],
        ["києві", 2],
        ["реклама", 2],
        ["бійців", 2],
        ["перша", 2],
        ["туди", 2],
        ["детальніше", 2],
        ["наливайченко", 2],
        ["медики", 1],
        ["альтернативного", 1],
        ["сша", 1],
        ["яременко", 1],
        ["редакція", 1],
        ["гроші", 1],
        ["власні", 1],
        ["підробляли", 1],
        ["українська", 1],
        ["артемівську", 1],
        ["розповіла", 1],
        ["української", 1],
        ["взяти", 1]
    ];
    var totalWordsWeigth = 0;
    var wordsListLength = countedWords.length;
    for(var i = 0; i < wordsListLength; i++) {
        totalWordsWeigth += countedWords[i][1];
    }
    var maxWordWeight = countedWords[0][1];
    var minWordWeight = countedWords[wordsListLength - 1][1];
    var numberIntervals = 1 + Math.round(Math.log(wordsListLength, 2));
    var step = (maxWordWeight - minWordWeight) / numberIntervals;
    var intervalParam = [];
    var increasePercent = 50;
    var minWeight = 1;
    var totalWeigth = 0;
    for(var i = 0; i < numberIntervals; i++) {
        intervalParam[i] = [];
        if(i == 0) {
            intervalParam[i][0] = minWordWeight + step;
            intervalParam[i][1] = minWeight;
        } else {
            intervalParam[i][0] = intervalParam[i - 1][0] + step;
            intervalParam[i][1] = intervalParam[i - 1][1] + (intervalParam[i - 1][1] * increasePercent / 100);
        }
    }
    for(var i = 0; i < wordsListLength; i++) {
        for(var k = 0; k < numberIntervals; k++) {
            if(countedWords[i][1] <= intervalParam[k][0]) {
                countedWords[i][1] = intervalParam[k][1];
                totalWeigth += countedWords[i][1];
                break;
            }
        }
    }

    var cloudContainer = $("#osx-modal-data-wordCloud");
    var cloudCanvas = $("#canvas_cloudModal").get(0); //todo change canvas_cloudModal to cloudCanvasModal

    cloudCanvas.width  = cloudContainer.offsetParent().width() * 0.93; //todo get width from css element
    cloudCanvas.height = cloudContainer.offsetParent().height() * 0.8; //todo todo height color from css element
//    var weightFactor = totalWeigth / 2;
    countedWords = countedWordsBig + countedWordsMiddle + countedWordsSmall;
    var weightFactor = 60;
    alert(
        "wordsListLength: " + wordsListLength + "\n" +
        "maxWeigth" + cloudCanvas.width / totalWeigth + "\n" +
        cloudCanvas.width + " / " + totalWeigth + " = " + cloudCanvas.width / totalWeigth + "\n" +
        cloudCanvas.height + " / " + totalWeigth + " = " + cloudCanvas.height / totalWeigth + "\n" +
        cloudCanvas.width + " * " + cloudCanvas.height  + " = " + cloudCanvas.width * cloudCanvas.height  + "\n" +
        "weightFactor: " + weightFactor
    );
    var options = {
        list: countedWords,
        gridSize: 10,
        weightFactor: weightFactor,
        backgroundColor: '#EEEEEE', //todo get color from css element
//        minSize: 7,
        rotateRatio: 0.5
    }
    WordCloud(cloudCanvas, options);
}
