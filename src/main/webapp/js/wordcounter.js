$(document).ready(function() {

    //Stops the submit request
    $("#myAjaxRequestForm").submit(function(e){
        e.preventDefault();
    });

    //checks for the button click event
    $("#myButton").click(function(e){

        //getTextType the form data and then serialize that
        dataString = $("#myAjaxRequestForm").serialize();

        //getTextType the form data using another method
        var userRequest = $("textarea#userRequest").val();
        var dataTypeResponse = $('input[name=dataTypeResponse]').val();
        dataString = "userRequest=" + userRequest + "&dataTypeResponse=" + dataTypeResponse;
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
                    $('#ajaxResponse').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
                    var parsed = data.dataAjax;
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
                $('#myButton').attr("disabled", true);
            },

            //this is called after the response or error functions are finsihed
            //so that we can take some action
            complete: function(jqXHR, textStatus){
                //enable the button
                $('#myButton').attr("disabled", false);
            }

        });
    });
});