package com.qalight.javacourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stkotok on 19.07.2014.
 */
public class StringUrlsParser {

    public List<String> urlList(String stringUrls) {

        final String delim = ", ";

        return new ArrayList<String>(Arrays.asList(stringUrls.split(delim)));
    }

}
