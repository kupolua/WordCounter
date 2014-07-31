package com.qalight.javacourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kpl on 30.07.2014
 */
public enum UserRequestRouter {
    CONSOLIDATEDRESULT {
        @Override
        public List<List<Map.Entry<String, Integer>>> getCountedWords(String userUrlsstring, String sortingParam) {

            List<List<Map.Entry<String, Integer>>> countedWordsList = new ArrayList<List<Map.Entry<String, Integer>>>();
            System.out.println("CONSOLIDATEDRESULT");

            return countedWordsList;
        }
    },
    EACHURLSRESULT {
        @Override
        public List<List<Map.Entry<String, Integer>>> getCountedWords(String userUrlsstring, String sortingParam) {

            StringUrlsParser stringUrlsParser = new StringUrlsParser();
            List<String> userUrlsList = stringUrlsParser.parseUrslList(userUrlsstring);

            List<List<Map.Entry<String, Integer>>> countedWordsList = new ArrayList<List<Map.Entry<String, Integer>>>();
            for (String url : userUrlsList) {
                HTMLToTextConverter htmlToTextConverter = new HTMLToTextConverter();
                String plainText = htmlToTextConverter.getPlainTextByUrl(url);

                WordCounter wordCounter = new WordCounter();
                Map<String, Integer> countedWords = wordCounter.countWords(plainText);

                WordsSorter resultSorter = new WordsSorter();

//                List<Map.Entry<String, Integer>> sortedWords = WordsSorter.valueOf(sortingParam).getCountedWords(countedWords);
//                countedWordsList.add(sortedWords);
            }
            System.out.println("EACHURLSRESULT");  //todo: Remove this sout
            return countedWordsList;
        }
    };

    public abstract List<List<Map.Entry<String, Integer>>> getCountedWords(String userRequest, String sortingParam);
}
