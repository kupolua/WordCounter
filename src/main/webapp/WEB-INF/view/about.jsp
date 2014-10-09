<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<!-- //todo do refactor after change web design by kristi-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>

    <link rel="stylesheet" href="css/aboutus.css" type="text/css"/>
    <link href="css/foundation.min.css" rel="stylesheet" type="text/css"/>
    <link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
</head>
<body>
    <div id="logo">
        <img src="https://dl.dropboxusercontent.com/u/18408157/wc-6.png" alt="WordCounterLogo">
    </div>
    <div id="navi">
            <ul>
              <li><a href="/WordCounter"><spring:message code="about.home"/></a></li>
              <li><a href="about" id="aboutUsLink"><spring:message code="index.aboutLink"/></a></li>
            </ul>
    </div>
<div id="allAboutContent">
        <p id="aboutText">We are a smart, ambitious and young team of software developers from Ukraine.</p>
        <p id="aboutText">We value hard work, collaboration and integrity.</p>
        <p id="aboutText">We are dedicated to what we do striving to provide the best quality for our customers.</p>
</div>
</body>
</html>