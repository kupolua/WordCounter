var spinner;
var dataResponse;
var isFilter = 0;
var userUrlsList;
var selectedRows = 10;
$(document).ready(function() {
    $("#wordCounterForm").submit(function(e){
        e.preventDefault();
    });

    $("#CountWords").click(function(e){
        var opts = {
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
        var target = document.getElementById('spinner');
        userUrlsList = $("textarea#userUrlsList").val();
        var dataTypeResponse = $('input[name=dataTypeResponse]').val();
        dataString = "userUrlsList=" + encodeURIComponent(userUrlsList);
        $.ajax({
            type: "POST",
            url: "./countWords",
            data: dataString,
            dataType: "json",
            success: function( data, textStatus, jqXHR) {
                //todo remove if when error hending
                if(data.success){
                    dataResponse = data.unFilteredWords;
                    writeTable(dataResponse, isFilter, selectedRows); //todo remove writeTable(). Use callback
                }
            },
            error: function(jqXHR, textStatus, errorThrown){
                $("#wordCounterResponse").html(jqXHR.responseText);
            },
            beforeSend: function(jqXHR, settings){
                settings.data += "&dummyData=whatever";
                $('#CountWords').attr("disabled", true)
                spinner = new Spinner(opts).spin(target);
            },
            complete: function(jqXHR, textStatus){
                $('#CountWords').attr("disabled", false);
                spinner.stop(target);
            }
        });
    });
    //todo hide button. It will be use for ajax request
    $("#buttonSaveAsPdf").click(function(e){
        var opts = {
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
        var target = document.getElementById('spinner');
        userUrlsList = $("textarea#userUrlsList").val();
        dataString = "userUrlsList=" + encodeURIComponent(userUrlsList);
        $.ajax({
            type: "POST",
            url: "./downloadPDF",
            data: dataString,
            dataType: "pdf",
            success: function( data, textStatus, jqXHR) {
                //todo remove if when error hending
//                if(data.success){
//                    alert("pdf");
//                    var win = window.open();
//                    win.document.write(data);
//                    dataResponse = data.unFilteredWords;
//                    writeTable(dataResponse, isFilter); //todo remove writeTable(). Use callback
//                }
            },
            error: function(jqXHR, textStatus, errorThrown){
                $("#wordCounterResponse").html(jqXHR.responseText);
            },
            beforeSend: function(jqXHR, settings){
                settings.data += "&dummyData=whatever";
                $('#buttonSaveAsPdf').attr("disabled", true)
                spinner = new Spinner(opts).spin(target);
            },
            complete: function(jqXHR, textStatus){
                $('#buttonSaveAsPdf').attr("disabled", false);
                spinner.stop(target);
            }
        });
    });

    $("#getPdfByUrl").click(function(e){
        $("#getPdfByUrl").attr("href", "downloadPDF?userUrlsList=" + encodeURIComponent(userUrlsList));
        $("#getPdfByUrl").attr("target", "_blank");

        var sortingParamKey = $("th[aria-sort]").each( function () {
            $(this).val( $(this).attr("aria-sort") );
        });
//        alert(sortingParamKey.text());
        var sortingParamValue = $("th[aria-sort]").val(function() {
            return $(this).attr("aria-sort");
        });
    });

    $("#buttonGetFilterWords").click(function(e){
        selectedRows = $("select[name] option:selected").val(function() {
            return $(this).text();
        });
//        alert(selectedRows.text());
        $('#countedWords').dataTable().fnDestroy();
        $('#countedWords').hide();
        isFilter = 1;
        writeTable(dataResponse, isFilter, selectedRows.text());
    });

    $("#buttonGetUnFilterWords").click(function(e){
        selectedRows = $("select[name] option:selected").val(function() {
            return $(this).text();
        });
//        alert(selectedRows.text());
        $('#countedWords').dataTable().fnDestroy();
        $('#countedWords').hide();
        isFilter = 0;
        writeTable(dataResponse, isFilter, selectedRows.text());
    });
});

function writeTable(unFilteredWords, isFilter, pageLength) {
    var index;
    var k = 0;
    var isFound = 0;
    var countedWords;
    var countedUnFilteredWords = [];
    var countedFilteredWords = [];
    var unFilteredWordsLength = unFilteredWords.length;
    var wordsFilter = $('#wordsFilter').text().split(' ');

    for(i = 0; i < unFilteredWordsLength; i++){
        countedUnFilteredWords[i] = [];
        var filterLength = unFilteredWords[i].length;
        for(j = 0; j < filterLength; j++) {
            countedUnFilteredWords[i][j] = unFilteredWords[i][j];
            index = wordsFilter.indexOf(unFilteredWords[i][0]);
            if (index < 0) {
                if(isFound == 0) {
                    countedFilteredWords[k] = [];
                }
                isFound = 1;
            }
            if(isFound == 1) {
                countedFilteredWords[k][j] = unFilteredWords[i][j];
            }
        }
        if(isFound == 1) {
            k++;
        }
        isFound = 0;
    }

    if(isFilter == 0){
        countedWords = countedUnFilteredWords;
    } else {
        countedWords = countedFilteredWords;
    }
    $("#noWordCounter").hide();
    if(isFilter == 0) {
        $("#buttonGetUnFilterWords").hide();
        $("#buttonGetFilterWords").show();
        isFilter = 1;
    } else {
        $("#buttonGetFilterWords").hide();
        $("#buttonGetUnFilterWords").show();
        isFilter = 0;
    }

    $("#showFilter").show();
    $("#saveAsPdf").show();
    $("#buttonSaveAsPdf").hide(); //todo hide button. It will be use for ajax request
    $("#wordCounterResponse").show();
    $('#countedWords').show();
    $('#countedWords').dataTable( {
        "data": countedWords,
        "order": [ 1, 'desc' ],
        "pageLength": pageLength,
        "columns": [
            { "title": "Word" },
            { "title": "Count" }
        ]
    });
}
