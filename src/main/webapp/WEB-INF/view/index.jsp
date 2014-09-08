﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>
    <title>Word Counter ${version}</title>
    <link rel="stylesheet" href="css/wordcounter.css" type="text/css"/>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>

    <%--<link href="http://cdnjs.cloudflare.com/ajax/libs/foundation/4.3.1/css/foundation.min.css" rel="stylesheet" type="text/css"/>--%>
    <link href="http://cdn.datatables.net/plug-ins/725b2a2115b/integration/foundation/dataTables.foundation.css" rel="stylesheet" type="text/css"/>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/foundation/4.3.1/css/foundation.min.css" rel="stylesheet" type="text/css"/>

    <script src="http://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="http://cdn.datatables.net/plug-ins/725b2a2115b/integration/foundation/dataTables.foundation.js" type="text/javascript"></script>
    <script src="js/wordcounter.js"  type="text/javascript"></script>

</head>

<body>
<div id="allContent">
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
                    <input id="myButton" type="button" value="Count words"/>
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
<img src="img/ok.jpg"> </img>
</body>
</html>