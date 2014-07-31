package com.qalight.javacourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kpl on 30.07.2014.
 */
public enum UserRequestRouter {
    CONSOLIDATEDRESULT {
        @Override
        public List<List<Map.Entry<String, Integer>>> getCountedWords(String userRequest){
            List<List<Map.Entry<String, Integer>>> countedWords = new ArrayList<List<Map.Entry<String, Integer>>>();
            System.out.println("CONSOLIDATEDRESULT");


            return countedWords;
        }
    },
    EACHURLSRESULT {
        @Override
        public List<List<Map.Entry<String, Integer>>> getCountedWords(String userRequest){
            List<List<Map.Entry<String, Integer>>> countedWords = new ArrayList<List<Map.Entry<String, Integer>>>();
            System.out.println("EACHURLSRESULT");


            return countedWords;
        }
    };
    public abstract List<List<Map.Entry<String, Integer>>> getCountedWords(String userRequest);
}
