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
    <link href="font/Lato-Regular.ttf" rel="stylesheet" type="text/css">
</head>
<body>
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
    </div>
</body>
</html>