var spinner;
var dataResponse;
var isFilter = 0;
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
        var userUrlsList = $("textarea#userUrlsList").val();
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
                    writeTable(dataResponse, isFilter); //todo remove writeTable(). Use callback
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

    $("#buttonGetFilterWords").click(function(e){
        $('#countedWords').dataTable().fnDestroy();
        $('#countedWords').hide();
        writeTable(dataResponse, isFilter);
        isFilter = 1;
    });

    $("#buttonGetUnFilterWords").click(function(e){
        $('#countedWords').dataTable().fnDestroy();
        $('#countedWords').hide();
        writeTable(dataResponse, isFilter);
        isFilter = 0;
    });
});

function writeTable(unFilteredWords, isFilter) {
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
    $("#wordCounterResponse").show();
    $('#countedWords').show();
    $('#countedWords').dataTable( {
        "data": countedWords,
        "order": [ 1, 'desc' ],
        "columns": [
            { "title": "Word" },
            { "title": "Count" }
        ]
    });
}
