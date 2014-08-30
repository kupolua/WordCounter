package com.qalight.javacourse.controller;

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
    public static String VERSION = "0.2";

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.addAttribute("version", VERSION);
        return "index";
    }

}
