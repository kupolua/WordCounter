package com.qalight.javacourse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kpl on 23.07.2014.
 */
public class HtmlFormReader {
    private static final Logger LOG = LoggerFactory.getLogger(HtmlFormReader.class);
    private static final String NO_HTML_SOURCE_FILE = "No Source File ";
    private static final String ERROR_WEB_PAGE = "Error.html";

    //todo kupolua: createResponse JUnit test
    public String readHtmlSourceFile(String fileName) {
        String readHtml;
        try {
            LOG.info("Reading source file " + fileName);
            byte[] htmlSources = Files.readAllBytes(Paths.get("./src/main/resources/" + fileName));
            readHtml = new String(htmlSources);
        } catch (IOException e) {
            LOG.error("Source file <" + fileName + "> cannot be read.", e);
            readHtml = readErrorWebPage();
        }
        return readHtml;
    }

    private String readErrorWebPage() {
        String readErrorHtml;
        try {
            byte[] errorHtmlSources = Files.readAllBytes(Paths.get("./src/main/resources/" + ERROR_WEB_PAGE));
            readErrorHtml = new String(errorHtmlSources);
        } catch (IOException e) {
            LOG.error("Error page source file <" + ERROR_WEB_PAGE + "> cannot be read.", e);
            readErrorHtml = NO_HTML_SOURCE_FILE;
        }
        return readErrorHtml;
    }
}