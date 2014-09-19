﻿﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>
    <title>Word Counter ${version}</title>
    <link rel="stylesheet" href="css/wordcounter.css" type="text/css"/>
    <link href="css/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="css/dataTables.foundation.css" rel="stylesheet" type="text/css"/>
    <link href="css/foundation.min.css" rel="stylesheet" type="text/css"/>

    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery-ui.min.js" type="text/javascript"></script>
    <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="js/dataTables.foundation.js" type="text/javascript"></script>
    <script src="js/spin.js" type="text/javascript"></script>
    <script src="js/wordcounter.js"  type="text/javascript"></script>

</head>

<body>
<div id="allContent">
    <div id="myHeader">
        <a href="">Word Counter ${version}</a>
    </div>
    <div id="aboutUs"><a href="about">About us</a></div>
    <div id="myExample">
        <form id="myAjaxRequestForm">

            <fieldset>
                <h4>Put here the link or plain text and get the most used words.</h4>
                <p>For Example: <br><span style="padding: 0px 50px;">If you want your request to be processed as a URL, please add http:// prefix to it. http://localhost:8080/WordCounter/text</span>
                <br><span style="padding: 0px 50px;">Otherwise it will be handled as a plain text.</br></span></p>
                <p>
                    <label for="userUrlsList">Input url:</label>
                    <textarea id="userUrlsList" name="userUrlsList" cols="70" rows="7"></textarea></p>
                    <input type="hidden" name="dataTypeResponse" value="json">
                </p>
                <p>
                    <div id="spinner"></div>
                    <input id="button" type="button" value="Count words"/>
                </p>
            </fieldset>
        </form>
        <div id="anotherSection">
            <fieldset>
                <legend>WordCounter response request(s)</legend>
                <div>
                    <blockquote id="getFilterWords"></blockquote>
                    <span id="showFilter"></span>
                </div>
                <div id="ajaxResponse">No counted most used words.</div>
            </fieldset>
        </div>
    </div>
    <div id="ajaxResp"></div>

</div>
<img src="img/ok.jpg"> </img>
</body>
</html>   