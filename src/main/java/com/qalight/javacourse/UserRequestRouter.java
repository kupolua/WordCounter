package com.qalight.javacourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by kpl on 30.07.2014.
 */
public enum UserRequestRouter {
    CONSOLIDATEDRESULT {
        @Override
        public List<List<Map.Entry<String, Integer>>> getCountedWords(String userUrlsString, String sortingParam) {

            StringUrlsParser stringUrlsParser = new StringUrlsParser();
            List<String> userUrlsList = stringUrlsParser.parseUrslList(userUrlsString);

            StringJoiner sj = new StringJoiner("");
            for (String url : userUrlsList) {
                HTMLToTextConverter htmlToTextConverter = new HTMLToTextConverter();
                String plainText = htmlToTextConverter.getPlainTextByUrl(url);

                UnrefinedTextFilter unrefinedTextFilter = new UnrefinedTextFilter();
                String refinedText = unrefinedTextFilter.refineText(plainText);

                sj.add(refinedText).add(" ");
            }
            //todo: summedRefinedText refactor to consolidatedRefinedText
            String summedRefinedText = sj.toString();

            WordCounter wordCounter = new WordCounter();
            Map<String, Integer> countedWords = wordCounter.countWords(summedRefinedText);

            List<List<Map.Entry<String, Integer>>> countedWordsList = new ArrayList<List<Map.Entry<String, Integer>>>();
            List<Map.Entry<String, Integer>> sortedWords = WordsSorter.valueOf(sortingParam).getSortedWords(countedWords);

            countedWordsList.add(sortedWords);

            return countedWordsList;
        }
    },
    EACHURLSRESULT {
        @Override
        public List<List<Map.Entry<String, Integer>>> getCountedWords(String userUrlsString, String sortingParam) {

            StringUrlsParser stringUrlsParser = new StringUrlsParser();
            List<String> userUrlsList = stringUrlsParser.parseUrslList(userUrlsString);

            List<List<Map.Entry<String, Integer>>> countedWordsList = new ArrayList<List<Map.Entry<String, Integer>>>();
            for (String url : userUrlsList) {
                HTMLToTextConverter htmlToTextConverter = new HTMLToTextConverter();
                String plainText = htmlToTextConverter.getPlainTextByUrl(url);

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
