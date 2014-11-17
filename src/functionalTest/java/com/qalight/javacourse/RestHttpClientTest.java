package com.qalight.javacourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qalight.javacourse.service.WordCounterResultContainerImpl;
import com.squareup.okhttp.*;
import org.apache.commons.collections.map.LinkedMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qalight.javacourse.Constants.*;

public class RestHttpClientTest {
    private static final String COUNT_REQUEST = "countWordsRestStyle";
    private static final String COUNT_URL = SERVER_NAME + PORT + CONTEXT + COUNT_REQUEST;
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testSimpleInputString_RawJson() throws Exception {
        // given
        final String requestedValue = "two one two";
        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(requestedValue));
        }
        final String expected = "{\"countedResult\":{\"two\":2,\"one\":1},\"errors\":[]}";
        final String actual = response.body().string();
        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testInputEmptyString() throws Exception {
        // given
        final String requestedValue = "";
        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        final String expected = "{\"respMessage\":\"Request is null or empty\"}";
        final String actual = response.body().string();
        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testInputBrokenLink() throws Exception {
        // given
        final String requestedValue = "http://broken-guugol.com/";
        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        final String expected = "{\"countedResult\":{},\"errors\":[\"Can't connect to: http://broken-guugol.com/\"]}";
        final String actual = response.body().string();
        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testImproperInputString() throws Exception {
        // given
        final String requestedValue = "kris@gmail.com www.google.com %/*\\^# 0";
        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        final String expected = "{\"countedResult\":{},\"errors\":[\"System cannot count entered text " +
                "{kris@gmail.com www.google.com %/*\\\\^# 0}. Did you forget to add 'http://' to the link or entered " +
                "not readable text?\"]}";
        final String actual = response.body().string();
        Assert.assertEquals(expected, actual);
    }

    @Ignore
    @Test(timeout = DEFAULT_TIMEOUT)
    public void testSimpleInputString_ResponseObject() throws Exception {
        // given
        final String requestedValue = "Alex Mike Alex, Alex";
        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(requestedValue));
        }
        final WordCounterResponse expected = new WordCounterResponse(new HashMap<String, Integer>() {{
            put("Alex".toLowerCase(), 3);
            put("Mike".toLowerCase(), 1);
        }});
        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResponse actual = objectMapper.readValue(resultStr, WordCounterResponse.class);

        Assert.assertEquals(expected, actual);
    }
    //todo need refactoring (this is not finish addition)
    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInThreeURLs_oneBroken() throws Exception {
        // given
        final String htmlPageBroken = "http://kupol....in.ua/wordcounter/testData/test_page_latin.html";
        final String pptxLink = "http://kupol.in.ua/wordcounter/testData/" +
                "%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx";
        final String txtLink = "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String separator = " ";
        final String errorMassage = "Can't connect to: http://kupol....in.ua/wordcounter/testData/test_page_latin.html";
        final String requestedValue = htmlPageBroken + separator + pptxLink + separator + txtLink;

        Request request = buildRequestWithParamValue(requestedValue);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(requestedValue));
        }

        Map<String, Integer> expectedCountedWords = new HashMap<>();
        expectedCountedWords.put("думи", 2);
        expectedCountedWords.put("people", 1);
        expectedCountedWords.put("мої", 1);
        expectedCountedWords.put("nice", 1);

        List<String> expectedError = new ArrayList<>();
        expectedError.add(errorMassage);
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

    static final class WordCounterResponse {
        private final Map<String, Integer> countedResult;

        public WordCounterResponse() {
            countedResult = new HashMap<>();
        }

        public WordCounterResponse(Map<String, Integer> countedResult) {
            this.countedResult = countedResult;
        }

        public Map<String, Integer> getCountedResult() {
            return countedResult;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WordCounterResponse that = (WordCounterResponse) o;

            if (!countedResult.equals(that.countedResult)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return countedResult.hashCode();
        }

        @Override
        public String toString() {
            return "WordCounterResponse{" +
                    "countedResult=" + countedResult +
                    '}';
        }
    }
}
