package com.qalight.javacourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by kpl on 30.07.2014.
 */
// todo: use interface and implementation classes instead of implementation in enum
public enum UserRequestRouter {
    CONSOLIDATED_RESULT {
        @Override
        // todo: create result class instead of List<List<Map.Entry<String, Integer>>> the representation look very weird
        public List<List<Map.Entry<String, Integer>>> getCountedWords(String userUrlsString, String sortingParam) {

            StringUrlsParser stringUrlsParser = new StringUrlsParser();
            List<String> userUrlsList = stringUrlsParser.parseUrlList(userUrlsString);

            StringJoiner sj = new StringJoiner("");
            for (String url : userUrlsList) {
                // todo: use design patterns to check which converter to use based on request and apply converter
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

            return countedWordsList;
        }
    },

    EACH_URLS_RESULT {
        @Override
        // todo: create result class instead of List<List<Map.Entry<String, Integer>>> the representation look very weird
        public List<List<Map.Entry<String, Integer>>> getCountedWords(String userUrlsString, String sortingParam) {

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
            return countedWordsList;
        }
    };

    public abstract List<List<Map.Entry<String, Integer>>> getCountedWords(String userRequest, String sortingParam);
}
