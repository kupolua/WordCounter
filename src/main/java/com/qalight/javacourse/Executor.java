package com.qalight.javacourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by box on 07.06.2014.
 */
public class Executor {

    public static void main(String[] args) {
        Executor e = new Executor();
        e.inputUrlsAndStartThreads();
    }

    public void inputUrlsAndStartThreads() {
        Input input = new Input();
        List<String> urlList = input.dataIn();
        String sortingParam = "VD"; // Временная заглушка. Насколько понимаю, ввод с консоли мы совсем отключим.

        ExecutorService service = Executors.newCachedThreadPool();
        for (String url : urlList) {
            service.submit(new TaskForThread(url, sortingParam));
        }
        service.shutdown();
    }

    public List<Map<String, Integer>> inputUrls(String userUrls, String sortingParam) {

        StringUrlsParser stringUrlsParser = new StringUrlsParser();

        List<String> urlList = stringUrlsParser.urlList(userUrls);
        List<Map<String, Integer>> urlsList = new ArrayList<Map<String, Integer>>();
        for (String url : urlList) {
            Executor executor = new Executor();
            urlsList.add(executor.goingToCountWords(url, sortingParam));
        }
        return urlsList;
    }

    protected Map<String, Integer> goingToCountWords(String url, String sortingParam) {
        PlainTextGetter iProcessing = new PlainTextGetter();
        String plainText = iProcessing.getPlainTextByUrl(url);

        WordCounter wordCounter = new WordCounter();
        Map<String, Integer> countedWords = wordCounter.countWords(plainText);

        WordCounterResultSorter resultSorter = new WordCounterResultSorter();
        Map<String, Integer> sortedResults = resultSorter.sortWords(countedWords, sortingParam);

        return sortedResults;

//        DatabaseInputLogic databaseInputLogic = new DatabaseInputLogic();
//        WordFilter wordFilter = new WordFilter();
//        String parsedUrl = wordFilter.parseUrlForDb(url);
//        databaseInputLogic.writeToH2db(list, parsedUrl);
    }
}
