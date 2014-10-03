package com.qalight.javacourse.controller;

import com.qalight.javacourse.service.ResultPresentation;
import com.qalight.javacourse.service.ResultPresentationService;
import com.qalight.javacourse.service.WordCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/countWords")
public class CountWordsController {
    private static final Logger LOG = LoggerFactory.getLogger(CountWordsController.class);
    private final WordCounterService wordCounterService;

    @Autowired
    public CountWordsController(@Qualifier("wordCounterService") WordCounterService wordCounterService) {
        this.wordCounterService = wordCounterService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getResult(@RequestParam String userUrlsList) {
        final String result = getResultAndCatchException(userUrlsList);
        return result;
    }

    private String getResultAndCatchException(String dataSources) {
        String result;
        try {
            result = wordCounterService.getWordCounterResult(dataSources);
        } catch (Throwable e) {
            LOG.error("error while processing request: " + e.getMessage(), e);
            result = logAndCreateErrorResponse("dataTypeResponse", e);
        }
        return result;
    }
    //todo: why we use dataTypeResponse?
    private String logAndCreateErrorResponse(String dataTypeResponse, Throwable e) {
        String result;
        ResultPresentationService resultPresentationService = new ResultPresentationService();
        ResultPresentation resultPresentation = resultPresentationService.getResultPresentation(dataTypeResponse);
        result = resultPresentation.createErrorResponse(e.getMessage());
        return result;
    }
}
