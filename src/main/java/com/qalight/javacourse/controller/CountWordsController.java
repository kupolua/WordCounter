package com.qalight.javacourse.controller;

import com.qalight.javacourse.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class CountWordsController {
    private static final Logger LOG = LoggerFactory.getLogger(CountWordsController.class);
    private final WordCounterService wordCounterService;
    private final JsonResultPresentation resultPresentation;

    @Autowired
    public CountWordsController(@Qualifier("wordCounterService") WordCounterService wordCounterService, JsonResultPresentation resultPresentation) {
        this.wordCounterService = wordCounterService;
        this.resultPresentation = resultPresentation;
    }

    @RequestMapping(value = "/countWords", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getResult(@RequestParam String textCount, @RequestParam String sortingField,
                            @RequestParam String sortingOrder, @RequestParam String isFilterWords) {
        WordCounterResultContainer result = getResultAndCatchException(textCount, sortingField, sortingOrder, isFilterWords);
        String jsonResult = resultPresentation.createResponse(result.getCountedResult());
        return jsonResult;
    }

    //todo: handle sorting & filtering params
    @RequestMapping(value = "/downloadPDF", method = RequestMethod.GET, produces = "application/pdf;charset=UTF-8")
    public ModelAndView getPdfResult(@RequestParam String textCount, @RequestParam String sortingField,
                                     @RequestParam String sortingOrder, @RequestParam String isFilterWords) {
        final String VIEW_NAME = "pdfView";
        final String MODEL_NAME = "calculatedWords";
        WordCounterResultContainer result = getResultAndCatchException(textCount, sortingField, sortingOrder, isFilterWords);
        Map<String, Integer> sortedMap = result.getCountedResult();
        return new ModelAndView(VIEW_NAME, MODEL_NAME, sortedMap);
    }

    @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET, produces = "application/vnd.ms-excel;charset=UTF-8")
    public ModelAndView getExcelResult(@RequestParam String textCount, @RequestParam String sortingField,
                                       @RequestParam String sortingOrder, @RequestParam String isFilterWords) {
        final String VIEW_NAME = "excelView";
        final String MODEL_NAME = "calculatedWords";
        WordCounterResultContainer resultContainer = getResultAndCatchException(textCount, sortingField, sortingOrder, isFilterWords);
        Map<String, Integer> sortedMap = resultContainer.getCountedResult();
        return new ModelAndView(VIEW_NAME, MODEL_NAME, sortedMap);
    }

    private WordCounterResultContainer getResultAndCatchException(String dataSources, String sortingField, String sortingOrder, String isFilterWords) {
        WordCounterResultContainer result = null;
        try {
            result = wordCounterService.getWordCounterResult(dataSources, sortingField, sortingOrder, isFilterWords);
        } catch (Throwable e) {
            LOG.error("error while processing request: " + e.getMessage(), e);
        }
        return result;
    }

    private String logAndCreateErrorResponse(String dataTypeResponse, Throwable e) {
        String result;
        ResultPresentationService resultPresentationService = new ResultPresentationService();
        ResultPresentation resultPresentation = resultPresentationService.getResultPresentation(dataTypeResponse);
        result = resultPresentation.createErrorResponse(e.getMessage());
        return result;
    }
}
