<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>
    <title>Word Counter ${version}</title>
    <link rel="stylesheet" href="/resources/themes/master.css" type="text/css"/>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
    <script src="/resources/scripts/mysamplecode.js" type="text/javascript"></script>
    <script src="/resources/scripts/mysamplecode.js" type="text/javascript"></script>

    <%--<link href="http://cdnjs.cloudflare.com/ajax/libs/foundation/4.3.1/css/foundation.min.css" rel="stylesheet" type="text/css"/>--%>
    <link href="http://cdn.datatables.net/plug-ins/725b2a2115b/integration/foundation/dataTables.foundation.css" rel="stylesheet" type="text/css"/>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/foundation/4.3.1/css/foundation.min.css" rel="stylesheet" type="text/css"/>

    <script src="http://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="http://cdn.datatables.net/plug-ins/725b2a2115b/integration/foundation/dataTables.foundation.js" type="text/javascript"></script>


    <script>
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
//                            alert(data.dataAjax);
//                            jsonLength = data.response.length;
//                            $("#ajaxResponse").html("");

//                            for(i=0; i < jsonLength; ++i) {
//                                $("#ajaxResponse").append("<p><h1>" + data.listUsersUrls[i] + "</h1></p>");
//                                $("#ajaxResponse").append("<table>");
//                                $("#ajaxResponse").append("<tr><td><b>Word: </p></b></td><td><b> Count : </p></b></td></tr>");
//                                countLines = (data.response[i]).length;
//                                    for(j=0; j < countLines; j++) {
//                                        $("#ajaxResponse").append("<tr><td>" + data.response[i][j].key + "</td><td>" + data.response[i][j].value + "</td></tr>");
//                                    }
//                                $("#ajaxResponse").append("</table>");
//
//                            }

                            $('#ajaxResponse').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );

                            var parsed = data.dataAjax;

                            var arr1 = [];
                            var parsedLength = parsed.length;
//                                alert(parsedLength);

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



    </script>
</head>

<body>
<div id="allContent" style="padding: 0px 50px;">
    <div id="myHeader">
        <div id="myLogo" class="header">
        </div>

        <h2>
            Word Counter ${version}
        </h2>
    </div>
    <div id="myExample">
        <form id="myAjaxRequestForm">

            <fieldset>
                <h4>Put here the link and get the most used words.</h4>
                <p>For Example: <br><span style="padding: 0px 50px;">http://www.eslfast.com/supereasy/se/supereasy002.htm</span>
                <br><span style="padding: 0px 50px;">http://defas.com.ua/java/Policy_of_.UA.pdf</br></span></p>
                <p>
                    <label for="userRequest">Input url:</label>
                    <textarea id="userRequest" name="userRequest" cols="70" rows="7"></textarea></p>
                    <input type="hidden" name="dataTypeResponse" value="json">
                </p>
                <p>
                    <input id="myButton" type="button" value="Count Me!"/>
                </p>
            </fieldset>
        </form>
        <div id="anotherSection">
            <fieldset>
                <legend>WordCounter response request(s)</legend>
                <div id="ajaxResponse">No counted most used words.</div>
            </fieldset>
        </div>
    </div>
    <div id="ajaxResp"></div>

</div>
</body>
</html>