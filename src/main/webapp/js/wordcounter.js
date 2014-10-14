var spinner;
var dataResponse;
var isFilter = false;
var isFilterWords = 0;
var textCount;
var countedWords;
var selectedRows = 10;
var opts;
var target;

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
            top: '150', // Top position relative to parent
            left: '50%' // Left position relative to parent
        };
        target = document.getElementById('spinnerAnchor');
        textCount = $("textarea#textCount").val();
        dataString = "textCount=" + encodeURIComponent(textCount);

        $.ajax({
            type: "POST",
            url: "./countWords",
            data: dataString,
            dataType: "json",
            success: function(data) {
                //todo remove if when error spring hending
                if(data.success){
                    dataResponse = data.unFilteredWords;
                    countedWords = getCountedWords(dataResponse, isFilter);
                    setStatusFilterButton(isFilter);
                    displayResponseContainer();
                    writeTable(countedWords, selectedRows); //todo remove writeTable(). Use callback
                }
            },
            error: function(jqXHR){
                hideResponseContainer();
                var errorMassage = jQuery.parseJSON(jqXHR.responseText);
                $("#messageCounter").html(errorMassage.respMessage);
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
        setTableContext(isFilter);
    });

    $("#buttonGetUnFilterWords").click(function(e){
        isFilter = false;
        setTableContext(isFilter);
    });

    $("#getPdfByUrl").click(function(e){
        $("#getPdfByUrl").attr("href", getLink("downloadPDF?"));
        $("#getPdfByUrl").attr("target", "_blank");
    });

    $("#getXlsByUrl").click(function(e){
        $("#getXlsByUrl").attr("href", getLink("downloadExcel?"));
        $("#getXlsByUrl").attr("target", "_blank");
    });
});

function getLink(appPath) {
    var linkAppPath = appPath +
        "textCount=" + encodeURIComponent(textCount) +
        "&sortingOrder=" + getSortingOrder() +
        "&isFilterWords=" + isFilterWords;
    return linkAppPath;
}

function getSortingOrder() {
    var sortedElement = $("th[aria-sort]");
    var sortingOrder = sortedElement.attr("aria-sort");
    var sortingField = sortedElement.index();

    if(sortingField == 0) {
        sortingField = "KEY_";
    }
    if(sortingField == 1) {
        sortingField = "VALUE_";
    }

    if(sortingOrder == "ascending") {
        sortingOrder = "ASCENDING";
    }
    if(sortingOrder == "descending") {
        sortingOrder = "DESCENDING";
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
    writeTable(countedWords, selectedRows.text());
}

function writeTable(countedWords, pageLength) {
    $('#countedWords').dataTable( {
        "destroy": true,
        "data": countedWords,
        "order": [ 1, 'desc' ],
        "pageLength": pageLength,
        "columns": [
            {"title": $("#wordsColumNameAnchor").text()},
            {"title": $("#countColumNameAnchor").text()}
        ]
    });
}

function getCountedWords(unFilteredWords, isFilter) {
    var index;
    var unFilteredWordsLength = unFilteredWords.length;
    var filterLength = 0;
    var countedWordsTable = [];
    var isFound = false;
    var isFilteredWord = 0;

    if(isFilter) {
        var wordsFilter = $('#wordsFilter').text().split(' ');
    }

    for(i = 0; i < unFilteredWordsLength; i++){
        if(!isFilter) {
            countedWordsTable[isFilteredWord] = [];
            isFound = true;
        } else {
            index = wordsFilter.indexOf(unFilteredWords[i][0]);
            if (index < 0) {
                countedWordsTable[isFilteredWord] = [];
                isFound = true;
            } else {
                isFound = false;
            }
        }
        if(isFound) {
            filterLength = unFilteredWords[i].length;
            for(j = 0; j < filterLength; j++) {
                countedWordsTable[isFilteredWord][j] = unFilteredWords[i][j];
            }
            isFilteredWord++;
        }
    }
    return countedWordsTable;
}

function dataTableDestroy() {
    $('#countedWords').dataTable().fnDestroy();
    $('#countedWords').hide();
}

function getSelectedRows() {
    selectedRows = $("select[name] option:selected").val(function() {
        return $(this).text();
    });
    return selectedRows;
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
    $('#countedWords').show();
    $("#messageCounter").hide();
}
function hideResponseContainer() {
    $("#messageCounter").show();
    $("#buttonGetUnFilterWords").hide();
    $("#buttonGetFilterWords").hide();
    $("#showFilter").hide();
    $("#saveAsPdf").hide();
    $("#saveAsXls").hide();
    $("#wordCounterResponse").hide();
    $('#countedWords').hide();
}


