package com.qalight.javacourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qalight.javacourse.service.ErrorDataContainer;
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

public class CountingWordsPlainTextFunctionalityTest {
    private OkHttpClient client;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_cyrillic() throws Exception {
        // given
        final String textRequest = "аэросъемка, АЭРОСЪЕМКА, дЫмаРь, дымарь";

        // when
        Request request = buildRequestWithParamValue(textRequest);
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
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_otherCyrillic() throws Exception {
        // given
        final String textRequest = "Під'їзд, ПІД'ЇЗД, ґедзь, єнот, й";

        // when
        Request request = buildRequestWithParamValue(textRequest);
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
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_latin() throws Exception {
        // given
        final String textRequest = "SWEET sweet lady loving wife";

        // when
        Request request = buildRequestWithParamValue(textRequest);
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
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_email() throws Exception {
        // given
        final String languageParam = LANGUAGE_DEFAULT_EN;
        final String textRequest = "kris@gmail.com";

        // when
        Request request = buildRequestWithParamValue(textRequest, languageParam);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text. Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_urlWithoutHttp() throws Exception {
        // given
        final String languageParam = LANGUAGE_RU;
        final String textRequest = "www.google.com";

        // when
        Request request = buildRequestWithParamValue(textRequest, languageParam);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("Система не может обработать введенный текст. Пожалуйста, проверьте, " +
                "не забыли ли Вы добавить 'http://' префикс к ссылке или ввели нечитаемый текст.");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_trashSymbols() throws Exception {
        // given
        final String languageParam = LANGUAGE_UA;
        final String textRequest = "%/*\\^#";

        // when
        Request request = buildRequestWithParamValue(textRequest, languageParam);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("Система не взмозі обробити введений текст. Будь ласка, перевірте, чи ви " +
                "не забули додати 'http://' префікс до посилання або ввели нечитабельний текст.");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_textWithTrashSymbols() throws Exception {
        // given
        final String textRequest = "рыжий%%%%%% 148MAD мін@";

        // when
        Request request = buildRequestWithParamValue(textRequest);
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
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_numbers() throws Exception {
        // given
        final String languageParam = LANGUAGE_ES;
        final String textRequest = "0";

        // when
        Request request = buildRequestWithParamValue(textRequest, languageParam);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text. Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWords_emptyRequest() throws Exception {
        // given
        final String textRequest = "";

        // when
        Request request = buildRequestWithParamValue(textRequest);
        Response response = client.newCall(request).execute();

        // then
        String expectedError = "Request is empty.";

        final ErrorDataContainer expected = new ErrorDataContainer(expectedError);

        final String resultStr = response.body().string();
        final ErrorDataContainer actual = objectMapper.readValue(resultStr, ErrorDataContainer.class);

        Assert.assertEquals(expected, actual);
    }

    public static String createFailMessage(String requestedValue) {
        return "Cannot get response from " + COUNT_URL + " with request: " + requestedValue;
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

    public Request buildRequestWithParamValue(String requestedValue, String languageParam) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedValue)
                .build();
        final Request request = new Request.Builder()
                .header(PARAM_LANGUAGE, languageParam)
                .url(COUNT_URL)
                .post(formBody)
                .build();
        return request;
    }
}
