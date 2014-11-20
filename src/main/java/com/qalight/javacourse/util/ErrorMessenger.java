package com.qalight.javacourse.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ErrorMessenger {
    private static final String DEFAULT_LANGUAGE = "en";
    private static Locale userLocale;

    public static void setLocale(Locale locale) {
        userLocale = checkForUnsupportedLocale(locale);
    }

    private static Locale checkForUnsupportedLocale(Locale uncheckedLocale) {
        if (uncheckedLocale == null) {
            return new Locale(DEFAULT_LANGUAGE);
        }
        final String locale = String.valueOf(uncheckedLocale);
        final String supportedLocales = "en ru uk";
        final List<String> supportedLocaleList = Arrays.asList(supportedLocales.split(" "));
        Locale userLocale = new Locale(DEFAULT_LANGUAGE);
        for(String eachSupportedLocale : supportedLocaleList){
            if (locale.startsWith(eachSupportedLocale)){
                userLocale = uncheckedLocale;
                break;
            }
        }
        return userLocale;
    }

    public static String getErrorMsg(ErrorCode errorCode) {
        final String key = String.valueOf(errorCode);
        final String pathToBundle = "errorMsgs/Messages";
        ResourceBundle errorBundle = ResourceBundle.getBundle(pathToBundle, userLocale);
        return errorBundle.getString(key);
    }
}
