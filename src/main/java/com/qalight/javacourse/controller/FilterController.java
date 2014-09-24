package com.qalight.javacourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${app.version}")    private String version;
    @Value("${wordsEN}")        private String filterEN;
    @Value("${wordsUA}")        private String filterUA;
    @Value("${wordsRU}")        private String filterRU;

    @Autowired
    private Environment environment;
    @RequestMapping("/filter")
    public String filter(ModelMap model) {
        model.addAttribute("version", version);
        model.addAttribute("wordsEN", filterEN);
        model.addAttribute("wordsUA", filterUA);
        model.addAttribute("wordsRU", filterRU);
        return "filter";
    }
}
