package com.qalight.javacourse;

import com.google.gson.JsonObject;
import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.core.WordsSorter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by kpl on 08.08.2014.
 */
public class ConsolidatedResultGetter implements CountedWordsGetter{

    @Override
    public JsonObject getCountedWords(String userUrlsString, String sortingParam) {
        StringUrlsParser stringUrlsParser = new StringUrlsParser();
        List<String> userUrlsList = stringUrlsParser.parseUrlList(userUrlsString);

        StringJoiner sj = new StringJoiner("");
        for (String url : userUrlsList) {
            // todo kupolua: use design patterns to check which converter to use based on request and apply converter
            ToStringConverter htmlToTextConverter = new HtmlToStringConverter();
            String plainText = htmlToTextConverter.convertToString(url);
            UnrefinedTextFilter unrefinedTextFilter = new UnrefinedTextFilter();
            String refinedText = unrefinedTextFilter.refineText(plainText);

            sj.add(refinedText).add(" ");
        }
        String consolidatedRefinedText = sj.toString();

        WordCounter wordCounter = new WordCounter();
        Map<String, Integer> countedWords = wordCounter.countWords(consolidatedRefinedText);

        List<List<Map.Entry<String, Integer>>> countedWordsList = new ArrayList<List<Map.Entry<String, Integer>>>();
        List<Map.Entry<String, Integer>> sortedWords = WordsSorter.valueOf(sortingParam).getSortedWords(countedWords);

        countedWordsList.add(sortedWords);

        return new ResponseObjectCreator().createResponseObject(countedWordsList, userUrlsList);
    }
}
