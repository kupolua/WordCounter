package com.qalight.javacourse.service;

/**
 * Created by pavelkulakovsky on 11.08.14.
 */
public class HtmlTextTypeIml implements TextType {
    //todo: validate textType by file extention
    private static final String textTypes = "htm";
    private static final String textType = "html";

    @Override
    public Boolean isEligable(String dataSourceLink) {
        return dataSourceLink.endsWith(textTypes);
    }

    @Override
    public String getTextType() {
        return textType;
    }
}
