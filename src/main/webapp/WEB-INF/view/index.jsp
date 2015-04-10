﻿﻿﻿<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="robots" content="noindex,nofollow"/>
    <title>Word Counter ${version}</title>

    <link href="css/wordcounter.css" rel="stylesheet" type="text/css"/>
    <link href="css/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="css/dataTables.foundation.css" rel="stylesheet" type="text/css"/>
    <link href="css/foundation.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/modal-window.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="css/tabs.css" rel="stylesheet" type="text/css" media="screen" />

    <script src="js/wordcloud.js" type="text/javascript"></script>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery.simplemodal.js" type="text/javascript"></script>
    <script src="js/modal-window.js" type="text/javascript"></script>
    <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="js/jquery-ui.min.js" type="text/javascript"></script>
    <script src="js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="js/jquery.fileDownload.js" type="text/javascript"></script>
    <script src="js/dataTables.foundation.js" type="text/javascript"></script>
    <script src="js/spinner.js" type="text/javascript"></script>
    <script src="js/wordcounter.js"  type="text/javascript"></script>

</head>

<body id="home">

<div id="allContent">
    <div id="logo">
        <img src="img/wc-6.png" alt="WordCounterLogo">
    </div>
    <div id="navi">
            <ul>
              <li><a href="/WordCounter" id="homeNav"><spring:message code="about.home"/></a></li>
              <li><a href="about" id="aboutUsLink"><spring:message code="index.aboutLink"/></a></li>
            </ul>
    </div>
<div id="headingText">
    <div id="welcomeText">
        <div id="p1"><spring:message code="index.welcomeHeader"/></div>
        <div id="p2"><spring:message code="index.welcomeText"/></div>
            <a href="about"><spring:message code="index.showMore"/></a>
    </div>
 </div>
    <div id="container">
        <div class="tabs">
            <input id="tab1" type="radio" name="tabs" checked>
            <label for="tab1" title="inputText" onclick="clearRequest('urlCount', 'crawlContainer')">Text</label>

            <input id="tab2" type="radio" name="tabs">
            <label for="tab2" title="inputUrls" onclick="clearRequest('textCount', 'filterContainer')">URLs</label>

            <section id="content1">
                <textarea id="textCount" name="textCount" cols="125" rows="7" onclick="showFilteredWords()"></textarea>
                <%--<div id="filterContainer1" class="filterContainer1">--%>
                    <%--<div id="showFilter1" class="showFilter1">--%>
                     <div id="filterContainer">
                        <input id="filterCheck" type="checkbox" name="filterCheck" value="true" onclick="showFilteredWords()"/>
                        <spring:message code="index.bUnFilter"/>
                    <%--</div>--%>
                    <%--<div id="showFilter" class="showFilter1">--%>
                        <a href="#" id="filterShow" class="osx"><spring:message code="index.contentLocalLang"/></a>
                     </div>
                    <%--</div>--%>
                <%--</div>--%>
            </section>
            <section id="content2">
                <textarea id="urlCount" name="urlCount" cols="125" rows="7" onclick="showCrawl()"></textarea>
                <div id="crawlContainer">
                    <div>
                        <input type="checkbox" name="crawlDepth" value="true" onclick="showCrawlScope()" id="crawlDepthInput"/>
                        <b><spring:message code="index.crawlDepth"/></b>
                    </div>
                    <%--<div id="crawler" class="crawlContainer">--%>
                        <div id="crawlDepth" class="crawlLeft">
                            <input type="checkbox" checked disabled="disabled" name="crawlLocalDomain" value="true" id="crawlLocalDomainInput"/>
                            <spring:message code="index.crawlScope"/>
                        </div>
                    <%--</div>--%>
                    <div id="crawlLocalDomain">
                        <div id="crawlScopeClarification"><spring:message code="index.crawlScopeClarification"/></div>
                    </div>
                </div>
            </section>
        </div>
    </div>
<div id="wordCounterContent">
    <div id="webFormContainer" name="webFormContainer">
            <div id="spinnerAnchor"></div>
            <div id="CountWords" class="bottoms">
                <div class="leftBottoms">
                    <input id="getCountedWords" type="button" value="<spring:message code="index.bCountWords"/>"/>
                    <input id="reloadWordCounter" type="button" value="<spring:message code="index.bReloadWordCounter"/>" onclick="location.reload()"/>
                </div>
                <div class="rightExport">
                    <div id="exportBottoms" class="exportBottoms">
                        <div id="saveAsPdf">
                            <input id="getPdf" type="image" src="img/pdf-32.png" alt="<spring:message code="index.saveAsPdf"/>" title="<spring:message code="index.saveAsPdf"/>"/>
                        </div>
                        <div id="saveAsXls">
                            <input id="getXls" type="image" src="img/excel-32.png" alt="<spring:message code="index.saveAsXls"/>" title="<spring:message code="index.saveAsXls"/>"/>
                        </div>
                        <div id="textExport">
                            <spring:message code="index.textExport"/>
                        </div>

                    </div>
                </div>
            </div>
        <div id="dataContainer">
        <fieldset>
            <legend><spring:message code="index.response"/></legend>
            <div id="errorsSpoiler" class="spoiler_open" tabindex="1">
                <div id="errors" class="spoiler_desc">
                    <div id="errorsContainer" name="errorsContainer"></div>
                </div>
                <span class="spoiler_title"><spring:message code="index.errorsTitleOpen"/></span>
                <span id="spoiler_close" tabindex="0" class="spoiler_close"><spring:message code="index.errorsTitleClose"/></span>
            </div>
            <div id="responseContainer">
                <div id="messageCounter"><spring:message code="index.noCount"/></div>
                <div id="wordCloud" class="container">
                    <div id="table" class="left">
                        <div id="wordCounterResponse">
                            <table id="countedWords" cellpadding="0" cellspacing="0" border="0" class="display"></table>
                            <div id="wordsColumNameAnchor"><spring:message code="index.wordsColumName"/></div>
                            <div id="countColumNameAnchor"><spring:message code="index.countColumName"/></div>
                        </div>
                    </div>
                    <div id="cloud" class="right">
                        <div id="wordCloudData">
                            <div id="showModalCloud" class="showModalCloud"><a href="#" id="showWordCloudLink" class="osx-wordCloud" onclick="showModalWordCloud()"><spring:message code="index.openModal"/></a></div>
                            <canvas id="canvas_cloud"></canvas>
                        </div>
                    </div>
                </div>
            </div>
                <div id="statisticContainer" class="visualization">
                   <div id="spanContainer">
                        <span id="spoil"><spring:message code="index.statisticTitleOpen"/></span>
                   </div>
                   <div id="statTable">
                        <table id="wordStatistic" cellpadding="0" cellspacing="0" border="0" class="display">
                            <tr>
                                <td><b><spring:message code="index.statisticTitleName"/></b></td>
                                <td><b><spring:message code="index.statisticTitleValue"/></b></td>
                            </tr>
                            <tr>
                                <td><spring:message code="index.statisticTotalWords"/></td>
                                <td><div id="statisticTotalWords"></div></td>
                            </tr>
                            <tr>
                                <td><spring:message code="index.statisticUniqueWords"/></td>
                                <td><div id="statisticUniqueWords"></div></td>
                            </tr>
                            <tr>
                                <td><spring:message code="index.statisticTotalCharacters"/></td>
                                <td><div id="statisticTotalCharacters"></div></td>
                            </tr>
                            <tr>
                                <td><spring:message code="index.statisticCharactersWithoutSpaces"/></td>
                                <td><div id="statisticCharactersWithoutSpaces"></div></td>
                            </tr>
                        </table>
                   </div>
                    <%--<div id="visualization" class="visualization">--%>
                    <div id="wordConnection">
                        <a href="topWordsRelationDiagram" target="_blank" onclick="setWordConnectionData()">
                            <img src="img/related.png" title="<spring:message code="index.wordConnection"/>" alt="<spring:message code="index.wordConnection"/>"/>
                        </a>
                    </div>
                    <div id="urlTree">
                        <a href="urlTree" target="_blank" onclick="setUrlTreeData()">
                            <img src="img/tree.png" alt="<spring:message code="index.urlTree"/>" title="<spring:message code="index.urlTree"/>" />
                        </a>
                    </div>
                    <%--</div>--%>
                </div>
                <%--<div id="filterContainer" class="filterContainer">--%>
                    <%--<div class="filterButton">--%>
                    <%--</div>--%>
                <%--</div>--%>
        </fieldset>
        </div>
    </div>
</div>
    <div id="osx-modal-content">
    <div id="osx-modal-title"><spring:message code="index.modalTitle"/></div>
    <div class="close"><a href="#" class="simplemodal-close">x</a></div>
    <div id="osx-modal-data">
        <h2><spring:message code="index.modalHeader"/></h2>
        <div id="wordsFilter">${filter}</div>
        <p><button class="simplemodal-close"><spring:message code="index.modalClose"/></button></p>
    </div>
    </div>
</div>
</div>
    <div id="osx-modal-content-wordCloud">
    <div id="osx-modal-title-wordCloud"><spring:message code="index.wordCloudTitle"/></div>
    <div id="wordCloudModalClose" class="close"><a href="#" class="simplemodal-close">x</a></div>
    <div id="osx-modal-data-wordCloud">
        <div id="wordCloudModal">
            <canvas id="canvas_cloudModal"></canvas>
        </div>
    </div>
    </div>
</div>
<div id="isWordCloudModalClosed"></div>
<div id="wordCloudRunuble"></div>

<footer id="wordFooter">
    <div><spring:message code="index.footer"/></div>
    <div id="version">V. ${version}</div>
</footer>
</body>
</html>