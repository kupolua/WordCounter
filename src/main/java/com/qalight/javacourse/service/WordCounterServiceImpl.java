package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.core.WordResultSorter;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.TextRefiner;
import com.qalight.javacourse.util.UrlFixer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class WordCounterServiceImpl implements WordCounterService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired private TextTypeInquirer textTypeInquirer;
    @Autowired private DocumentConverter documentConverter;
    @Autowired private WordCounter wordCounter;
    @Autowired private ResultPresentationImpl resultPresentationImpl;
    @Autowired private UrlFixer urlFixer;

    @Override
    public String getWordCounterResult(String clientRequest, String sortingParam, String dataTypeResponse) {
        checkParams(clientRequest, sortingParam);

        String fixedUrl = urlFixer.fixUrl(clientRequest);

        TextType textType = textTypeInquirer.inquireTextType(fixedUrl);

        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

        String plainText = documentToStringConverter.convertToString(fixedUrl);

        TextRefiner refiner = new TextRefiner();
        refiner.refineText(plainText);

        List<String> refinedWords = refiner.getRefinedWords();

        Map<String, Integer> countedWords = wordCounter.countWords(refinedWords);

        List<Map.Entry<String, Integer>> sortedWords = WordResultSorter.valueOf(sortingParam).getSortedWords(countedWords);

        ResultPresentation resultPresentation = resultPresentationImpl.getResultPresentation(dataTypeResponse);

        String result = resultPresentation.createResponse(clientRequest, sortedWords, dataTypeResponse);

        return result;
    }

    private static void checkParams(String userUrlsString, String sortingParam) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
    }

}