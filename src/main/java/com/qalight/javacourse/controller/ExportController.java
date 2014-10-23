package com.qalight.javacourse.controller;

import com.qalight.javacourse.service.WordCounterResultContainer;
import com.qalight.javacourse.service.WordCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ExportController {
    private final WordCounterService wordCounterService;

    @Autowired
    public ExportController(@Qualifier("wordCounterService") WordCounterService wordCounterService) {
        this.wordCounterService = wordCounterService;
    }

    @RequestMapping(value = "/downloadPDF", method = RequestMethod.POST, produces = "application/pdf;charset=UTF-8")
    public ModelAndView getPdfResult(@RequestParam String textCount,
                                     @RequestParam String sortingOrder,
                                     @RequestParam String isFilterWords) throws  Throwable {
        final String viewName = "pdfView";
        final String modelName = "calculatedWords";

        CountWordsUserRequest request = new CountWordsUserRequest(textCount, sortingOrder, isFilterWords);
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(request);

        Map<String, Integer> resultMap = result.getCountedResult();
        return new ModelAndView(viewName, modelName, resultMap);
    }
    @RequestMapping(value = "/downloadExcel", method = RequestMethod.POST, produces = "application/vnd.ms-excel;charset=UTF-8")
    public ModelAndView getExcelResult(@RequestParam String textCount,
                                       @RequestParam String sortingOrder,
                                       @RequestParam String isFilterWords) throws Throwable {
        final String viewName = "excelView";
        final String modelName = "calculatedWords";

        CountWordsUserRequest request = new CountWordsUserRequest(textCount, sortingOrder, isFilterWords);
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(request);

        Map<String, Integer> resultMap = result.getCountedResult();
        return new ModelAndView(viewName, modelName, resultMap);
    }

    @ExceptionHandler
    public ModelAndView handleException(Throwable e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", e.getMessage());
        return model;
    }
}
