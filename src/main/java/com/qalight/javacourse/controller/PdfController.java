package com.qalight.javacourse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PdfController {
    @RequestMapping(value = "/downloadPDF", method = RequestMethod.GET)
    public ModelAndView downloadPdf(@RequestParam String userUrlsList, @RequestParam String dataTypeResponse) {
        Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("world", 3);
            put("love", 5);
            put("плейстейшн", 5);
        }};
        return new ModelAndView("pdfView", "calculatedWords", expectedResult);
    }
}
