package com.qalight.javacourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by box on 07.06.2014.
 */
public class Executor {

    public List<List<Map.Entry<String, Integer>>> getCountedWords(String userUrls, String sortingParam) {

        StringUrlsParser stringUrlsParser = new StringUrlsParser();

        List<String> userUrlsList = stringUrlsParser.parseUrslList(userUrls);

        List<List<Map.Entry<String, Integer>>> countedWords = new ArrayList<List<Map.Entry<String, Integer>>>();
        for (String url : userUrlsList) {
            Executor executor = new Executor();
            countedWords.add(executor.goingToCountWords(url, sortingParam));
        }
        return countedWords;
    }

    // todo: give meaningful name goingToCountWords
    protected List<Map.Entry<String, Integer>> goingToCountWords(String url, String sortingParam) {

        HTMLToTextConverter htmlToTextConverter = new HTMLToTextConverter();
        String plainText = htmlToTextConverter.getPlainTextByUrl(url);

        WordCounter wordCounter = new WordCounter();
        Map<String, Integer> countedWords = wordCounter.countWords(plainText);
        WordsSorter resultSorter = new WordsSorter();
        List<Map.Entry<String, Integer>> list = resultSorter.getSortedWords(countedWords, sortingParam);

        return list;
    }
}
