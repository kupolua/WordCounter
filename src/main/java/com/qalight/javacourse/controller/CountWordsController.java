package com.qalight.javacourse.controller;

import com.qalight.javacourse.service.ResultPresentation;
import com.qalight.javacourse.service.ResultPresentationImpl;
import com.qalight.javacourse.service.WordCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/countWords")
public class CountWordsController {
    private static final Logger LOG = LoggerFactory.getLogger(CountWordsController.class);

    @Autowired
    private WordCounterService wordCounterService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getResult (@RequestParam String userRequest, @RequestParam String dataTypeResponse) {

        final String result = getResultAndCatchException(userRequest, dataTypeResponse);

        return result;
    }

    private String getResultAndCatchException(String dataSources, String dataTypeResponse) {
        String result;
        try {
            result = wordCounterService.getWordCounterResult(dataSources, dataTypeResponse);
        } catch (Throwable e) {
            LOG.error("error while processing request: " + e.getMessage(), e);
            ResultPresentationImpl resultPresentationImpl = new ResultPresentationImpl();
            ResultPresentation resultPresentation = resultPresentationImpl.getResultPresentation(dataTypeResponse);

            result = resultPresentation.createErrorResponse(e.getMessage());
        }
        return result;
    }
}
