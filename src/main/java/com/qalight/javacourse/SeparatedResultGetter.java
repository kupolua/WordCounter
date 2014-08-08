package com.qalight.javacourse;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kpl on 08.08.2014.
 */
public class SeparatedResultGetter implements CountedWordsGetter {
    @Override
    public JsonObject getCountedWords(String userUrlsString, String sortingParam) {
        StringUrlsParser stringUrlsParser = new StringUrlsParser();
        List<String> userUrlsList = stringUrlsParser.parseUrlList(userUrlsString);

        List<List<Map.Entry<String, Integer>>> countedWordsList = new ArrayList<List<Map.Entry<String, Integer>>>();
        for (String url : userUrlsList) {
            // todo: use design patterns to check which converter to use based on request and apply converter
            // todo: do not create new converter in 'for' block. initialize it in constructor
            ToStringConverter htmlToTextConverter = new HtmlToStringConverter();
            String plainText = htmlToTextConverter.convertToString(url);

            UnrefinedTextFilter unrefinedTextFilter = new UnrefinedTextFilter();
            String refinedText = unrefinedTextFilter.refineText(plainText);

            WordCounter wordCounter = new WordCounter();
            Map<String, Integer> countedWords = wordCounter.countWords(refinedText);

            List<Map.Entry<String, Integer>> sortedWords = WordsSorter.valueOf(sortingParam).getSortedWords(countedWords);
            countedWordsList.add(sortedWords);



        }
        return new ResponseObjectCreator().createResponseObject(countedWordsList, userUrlsList);
    }
}
