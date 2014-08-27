package com.qalight.javacourse.service;


public class HtmlTextTypeImpl implements TextType {
    private static final String[] TYPES = {".pdf"};

    @Override
    public boolean isEligible(String dataSourceLink) {
        boolean isEligible = true;
        for(String type : TYPES){
            if(dataSourceLink.endsWith(type)){
                isEligible = false;
                break;
            }
        }
        return isEligible;
    }
}
