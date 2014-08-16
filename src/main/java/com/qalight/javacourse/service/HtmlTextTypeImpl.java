package com.qalight.javacourse.service;


public class HtmlTextTypeImpl implements TextType {
    private final String[] TYPES = {".xml",".pdf"};
    private static final String textType = "html";

    @Override
    public Boolean isEligible(String dataSourceLink) {
        boolean isEligible = true;
        for(String type : TYPES){
            if(dataSourceLink.endsWith(type)){
                isEligible = false;
                break;
            }
        }
        return isEligible;
    }

    @Override
    public String getTextType() {
        return textType;
    }
}
