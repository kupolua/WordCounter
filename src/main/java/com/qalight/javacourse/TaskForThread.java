package com.qalight.javacourse;

/**
 * Created by box on 05.07.2014.
 */
public class TaskForThread implements Runnable{

    private String url;
    private String sortingParam;

    Executor executor = new Executor();

    public TaskForThread(String url, String sortingParam) {
        this.url = url;
        this.sortingParam = sortingParam;
    }

    @Override
    public void run() {
        executor.goingToCountWords(url, sortingParam);
    }

}
