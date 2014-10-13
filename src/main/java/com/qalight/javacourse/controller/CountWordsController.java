package com.qalight.javacourse.controller;

import com.qalight.javacourse.service.JsonResultPresentation;
import com.qalight.javacourse.service.WordCounterResultContainer;
import com.qalight.javacourse.service.WordCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class CountWordsController {
    private static final Logger LOG = LoggerFactory.getLogger(CountWordsController.class);
    private final WordCounterService wordCounterService;
    private final JsonResultPresentation resultPresentation;

    @Autowired
    public CountWordsController(@Qualifier("wordCounterService") WordCounterService wordCounterService,
                                JsonResultPresentation resultPresentation) {
        this.wordCounterService = wordCounterService;
        this.resultPresentation = resultPresentation;
    }

    @RequestMapping(value = "/countWords", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getResult(@RequestParam String textCount) throws Throwable {
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(textCount);
        String jsonResult = resultPresentation.createResponse(result.getCountedResult());
        return jsonResult;
    }

    //todo: handle sorting & filtering params
    @RequestMapping(value = "/downloadPDF", method = RequestMethod.GET, produces = "application/pdf;charset=UTF-8")
    public ModelAndView getPdfResult(@RequestParam String textCount, @RequestParam String sortingField,
                                     @RequestParam String sortingOrder, @RequestParam String isFilterWords) {
        final String VIEW_NAME = "pdfView";
        final String MODEL_NAME = "calculatedWords";
        WordCounterResultContainer result = getResultAndCatchException(textCount);
        Map<String, Integer> resultMap = result.getCountedResult();
        return new ModelAndView(VIEW_NAME, MODEL_NAME, resultMap);
    }

    //todo: handle sorting & filtering params
    @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET, produces = "application/vnd.ms-excel;charset=UTF-8")
    public ModelAndView getExcelResult(@RequestParam String textCount, @RequestParam String sortingField,
                                       @RequestParam String sortingOrder, @RequestParam String isFilterWords) {
        final String VIEW_NAME = "excelView";
        final String MODEL_NAME = "calculatedWords";
        WordCounterResultContainer resultContainer = getResultAndCatchException(textCount);
        Map<String, Integer> resultMap = resultContainer.getCountedResult();
        return new ModelAndView(VIEW_NAME, MODEL_NAME, resultMap);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleExceptions(Throwable ex) {
        String errorMessage = resultPresentation.createErrorResponse(ex);
        LOG.error("Error while processing request: " + ex.getMessage(), ex);
        return errorMessage;
    }

    //todo if we use handleExceptions may be delete it?
    private WordCounterResultContainer getResultAndCatchException(String dataSources) {
        WordCounterResultContainer result = null;
        try {
            result = wordCounterService.getWordCounterResult(dataSources);
        } catch (Throwable e) {
            LOG.error("error while processing request: " + e.getMessage(), e);
        }
        return result;
    }

}
