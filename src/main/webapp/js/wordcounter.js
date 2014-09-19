var filteredWords;
var unFilteredWords;

function getFilteredWords(isFilter) {
    if (isFilter == 1){
        $('#getFilterWords').html( '<div id="filterWords" onclick="getFilteredWords(0)">Filter words</div>');
        writeTable(unFilteredWords);
    } else {
        $('#getFilterWords').html( '<div id="filterWords" onclick="getFilteredWords(1)">Unfilter words</div>');
        writeTable(filteredWords);
    }
}

function writeTable(parsed, filter) {
    $('#ajaxResponse').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
    $('#showFilter').html( '<a href="filter" target="_blank">Show filter</a>');
    var arr1 = [];
    var parsedLength = parsed.length;

    for(i = 0; i < parsedLength; i++){
        arr1[i] = [];
        var parsedLength2 = parsed[i].length;
        for(j = 0; j < parsedLength2; j++) {
            arr1[i][j] = parsed[i][j];
        }
    }

    $('#example').dataTable( {
        "data": arr1,
        "order": [ 1, 'desc' ],
        "columns": [
            { "title": "Word" },
            { "title": "Count" }
        ]
    });
}

$(document).ready(function() {

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
    var spinner;

//Stops the submit request
    $("#myAjaxRequestForm").submit(function(e){
        e.preventDefault();
    });

    //checks for the button click event
    $("#button").click(function(e){

        //getTextType the form data and then serialize that
        dataString = $("#myAjaxRequestForm").serialize();

        //getTextType the form data using another method
        var userUrlsList = $("textarea#userUrlsList").val();
        var dataTypeResponse = $('input[name=dataTypeResponse]').val();
        dataString = "userUrlsList=" + userUrlsList + "&dataTypeResponse=" + dataTypeResponse;
        //make the AJAX request, dataType is set to json
        //meaning we are expecting JSON data in response from the server
        $.ajax({
            type: "POST",
            url: "./countWords",
            data: dataString,
            dataType: "json",

            //if received a response from the server
            success: function( data, textStatus, jqXHR) {
                //our country code was correct so we have some information to display
                if(data.success){
                    unFilteredWords = data.dataAjax;
                    filteredWords = data.filteredWords;
                    getFilteredWords(1);
                }
                //display error message
                else {
                    $("#ajaxResponse").html("");
                    $("#ajaxResponse").append("<p>" + data.errorMessageToUser + "</p>");
                }
            },

            //If there was no resonse from the server
            error: function(jqXHR, textStatus, errorThrown){
                //console.log("Something really bad happened " + textStatus);
                $("#ajaxResponse").html(jqXHR.responseText);
            },

            //capture the request before it was sent to server
            beforeSend: function(jqXHR, settings){
                //adding some Dummy data to the request
                settings.data += "&dummyData=whatever";
                //disable the button until we getTextType the response
                $('#button').attr("disabled", true);
                spinner = new Spinner(opts).spin(target);


            },

            //this is called after the response or error functions are finsihed
            //so that we can take some action
            complete: function(jqXHR, textStatus){
                //enable the button
                spinner.stop(target);
                $('#button').attr("disabled", false);
            }

        });
    });
});
