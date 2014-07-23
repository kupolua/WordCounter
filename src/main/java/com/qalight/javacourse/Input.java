package com.qalight.javacourse;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by box on 07.06.2014.
 */
public class Input {
    //test
    public List<String> dataIn(){
        List<String> urlList = new ArrayList<String>();
        String choice;
        Scanner choiceIn = new Scanner(System.in);
        boolean stopUrlGathering = false;
        do {
            urlList.add(validateUrl());
            System.out.println("Do you want enter one more? (y/n)");
            choice = choiceIn.nextLine();
            if (choice.toLowerCase().contains("n")) {
                stopUrlGathering = true;
            }
        } while (!stopUrlGathering);
        return urlList;
    }

    private String validateUrl(){
        String http = "http://";
        String url;
        Scanner urlIn = new Scanner(System.in);
        UrlValidator urlValidator = new UrlValidator();
        do{
            System.out.print("Enter URL : http://www.");
            url = urlIn.nextLine();
        } while(!urlValidator.isValid(http + url));

        return url;
    }
}
