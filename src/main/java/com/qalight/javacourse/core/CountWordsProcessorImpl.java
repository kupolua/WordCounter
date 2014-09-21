package com.qalight.javacourse.core;

import com.qalight.javacourse.service.*;
import com.qalight.javacourse.util.TextRefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CountWordsProcessorImpl implements CountWordsProcessor {
    private final TextTypeInquirer textTypeInquirer;
    private final DocumentConverter documentConverter;
    private final WordCounter wordCounter;
    private final TextRefiner refiner;
    private final WordFilter wordFilter;

    @Autowired
    public CountWordsProcessorImpl(TextTypeInquirer textTypeInquirer, DocumentConverter documentConverter,
                                   WordCounter wordCounter, TextRefiner refiner, WordFilter wordFilter) {
        this.textTypeInquirer = textTypeInquirer;
        this.documentConverter = documentConverter;
        this.wordCounter = wordCounter;
        this.refiner = refiner;
        this.wordFilter = wordFilter;
    }

    @Override
    public Map<String, Integer> process(String clientRequest) {
        TextType textType = textTypeInquirer.inquireTextType(clientRequest);

        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

        String plainText = documentToStringConverter.convertToString(clientRequest);

        List<String> refinedWords = refiner.refineText(plainText);

        List<String> refinedWordsWithFilter = wordFilter.removeUnimportantWords(refinedWords);

        Map<String, Integer> result = wordCounter.countWords(refinedWordsWithFilter);

        return result;
    }
}
