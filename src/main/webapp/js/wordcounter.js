var spinner;
var dataResponse;
var isFilter = 0;
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
            color: '#2ba6cb', // #rgb or #rrggbb or array of colors
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
//                    alert("countedWords " + countedWords);
                    setStatusFilterButton(isFilter);
                    displayResponseContainer();
                    writeTable(countedWords, selectedRows); //todo remove writeTable(). Use callback
                }
            },
            error: function(jqXHR){
                $("#wordCounterResponse").html(jqXHR.responseText);
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

    $("#getPdfByUrl").click(function(e){
        var sortedElement = $("th[aria-sort]");
        var sortingOrder = sortedElement.attr("aria-sort");
        var sortingField = sortedElement.index();
//        alert("sortingField " + sortingField + " sortingOrder " + sortingOrder + " isFilterWords " + isFilterWords);
        $("#getPdfByUrl").attr("href", "downloadPDF?" +
            "textCount=" + encodeURIComponent(textCount) +
            "&sortingField=" + sortingField +
            "&sortingOrder=" + sortingOrder +
            "&isFilterWords=" + isFilterWords
        );
        $("#getPdfByUrl").attr("target", "_blank");
    });

    $("#buttonGetFilterWords").click(function(e){
        setTableContext(1);
    });

    $("#buttonGetUnFilterWords").click(function(e){
        setTableContext(0);
    });
});

function setTableContext(isFilter) {
    selectedRows = getSelectedRows();
    countedWords = getCountedWords(dataResponse, isFilter);
    setStatusFilterButton(isFilter);
    dataTableDestroy();
    displayResponseContainer();
//    alert(isFilter);
    writeTable(countedWords, selectedRows.text());
}

function writeTable(countedWords, pageLength) {
    $('#countedWords').dataTable( {
        "destroy": true,
        "data": countedWords,
        "order": [ 1, 'desc' ],
        "pageLength": pageLength,
        "columns": [
            { "title": $("#wordsColumNameAnchor").text() },
            { "title": $("#countColumNameAnchor").text() }
        ]
    });
}

function getCountedWords(unFilteredWords, isFilter) {
    var index;
    var unFilteredWordsLength = unFilteredWords.length;
    var filterLength = 0;
    var countedWordsTable = [];
    var isFound = 0;
    var isFilteredWord = 0;

    if(isFilter == 1) {
        var wordsFilter = $('#wordsFilter').text().split(' ');
    }

    for(i = 0; i < unFilteredWordsLength; i++){
        if(isFilter == 0) {
            countedWordsTable[isFilteredWord] = [];
            isFound = 1;
        } else {
            index = wordsFilter.indexOf(unFilteredWords[i][0]);
            if (index < 0) {
                countedWordsTable[isFilteredWord] = [];
                isFound = 1;
            } else {
                isFound = 0;
            }
        }
        if(isFound == 1) {
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
    if(isFilter == 0) {
        $("#buttonGetUnFilterWords").hide();
        $("#buttonGetFilterWords").show();
        isFilterWords = 0;
    } else {
        $("#buttonGetFilterWords").hide();
        $("#buttonGetUnFilterWords").show();
        isFilterWords = 1;
    }

}

function displayResponseContainer() {
    $("#showFilter").show();
    $("#saveAsPdf").show();
    $("#buttonSaveAsPdf").hide(); //todo hide button. It will be use for ajax request
    $("#wordCounterResponse").show();
    $('#countedWords').show();
}
