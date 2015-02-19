var spinner;
var dataResponse;
var isFilter = true;
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
}

function setXlsFields() {
    closeSpoiler();
    $("input:hidden[id='xlsTextCount']").attr("value", textCount);
    $("input:hidden[id='xlsSortingOrder']").attr("value", getSortingOrder());
    $("input:hidden[id='xlsIsFilterWords']").attr("value", isFilterWords);
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
    if(weightFactor <= 1) {
        weightFactor += 0.1;
    }

    var options =
    {
        list: countedWords,
        gridSize: gridSize,
        weightFactor: weightFactor,
        minSize: 10,
        rotateRatio: 0.5
    }
    WordCloud(canvas, options);
}

function showModalWordCloud() {
    countedWords = getCountedWords(dataResponse, true);
    var weightFactor = 100 / countedWords[0][1];
    var div = $("#osx-modal-data-wordCloud");
    var canvas = $("#canvas_cloudModal").get(0);

    canvas.width  = div.offsetParent().width() * 0.93;
    canvas.height = div.offsetParent().height() * 0.8;
    var options =
    {
        list: countedWords,
        gridSize: 10,
        weightFactor: weightFactor * 1.4,
        backgroundColor: '#EEEEEE',
        minSize: 7,
        rotateRatio: 0.5
    }
    WordCloud(canvas, options);
}
