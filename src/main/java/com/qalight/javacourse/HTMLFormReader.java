package com.qalight.javacourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kpl on 23.07.2014.
 */
public class HTMLFormReader {
    private static final Logger LOG = LoggerFactory.getLogger(HTMLFormReader.class);

    public String readHtmlSourceFile(String fileName) {
        final String NO_HTML_SOURCE_FILE = "No Source File ";
        final String ERROR_WEBPAGE = "Error.html";

        try {
            LOG.info("Reading source file " + fileName);
            byte[] htmlSources = Files.readAllBytes(Paths.get("./src/main/resources/" + fileName));
            return new String(htmlSources);
        } catch (IOException e) {
            LOG.error("Source file <" + fileName + "> cannot be read.", e);
            try {
                byte[] textSources = Files.readAllBytes(Paths.get("./src/main/resources/" + ERROR_WEBPAGE));
                return new String(textSources);
            } catch (IOException e1){
                LOG.error("Error source file <" + ERROR_WEBPAGE + "> cannot be read.", e1);
            }
        }
        return NO_HTML_SOURCE_FILE;
    }
}