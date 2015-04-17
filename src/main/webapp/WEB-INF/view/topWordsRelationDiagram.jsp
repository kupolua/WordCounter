<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- //todo do refactor after change web design by kristi-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>

    <link href="css/foundation.min.css" rel="stylesheet" type="text/css"/>
    <link href="font/OpenSans-Regular.ttf" rel="stylesheet" type='text/css'>
    <link href="css/d3RelationDiagram.css" rel="stylesheet" type="text/css">

    <script src="js/d3.v3.min.js" type="text/javascript"></script>
    <script src="js/jsapi.js" type="text/javascript"></script>
    <script src="js/topWordsRelationDiagram.js"  type="text/javascript"></script>
    <script>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

        ga('create', 'UA-61981167-1', 'auto');
        ga('send', 'pageview');

    </script>
</head>
<body id="aboutUS">
    <div id="logo">
        <img src="img/wc-6.png" alt="WordCounterLogo">
    </div>
    <div id="navi">
        <ul>
          <li><a href="/WordCounter"><spring:message code="about.home"/></a></li>
          <li><a href="about" id="aboutUsLink"><spring:message code="index.aboutLink"/></a></li>
        </ul>
    </div>
    <div id="splitDiagrams" class="urlContainer">
        <div><spring:message code="index.splitDiagrams"/></div>
        <div class="originalRequest" onClick="drawSelectedDiagram('originalRequest')"><spring:message code="index.originalRequest"/></div>
    </div>
    <div id="chart">
    </div>
</body>
</html>