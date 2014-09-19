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
public class TextController {
    private static final String APP_VERSION = "app.version";
    @Autowired
    private Environment environment;
    @RequestMapping("/text")
    public String text(ModelMap model) {
        model.addAttribute("version", environment.getProperty(APP_VERSION));
        return "text";
    }
}
