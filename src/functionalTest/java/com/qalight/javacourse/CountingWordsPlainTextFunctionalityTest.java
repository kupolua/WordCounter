package com.qalight.javacourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qalight.javacourse.service.WordCounterResultContainerImpl;
import com.squareup.okhttp.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qalight.javacourse.utils.Constants.COUNT_URL;
import static com.qalight.javacourse.utils.Constants.DEFAULT_TIMEOUT;
import static com.qalight.javacourse.utils.Constants.PARAM_TEXT_COUNT;

public class CountingWordsPlainTextFunctionalityTest {
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_cyrillic() throws Exception {
        // given
        final String textRequest = "аэросъемка, АЭРОСЪЕМКА, дЫмаРь, дымарь";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("аэросъемка", 2);
            put("дымарь", 2);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_otherCyrillic() throws Exception {
        // given
        final String textRequest = "Під'їзд, ПІД'ЇЗД, ґедзь, єнот, й";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("під'їзд", 2);
            put("й", 1);
            put("єнот", 1);
            put("ґедзь", 1);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_latin() throws Exception {
        // given
        final String textRequest = "SWEET sweet lady loving wife";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("sweet", 2);
            put("lady", 1);
            put("loving", 1);
            put("wife", 1);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_email() throws Exception {
        // given
        final String textRequest = "kris@gmail.com";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text {" + textRequest + "}. Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_urlLikeText() throws Exception {
        // given
        final String textRequest = "www.google.com";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text {" + textRequest + "}. Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_trashSymbols() throws Exception {
        // given
        final String textRequest = "%/*\\^#";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text {" + textRequest + "}. Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_textWithTrashSymbols() throws Exception {
        // given
        final String textRequest = "рыжий%%%%%% 148MAD мін@";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("рыжий", 1);
            put("mad", 1);
            put("мін", 1);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_numbers() throws Exception {
        // given
        final String textRequest = "0";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text {" + textRequest + "}. Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Ignore  // todo: Сделать этот тест после того, как будет готова локализация ошибок
    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWords_emptyRequest() throws Exception {
        // given
        final String textRequest = "";

        Request request = buildRequestWithParamValue(textRequest);

        // when
        Response response = client.newCall(request).execute();
        System.out.println(response.toString());

        // then
        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("Request is empty.");
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
