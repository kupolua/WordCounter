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
            <a href="/WordCounter"><spring:message code="filter.home"/></a>
        </div>
        <div id="aboutUsHeaderLine"><spring:message code="filter.description"/></div>
        <div id="filterEN"><b><spring:message code="filter.wordsEn"/></b></br> ${wordsEN}</p> </div>
        <div id="filterUA"><b><spring:message code="filter.wordsRu"/></b></br> ${wordsRU}</p></div>
        <div id="filterRU"><b><spring:message code="filter.wordsUk"/></b></br> ${wordsUA}</p></div>
    </div>
</body>
</html>