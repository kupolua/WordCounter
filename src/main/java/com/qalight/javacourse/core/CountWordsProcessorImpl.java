package com.qalight.javacourse.core;

import com.qalight.javacourse.service.*;
import com.qalight.javacourse.util.TextRefiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CountWordsProcessorImpl implements CountWordsProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CountWordsProcessorImpl.class);
    private final TextTypeInquirer textTypeInquirer;
    private final DocumentConverter documentConverter;
    private final WordCounter wordCounter;
    private final TextRefiner refiner;


    @Autowired
    public CountWordsProcessorImpl(TextTypeInquirer textTypeInquirer, DocumentConverter documentConverter,
                                   WordCounter wordCounter, TextRefiner refiner) {
        this.textTypeInquirer = textTypeInquirer;
        this.documentConverter = documentConverter;
        this.wordCounter = wordCounter;
        this.refiner = refiner;

    }

    @Override
    public ThreadResultContainer process(String clientRequest) {
        LOG.debug(String.format("executing request {%s} in thread '%s'",
                clientRequest, Thread.currentThread().getName()));
        ThreadResultContainer result;
        try{
            TextType textType = textTypeInquirer.inquireTextType(clientRequest);

            DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

            String plainText = documentToStringConverter.convertToString(clientRequest);

            List<String> refinedWords = refiner.refineText(plainText);

            result = new ThreadResultContainer(wordCounter.countWords(refinedWords));
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
            result = new ThreadResultContainer(Collections.emptyMap(), e.getMessage());
        }
        return result;
    }
}
