<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>

    <link href="css/foundation.min.css" rel="stylesheet" type="text/css"/>
    <%--<link href="css/d3v2.css" rel="stylesheet" type="text/css"/> //todo remove file--%>
    <link href="css/d3UrlTree.css" rel="stylesheet" type="text/css"/>
    <link href="font/OpenSans-Regular.ttf" rel="stylesheet" type='text/css'>

    <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="js/d3.v3.min.js" type="text/javascript"></script>
    <script src="js/d3.layout.js" type="text/javascript"></script>
    <%--<script src="js/wordTree.js" type="text/javascript"></script>//todo remove file--%>
    <script src="js/urlTree.js" type="text/javascript"></script>

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
    <div id="tree-container"></div>
</body>
</html>