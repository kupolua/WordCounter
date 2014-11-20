package com.qalight.javacourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qalight.javacourse.service.WordCounterResultContainerImpl;
import com.squareup.okhttp.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qalight.javacourse.utils.Constants.*;
import static com.qalight.javacourse.utils.Util.*;

public class CountingWordsThreeUrlsFunctionalityTest {
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }

    @Test
    public void testEnterThreeLinks() throws Exception {
        // given
        final String htmlPageLatin = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
        final String pptxLink = "http://kupol.in.ua/wordcounter/testData/" +
                "%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx";
        final String txtLink = "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String requestedValue = htmlPageLatin + SEPARATOR + pptxLink + SEPARATOR + txtLink;

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(COUNT_URL, requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>(){{
            put("test", 3);
            put("думи", 2);
            put("a", 1);
            put("мої", 1);
            put("santa-monica", 1);
            put("people", 1);
            put("nice", 1);}};

        List<String> expectedError = new ArrayList<>();
        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testEnterThreeLinks_withBrokenHtmlLink() throws Exception {
        // given
        final String htmlPageBroken = "http://kupol....in.ua/wordcounter/testData/test_page_latin.html";
        final String pptxLink = "http://kupol.in.ua/wordcounter/testData/" +
                "%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx";
        final String txtLink = "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String errorMassage = "Can't connect to: http://kupol....in.ua/wordcounter/testData/test_page_latin.html";
        final String requestedValue = htmlPageBroken + SEPARATOR + pptxLink + SEPARATOR + txtLink;

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(COUNT_URL, requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>(){{
                put("думи", 2);
                put("people", 1);
                put("мої", 1);
                put("nice", 1);}};

        List<String> expectedError = new ArrayList<>();
        expectedError.add(errorMassage);
        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testEnterThreeLinks_withNoReadableTextInPdf() throws Exception {
        // given
        final String htmlPageLatin = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
        final String pptxLink = "http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf";
        final String txtLink = "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String errorMassage = "System cannot count text in the source as it is empty or contains non-readable" +
                " content or symbols: http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf";
        final String requestedValue = htmlPageLatin + SEPARATOR + pptxLink + SEPARATOR + txtLink;

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(COUNT_URL, requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>(){{
            put("test", 3);
            put("a", 1);
            put("santa-monica", 1);
            put("people", 1);
            put("nice", 1);}};

        List<String> expectedError = new ArrayList<>();
        expectedError.add(errorMassage);
        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testEnterThreeLinks_withBrokenTxtLink() throws Exception {
        // given
        final String htmlPageLatin = "http://kupol.in.ua/wordcounter/testData/test_page_latin.html";
        final String pptxLink = "http://kupol.in.ua/wordcounter/testData/" +
                "%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx ";
        final String txtLink = "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers...txt";
        final String errorMassageConnect = "Can't connect to: http://kupol.in.ua/wordcounter/testData/test_page_latin.html";
        final String errorMassageDocument = "Document <http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers...txt> cannot" +
                " be processed. ";
        final String requestedValue = htmlPageLatin + SEPARATOR + pptxLink + SEPARATOR + txtLink;

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(COUNT_URL, requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>(){{
            put("думи", 2);
            put("мої", 1);}};

        List<String> expectedError = new ArrayList<>();
        expectedError.add(errorMassageConnect);
        expectedError.add(errorMassageDocument);
        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    public Request buildRequestWithParamValue(String requestedValue) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedValue)
                .build();
        final Request request = new Request.Builder()
                .url(COUNT_URL)
                .post(formBody)
                .build();
        return request;
    }
}
