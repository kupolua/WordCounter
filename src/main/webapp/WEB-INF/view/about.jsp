<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- //todo do refactor after change web design by kristi-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>

    <link href="css/aboutus.css" rel="stylesheet" type="text/css"/>
    <link href="css/foundation.min.css" rel="stylesheet" type="text/css"/>
    <link href="font/OpenSans-Regular.ttf" rel="stylesheet" type='text/css'>
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
    <div id="allAboutContent">
        <div id="aboutWCHead"><spring:message code="about.WCHeader"/></div>
        <div id="aboutText"><spring:message code="about.aboutWC"/></div>
        <div id="aboutUsHead"><spring:message code="about.UsHeader"/></div>
        <div id="aboutUsText"><spring:message code="about.aboutUs"/></div>
        <div id="feedbackText"><spring:message code="about.feedback"/></div>
        <div id="feedbackAdress">
            <object type="image/svg+xml" data="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIj8+Cjxzdmcgd2lkdGg9IjE5NSIgaGVpZ2h0PSIxNSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KIDwhLS0gQ3JlYXRlZCB3aXRoIE1ldGhvZCBEcmF3IC0gaHR0cDovL2dpdGh1Yi5jb20vZHVvcGl4ZWwvTWV0aG9kLURyYXcvIC0tPgogPGc+CiAgPHRpdGxlPmJhY2tncm91bmQ8L3RpdGxlPgogIDxyZWN0IGZpbGw9IiNmZmYiIGlkPSJjYW52YXNfYmFja2dyb3VuZCIgaGVpZ2h0PSIxNyIgd2lkdGg9IjE5NyIgeT0iLTEiIHg9Ii0xIi8+CiAgPGcgZGlzcGxheT0ibm9uZSIgb3ZlcmZsb3c9InZpc2libGUiIHk9IjAiIHg9IjAiIGhlaWdodD0iMTAwJSIgd2lkdGg9IjEwMCUiIGlkPSJjYW52YXNHcmlkIj4KICAgPHJlY3QgZmlsbD0idXJsKCNncmlkcGF0dGVybikiIHN0cm9rZS13aWR0aD0iMCIgeT0iMCIgeD0iMCIgaGVpZ2h0PSIxMDAlIiB3aWR0aD0iMTAwJSIvPgogIDwvZz4KIDwvZz4KIDxnPgogIDx0aXRsZT5MYXllciAxPC90aXRsZT4KICA8dGV4dCB4bWw6c3BhY2U9InByZXNlcnZlIiB0ZXh0LWFuY2hvcj0ibGVmdCIgZm9udC1mYW1pbHk9IkhlbHZldGljYSwgQXJpYWwsIHNhbnMtc2VyaWYiIGZvbnQtc2l6ZT0iMTQiIGlkPSJzdmdfMSIgeT0iMTEuNDk5NzE5IiB4PSIxLjM3NzgyOCIgc3Ryb2tlLXdpZHRoPSIwIiBzdHJva2U9IiMwMDAiIGZpbGw9IiMwMDAwMDAiPmRlZXBpZGVhLnNvZnR3YXJlQGdtYWlsLmNvbTwvdGV4dD4KIDwvZz4KPC9zdmc+">Your browser does not support SVG</object>
        </div>

    </div>
</body>
</html>