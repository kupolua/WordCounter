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

public class CountingWordsUrlsFunctionalityTest {
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInWebPage_cyrillic() throws Exception {
        // given
        final String cyrillicPage = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";

        Request request = buildRequestWithParamValue(cyrillicPage);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(cyrillicPage));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("аэросъемка", 2);
            put("дымарь", 2);
            put("нет", 1);
            put("щеголь", 1);
            put("он", 1);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInWebPage_latin() throws Exception {
        // given
        final String cyrillicPage = "http://kupol.in.ua/wordcounter/testData/page_latin.html";

        Request request = buildRequestWithParamValue(cyrillicPage);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(cyrillicPage));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("test", 3);
            put("a", 1);
            put("santa-monica", 1);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInDocumentViaUrl_pptx() throws Exception {
        // given
        final String pptxUrl =
                "http://kupol.in.ua/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx";

        Request request = buildRequestWithParamValue(pptxUrl);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(pptxUrl));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("думи", 2);
            put("мої", 1);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInDocumentViaUrl_noText() throws Exception {
        // given
        final String htmlNoText = "http://kupol.in.ua/wordcounter/testData/no_text.html";

        Request request = buildRequestWithParamValue(htmlNoText);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(htmlNoText));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text {www.google.com\n\n%/*\\^#\n\n0514\n\n}. " +
                    "Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInDocumentViaUrl_invalidLink() throws Exception {
        // given
        final String invalidLink = "http://kupol...in.ua/wordcounter/testData/no_text.html";

        Request request = buildRequestWithParamValue(invalidLink);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(invalidLink));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("Can't connect to: " + invalidLink);
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInDocumentViaUrl_txt() throws Exception {
        // given
        final String txtUrl = "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";

        Request request = buildRequestWithParamValue(txtUrl);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(txtUrl));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("nice", 1);
            put("people", 1);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInDocumentViaUrl_pdf() throws Exception {
        // given
        final String pdfUrl = "http://kupol.in.ua/wordcounter/testData/1500_words.pdf";

        Request request = buildRequestWithParamValue(pdfUrl);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(pdfUrl));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("word", 665);
            put("тексте", 507);
            put("мінімум", 253);
            put("в", 75);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInDocumentViaUrl_pdfNoText() throws Exception {
        // given
        final String pdfUrl = "http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf";

        Request request = buildRequestWithParamValue(pdfUrl);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(pdfUrl));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count text in the source as it is empty or contains non-readable content or symbols: " + pdfUrl);
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    private String createFailMessage(String requestedValue) {
        return "cannot get response from " + COUNT_URL + " with request: " + requestedValue;
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
