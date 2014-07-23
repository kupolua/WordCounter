package com.qalight.javacourse;

import java.net.URISyntaxException;

/**
 * Created by box on 05.07.2014.
 */
public class TaskForThread implements Runnable{
    private String url;
    Executor executor = new Executor();
    public TaskForThread(String url){
        this.url = url;
    }

    @Override
    public void run() {
        try {
            executor.goingToCountWords(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
