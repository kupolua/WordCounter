package com.qalight.javacourse;

/**
 * Created by kpl on 08.08.2014.
 */
public class WordCounterFactory {
    //todo kupolua:  give meaningful name to getTypeRequest
    public CountedWordsGetter getTypeRequest(String typeStatisticResult) {
        //todo kupolua:  do not use if-else use OOP. See the article on G+
        if (typeStatisticResult == null) {
            return null;
        }
        if (typeStatisticResult.equalsIgnoreCase("CONSOLIDATED_RESULT")) {
            return new ConsolidatedResultGetter();
        } else if (typeStatisticResult.equalsIgnoreCase("SEPARATED_RESULT")){
            return new SeparatedResultGetter();
        }

        return null;
    }
}
