package com.qalight.javacourse.controller;

import com.qalight.javacourse.service.ErrorDataContainer;
import com.qalight.javacourse.util.ErrorMessenger;
import com.qalight.javacourse.service.WordCounterResultContainer;
import com.qalight.javacourse.service.WordCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class CountWordsController {
    private static final Logger LOG = LoggerFactory.getLogger(CountWordsController.class);
    private final WordCounterService wordCounterService;

    @Autowired
    public CountWordsController(@Qualifier("wordCounterService") WordCounterService wordCounterService) {
        this.wordCounterService = wordCounterService;
    }

    @RequestMapping(value = "/countWordsRestStyle", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WordCounterResultContainer getResultRestStyle(@RequestParam String textCount, HttpServletRequest servletRequest) throws Throwable {
        Locale userLocale = servletRequest.getLocale();
        ErrorMessenger.setLocale(userLocale);
        CountWordsUserRequest userRequest = new CountWordsUserRequestImpl(textCount);
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        return result;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDataContainer handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        return getErrorMessage(ex);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDataContainer handleRuntimeExceptions(RuntimeException ex) {
        return getErrorMessage(ex);
    }

    private ErrorDataContainer getErrorMessage(Throwable ex) {
        ErrorDataContainer errorContainer = new ErrorDataContainer(ex.getMessage());
        LOG.error("Error while processing request: " + ex.getMessage(), ex);
        return errorContainer;
    }
}