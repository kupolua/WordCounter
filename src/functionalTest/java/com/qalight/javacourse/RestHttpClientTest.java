package com.qalight.javacourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
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
        final String expected = "{\"countedResult\":{\"two\":2,\"one\":1}}";
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
        final String expected = "{\"respMessage\":\"Error during executing request: " +
                "Can\\u0027t connect to: http://broken-guugol.com/\"}";
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
        final String expected = "{\"respMessage\":\"Error during executing request: " +
                "System cannot count entered text {kris@gmail.com www.google.com %/*\\\\^# 0}. " +
                "Did you forget to add \\u0027http://\\u0027 to the link or entered not readable text?\"}";
        final String actual = response.body().string();
        Assert.assertEquals(expected, actual);
    }

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
