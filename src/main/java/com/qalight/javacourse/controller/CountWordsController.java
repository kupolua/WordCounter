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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
    public String getResult(@RequestParam String textCount) throws InterruptedException, ExecutionException, TimeoutException {
        CountWordsUserRequest request = new CountWordsUserRequest(textCount);
        //todo this must be deleted. Its here only for compare with old version.
//        WordCounterResultContainer result = getResultAndCatchException(request);
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(request);

        String jsonResult = resultPresentation.createResponse(result.getCountedResult());
        return jsonResult;
    }

    //todo: handle sorting & filtering params
    @RequestMapping(value = "/downloadPDF", method = RequestMethod.GET, produces = "application/pdf;charset=UTF-8")
    public ModelAndView getPdfResult(@RequestParam String textCount, @RequestParam String sortingOrder,
                                     @RequestParam String isFilterWords) {
        final String viewName = "pdfView";
        final String modelName = "calculatedWords";

        CountWordsUserRequest request = new CountWordsUserRequest(textCount, sortingOrder, isFilterWords);
        WordCounterResultContainer result = getResultAndCatchException(request);

        Map<String, Integer> resultMap = result.getCountedResult();
        return new ModelAndView(viewName, modelName, resultMap);
    }

    @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET, produces = "application/vnd.ms-excel;charset=UTF-8")
    public ModelAndView getExcelResult(@RequestParam String textCount,
                                       @RequestParam String sortingOrder,
                                       @RequestParam String isFilterWords) {
        final String viewName = "excelView";
        final String modelName = "calculatedWords";

        CountWordsUserRequest request = new CountWordsUserRequest(textCount, sortingOrder, isFilterWords);
        WordCounterResultContainer resultContainer = getResultAndCatchException(request);

        Map<String, Integer> resultMap = resultContainer.getCountedResult();
        return new ModelAndView(viewName, modelName, resultMap);
    }

    private WordCounterResultContainer getResultAndCatchException(CountWordsUserRequest request) {
        WordCounterResultContainer result = null;
        try {
            result = wordCounterService.getWordCounterResult(request);
        } catch (Throwable e) {
            LOG.error("error while processing request: " + e.getMessage(), e);
        }
        return result;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleExceptions(Throwable ex) {
        String errorMessage = resultPresentation.createErrorResponse(ex);
        LOG.error("Error while processing request: " + ex.getMessage(), ex);
        return errorMessage;
    }
}