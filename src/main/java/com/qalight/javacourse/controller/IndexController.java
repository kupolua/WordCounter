package com.qalight.javacourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by emix on 8/30/14.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private static final String APP_VERSION = "app.version";

    @Autowired
    private Environment environment;
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.addAttribute("version", environment.getProperty(APP_VERSION));
        return "index";
    }
    @RequestMapping("/About")
    public String about(ModelMap model) {
        model.addAttribute("version", environment.getProperty(APP_VERSION));
        return "about";
    }
}
