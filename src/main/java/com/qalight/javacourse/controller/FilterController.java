package com.qalight.javacourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by emix on 8/30/14.
 */
@Controller
@RequestMapping("/")
public class FilterController {
    private static final String APP_VERSION = "app.version";
    private static final String FILTER_EN = "wordsEN";
    private static final String FILTER_UA = "wordsUA";
    private static final String FILTER_RU = "wordsRU";
    @Autowired
    private Environment environment;
    @RequestMapping("/filter")
    public String filter(ModelMap model) {
        model.addAttribute("version", environment.getProperty(APP_VERSION));
        model.addAttribute("wordsEN", environment.getProperty(FILTER_EN));
        model.addAttribute("wordsUA", environment.getProperty(FILTER_UA));
        model.addAttribute("wordsRU", environment.getProperty(FILTER_RU));
        return "filter";
    }
}
