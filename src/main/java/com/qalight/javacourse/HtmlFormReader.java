package com.qalight.javacourse;

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

    public String readFile(String fileName) {
        String noHTMLSourceFile = "No Source File ";
        String errorWebpage = "Error.html";

        try {
            LOG.info("Reading source file " + fileName);
            byte[] htmlSources = Files.readAllBytes(Paths.get("./src/main/resources/" + fileName));
            return new String(htmlSources);
        } catch (IOException e) {
            LOG.error("Source file <" + fileName + "> cannot be read.", e);
            try {
                byte[] textSources = Files.readAllBytes(Paths.get("./src/main/resources/" + errorWebpage));
                return new String(textSources);
            } catch (IOException e1){
                LOG.error("Error source file <" + errorWebpage + "> cannot be read.", e1);
            }
        }
        return noHTMLSourceFile;
    }
}
