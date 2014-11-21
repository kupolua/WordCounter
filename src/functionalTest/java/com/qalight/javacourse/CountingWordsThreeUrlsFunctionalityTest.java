package com.qalight.javacourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qalight.javacourse.service.WordCounterResultContainerImpl;
import com.squareup.okhttp.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qalight.javacourse.utils.Constants.*;

public class CountingWordsThreeUrlsFunctionalityTest {
    private OkHttpClient client;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testEnterThreeLinks() throws Exception {
        // given
        final String requestedValue = "http://kupol.in.ua/wordcounter/testData/page_latin.html " +
                "http://kupol.in.ua/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx " +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("test", 3);
            put("думи", 2);
            put("a", 1);
            put("мої", 1);
            put("santa-monica", 1);
            put("people", 1);
            put("nice", 1);
        }};

        List<String> expectedError = new ArrayList<>();
        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testEnterThreeLinks_withBrokenHtmlLink() throws Exception {
        // given
        final String requestedValue = "http://kupol....in.ua/wordcounter/testData/test_page_latin.html " +
                "http://kupol.in.ua/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx " +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String errorMassage = "Cannot connect to the source: >http://kupol....in.ua/wordcounter/testData/test_page_latin.html";

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("думи", 2);
            put("people", 1);
            put("мої", 1);
            put("nice", 1);
        }};

        List<String> expectedError = new ArrayList<>();
        expectedError.add(errorMassage);
        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testEnterThreeLinks_withNoReadableTextInPdf() throws Exception {
        // given
        final String requestedValue = "http://kupol.in.ua/wordcounter/testData/page_latin.html " +
                "http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf " +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String errorMassage = "System cannot count text in the source as it is empty or contains non-readable" +
                " content or symbols: >http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf";

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("test", 3);
            put("a", 1);
            put("santa-monica", 1);
            put("people", 1);
            put("nice", 1);
        }};

        List<String> expectedError = new ArrayList<>();
        expectedError.add(errorMassage);
        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testEnterThreeLinks_withBrokenTxtLink() throws Exception {
        // given
        final String requestedValue = "http://kupol.in.ua/wordcounter/testData/test_page_latin.html " +
                "http://kupol.in.ua/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx " +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers...txt";
        final String errorMassageConnect = "Cannot connect to the source: >http://kupol.in.ua/wordcounter/testData/test_page_latin.html";
        final String errorMassageDocument = "Cannot connect to the source: >http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers...txt";

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("думи", 2);
            put("мої", 1);
        }};

        List<String> expectedError = new ArrayList<>();
        expectedError.add(errorMassageConnect);
        expectedError.add(errorMassageDocument);
        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    public static String createFailMessage(String requestedValue) {
        return String.format("Cannot get response from %s with request: %s", COUNT_URL, requestedValue);
    }

    public Request buildRequestWithParamValue(String requestedValue) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedValue)
                .build();
        final Request request = new Request.Builder()
                .header(PARAM_LANGUAGE, LANGUAGE_DEFAULT_EN)
                .url(COUNT_URL)
                .post(formBody)
                .build();
        return request;
    }
}
