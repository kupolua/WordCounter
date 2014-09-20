<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>
    <title>Word Counter ${version}</title>

    <link rel="stylesheet" href="css/wordcounter.css" type="text/css"/>
    <script src="js/wordcounter.js"  type="text/javascript"></script>
</head>
<body>
    <div id="allContent">
        <div id="myHeader">
            <a href="/WordCounter">Word Counter ${version}</a>
        </div>
        <div id="goHome">
            <a href="/WordCounter">Home</a>
        </div>
        <div id="aboutUsHeaderLine">about us</div>
        <spring:message code="about.springmvc" text="default text" />
    </div>
</body>
</html>