package com.qalight.javacourse.service;

import com.qalight.javacourse.util.TextRefiner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Properties;

@Component
public class WordFilter {
    private String keyWordsForFilterEN = "wordsEN";
    private String keyWordsForFilterRU = "wordsRU";
    private String keyWordsForFilterUA = "wordsUA";

    public List<String> removeUnimportantWords(List<String> refinedWords) {

        String wordsForFilter = getPropertiesValue(keyWordsForFilterEN) + " " + getPropertiesValue(keyWordsForFilterRU)
                + " " + getPropertiesValue(keyWordsForFilterUA);
        TextRefiner textRefiner = new TextRefiner();
        List<String> filter = textRefiner.refineText(wordsForFilter);
        refinedWords.removeAll(filter);
        return refinedWords;
    }

    public String getPropertiesValue(String key) {

        String propFileName = "wordcounter.properties";
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        try {
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            try {
                properties.load(reader);
            } finally {
                reader.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String value = properties.getProperty(key);
        return value;
    }
}
