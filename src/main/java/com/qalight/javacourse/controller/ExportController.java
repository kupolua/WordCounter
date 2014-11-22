package com.qalight.javacourse.controller;

import com.qalight.javacourse.service.WordCounterResultContainer;
import com.qalight.javacourse.service.WordCounterService;
import com.qalight.javacourse.util.ErrorMessenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
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
                                     @RequestParam String isFilterWords,
                                     HttpServletRequest servletRequest) throws  Throwable {
        final String viewName = "pdfView";
        setErrorLocale(servletRequest);
        CountWordsUserRequest request = new CountWordsUserRequestImpl(textCount, sortingOrder, isFilterWords);
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(request);

        ModelAndView modelAndView = getModelAndView(viewName, result);
        return modelAndView;
    }

    @RequestMapping(value = "/downloadExcel", method = RequestMethod.POST, produces = "application/vnd.ms-excel;charset=UTF-8")
    public ModelAndView getExcelResult(@RequestParam String textCount,
                                       @RequestParam String sortingOrder,
                                       @RequestParam String isFilterWords,
                                       HttpServletRequest servletRequest) throws Throwable {
        final String viewName = "excelView";
        setErrorLocale(servletRequest);
        CountWordsUserRequest request = new CountWordsUserRequestImpl(textCount, sortingOrder, isFilterWords);
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(request);

        ModelAndView modelAndView = getModelAndView(viewName, result);
        return modelAndView;
    }

    private void setErrorLocale(HttpServletRequest servletRequest) {
        Locale userLocale = servletRequest.getLocale();
        ErrorMessenger.setLocale(userLocale);
    }

    private ModelAndView getModelAndView(String viewName, WordCounterResultContainer result) {
        Map<String, Integer> resultMap = result.getCountedResult();
        List<String> errorList = result.getErrors();
        ModelAndView modelAndView = new ModelAndView(viewName, "calculatedWords", resultMap);
        modelAndView.addObject("errorList", errorList);
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView handleException(Throwable e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", e.getMessage());
        return model;
    }
}
