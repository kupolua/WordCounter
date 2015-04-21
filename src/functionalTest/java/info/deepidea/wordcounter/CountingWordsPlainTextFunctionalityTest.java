package info.deepidea.wordcounter;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.deepidea.wordcounter.service.ErrorDataContainer;
import info.deepidea.wordcounter.service.WordCounterResultContainerImpl;
import com.squareup.okhttp.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static info.deepidea.wordcounter.utils.Constants.*;

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
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithParamValue(textRequest, depth, internalOnly);
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

        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 35);
            put("statisticUniqueWords", 2);
            put("statisticTotalCharacters", 38);
            put("statisticTotalWords", 4);
        }};

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_otherCyrillic() throws Exception {
        // given
        final String textRequest = "Під'їзд, ПІД'ЇЗД, ґедзь, єнот, й";
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithParamValue(textRequest, depth, internalOnly);
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

        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 28);
            put("statisticUniqueWords", 4);
            put("statisticTotalCharacters", 32);
            put("statisticTotalWords", 5);
        }};

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsWithParamsInPlainText_otherCyrillic() throws Exception {
        // given
        final String textRequest = "Під'їзд, ПІД'ЇЗД, голка, вода вода, the";
        final String depth = "0";
        final String internalOnly = "true";
        final String sortingOrder = KEY_DESCENDING;
        final String isFilterWords = "true";
        String languageParam = LANGUAGE_RU;

        // when
        Request request = buildRequestWithAllParams(textRequest, depth, internalOnly, sortingOrder, isFilterWords, languageParam);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new LinkedHashMap<String, Integer>() {{
            put("під'їзд", 2);
            put("голка", 1);
            put("вода", 2);
        }};

        List<String> expectedError = new ArrayList<>();

        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 34);
            put("statisticUniqueWords", 4);
            put("statisticTotalCharacters", 39);
            put("statisticTotalWords", 6);
        }};

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_latin() throws Exception {
        // given
        final String textRequest = "SWEET sweet lady loving wife";
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithParamValue(textRequest, depth, internalOnly);
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

        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 24);
            put("statisticUniqueWords", 4);
            put("statisticTotalCharacters", 28);
            put("statisticTotalWords", 5);
        }};

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual =
                objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_email() throws Exception {
        // given
        final String languageParam = LANGUAGE_DEFAULT_EN;
        final String textRequest = "kris@gmail.com";
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithLanguageParam(textRequest, depth, internalOnly, languageParam);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text. Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final Map<String, Integer> wordStatistic = new HashMap<>();

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_urlWithoutHttp() throws Exception {
        // given
        final String languageParam = LANGUAGE_RU;
        final String textRequest = "www.google.com";
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithLanguageParam(textRequest, depth, internalOnly, languageParam);
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

        final Map<String, Integer> wordStatistic = new HashMap<>();

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_trashSymbols() throws Exception {
        // given
        final String languageParam = LANGUAGE_UK;
        final String textRequest = "%/*\\^#";
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithLanguageParam(textRequest, depth, internalOnly, languageParam);
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

        final Map<String, Integer> wordStatistic = new HashMap<>();

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_textWithTrashSymbols() throws Exception {
        // given
        final String textRequest = "рыжий%%%%%% 148MAD мін@";
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithParamValue(textRequest, depth, internalOnly);
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

        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 21);
            put("statisticUniqueWords", 3);
            put("statisticTotalCharacters", 23);
            put("statisticTotalWords", 3);
        }};

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWordsInPlainText_numbers() throws Exception {
        // given
        final String languageParam = LANGUAGE_ES;
        final String textRequest = "0";
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithLanguageParam(textRequest, depth, internalOnly, languageParam);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(textRequest));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<>();

        List<String> expectedError = new ArrayList<String>() {{
            add("System cannot count entered text. Did you forget to add 'http://' to the link or entered not readable text?");
        }};

        final Map<String, Integer> wordStatistic = new HashMap<>();

        final Map<String, Set<String>> relatedLinks = Collections.emptyMap();

        final WordCounterResultContainerImpl expected =
                new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic, relatedLinks, Collections.emptyMap());

        final String resultStr = response.body().string();
        final WordCounterResultContainerImpl actual =
                objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWords_emptyRequest() throws Exception {
        // given
        final String textRequest = "";
        final String depth = "0";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithParamValue(textRequest, depth, internalOnly);
        Response response = client.newCall(request).execute();

        // then
        final String expectedError = "Request is empty.";

        final ErrorDataContainer expected = new ErrorDataContainer(expectedError);

        final String resultStr = response.body().string();
        final ErrorDataContainer actual = objectMapper.readValue(resultStr, ErrorDataContainer.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testCountWords_incorrectDepth() throws Exception {
        // given
        final String textRequest = "hi vasiliy";
        final String depth = "3";
        final String internalOnly = "true";

        // when
        Request request = buildRequestWithParamValue(textRequest, depth, internalOnly);
        Response response = client.newCall(request).execute();

        // then
        final String expectedError = "Depth could not be > 1 or < 0";

        final ErrorDataContainer expected = new ErrorDataContainer(expectedError);

        final String resultStr = response.body().string();
        final ErrorDataContainer actual = objectMapper.readValue(resultStr, ErrorDataContainer.class);

        Assert.assertEquals(expected, actual);
    }

    public static String createFailMessage(String requestedValue) {
        return String.format("Cannot get response from %s with request: %s", COUNT_URL, requestedValue);
    }

    public Request buildRequestWithParamValue(String requestedValue, String depth, String internalOnly) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedValue)
                .add(DEPTH, depth)
                .add(INTERNAL_ONLY, internalOnly)
                .build();
        final Request request = new Request.Builder()
                .header(PARAM_LANGUAGE, LANGUAGE_DEFAULT_EN)
                .url(COUNT_URL)
                .post(formBody)
                .build();
        return request;
    }

    public Request buildRequestWithLanguageParam(String requestedValue, String depth,
                                                 String internalOnly, String languageParam) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedValue)
                .add(DEPTH, depth)
                .add(INTERNAL_ONLY, internalOnly)
                .build();
        final Request request = new Request.Builder()
                .header(PARAM_LANGUAGE, languageParam)
                .url(COUNT_URL)
                .post(formBody)
                .build();
        return request;
    }

    public Request buildRequestWithAllParams(String requestedValue, String depth, String internalOnly,
                                             String sortingOrder, String isFilterWords, String languageParam) {
        String countUrl = SERVER_NAME + PORT + CONTEXT + "countWordsWithParams";
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedValue)
                .add(DEPTH, depth)
                .add(INTERNAL_ONLY, internalOnly)
                .add(PARAM_SORTING_ORDER, sortingOrder)
                .add(PARAM_IS_FILTER_WORDS, isFilterWords)
                .build();
        final Request request = new Request.Builder()
                .header(PARAM_LANGUAGE, languageParam)
                .url(countUrl)
                .post(formBody)
                .build();
        return request;
    }
}
