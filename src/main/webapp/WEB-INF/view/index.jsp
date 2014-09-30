﻿<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
    <div id="aboutUs"><a href="about"><spring:message code="index.aboutLink"/></a></div>
    <div id="myExample">
        <form id="myAjaxRequestForm">

            <fieldset>
                <h4><spring:message code="index.textBig"/></h4>
                <p><spring:message code="index.example"/> <br><span style="padding: 0px 50px;"><spring:message code="index.exampleText1"/></span>
                <br><span style="padding: 0px 50px;"><spring:message code="index.exampleText2"/></br></span></p>
                <p>
                    <label for="userUrlsList"><spring:message code="index.enter"/></label>
                    <textarea id="userUrlsList" name="userUrlsList" cols="70" rows="7"></textarea></p>
                    <input type="hidden" name="dataTypeResponse" value="json">
                </p>
                <p>
                    <div id="spinner"></div>
                    <input id="button" type="button" value="<spring:message code="index.bSubmit"/>"/>
                </p>
            </fieldset>
        </form>
        <div id="anotherSection">
            <fieldset>
                <legend><spring:message code="index.response"/></legend>
                <div>
                    <div id="getFilterWords"></div>
                    <span id="showFilter"></span>
                </div>
                <div id="ajaxResponse"><spring:message code="index.noCount"/></div>
            </fieldset>
        </div>
    </div>
    <div id="ajaxResp"></div>

</div>
<img src="img/ok.jpg"> </img>
</body>
</html>   