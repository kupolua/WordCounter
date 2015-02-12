//package info.deepidea.wordcounter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import info.deepidea.wordcounter.service.WordCounterResultContainerImpl;
//import com.squareup.okhttp.*;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static info.deepidea.wordcounter.utils.Constants.*;
//
//public class CountingWordsUrlsFunctionalityTest {
//    private OkHttpClient client;
//    private ObjectMapper objectMapper;
//
//    @Before
//    public void setup() {
//        client = new OkHttpClient();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test(timeout = DEFAULT_TIMEOUT)
//    public void testCountWordsInWebPage_cyrillic() throws Exception {
//        // given
//        final String cyrillicPage = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";
//
//        // when
//        Request request = buildRequestWithParamValue(cyrillicPage);
//        Response response = client.newCall(request).execute();
//
//        // then
//        if (!response.isSuccessful()) {
//            Assert.fail(createFailMessage(cyrillicPage));
//        }
//
//        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
//            put("аэросъемка", 2);
//            put("дымарь", 2);
//            put("нет", 1);
//            put("щеголь", 1);
//            put("он", 1);
//        }};
//
//        List<String> expectedError = new ArrayList<>();
//
//        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 44);
//            put("statisticUniqueWords", 5);
//            put("statisticTotalCharacters", 53);
//            put("statisticTotalWords", 7);
//        }};
//
//        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic);
//
//        final String resultStr = response.body().string();
//        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test(timeout = DEFAULT_TIMEOUT)
//    public void testCountWordsInWebPage_latin() throws Exception {
//        // given
//        final String cyrillicPage = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
//
//        // when
//        Request request = buildRequestWithParamValue(cyrillicPage);
//        Response response = client.newCall(request).execute();
//
//        // then
//        if (!response.isSuccessful()) {
//            Assert.fail(createFailMessage(cyrillicPage));
//        }
//
//        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
//            put("test", 3);
//            put("a", 1);
//            put("santa-monica", 1);
//        }};
//
//        List<String> expectedError = new ArrayList<>();
//
//        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 27);
//            put("statisticUniqueWords", 3);
//            put("statisticTotalCharacters", 34);
//            put("statisticTotalWords", 5);
//        }};
//
//        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic);
//
//        final String resultStr = response.body().string();
//        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test(timeout = DEFAULT_TIMEOUT)
//    public void testCountWordsInDocumentViaUrl_noText() throws Exception {
//        // given
//        final String htmlNoText = "http://kupol.in.ua/wordcounter/testData/no_text.html";
//
//        // when
//        Request request = buildRequestWithParamValue(htmlNoText);
//        Response response = client.newCall(request).execute();
//
//        // then
//        if (!response.isSuccessful()) {
//            Assert.fail(createFailMessage(htmlNoText));
//        }
//
//        final Map<String, Integer> expectedCountedWords = new HashMap<>();
//
//        List<String> expectedError = new ArrayList<String>() {{
//            add("System cannot count entered text. Did you forget to add " +
//                    "'http://' to the link or entered not readable text?");
//        }};
//
//        final Map<String, Integer> wordStatistic = new HashMap<>();
//
//        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic);
//
//        final String resultStr = response.body().string();
//        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test(timeout = DEFAULT_TIMEOUT)
//    public void testCountWordsInDocumentViaUrl_invalidLink() throws Exception {
//        // given
//        final String invalidLink = "http://kupol...in.ua/wordcounter/testData/no_text.html";
//
//        // when
//        Request request = buildRequestWithParamValue(invalidLink);
//        Response response = client.newCall(request).execute();
//
//        // then
//        if (!response.isSuccessful()) {
//            Assert.fail(createFailMessage(invalidLink));
//        }
//
//        final Map<String, Integer> expectedCountedWords = new HashMap<>();
//
//        List<String> expectedError = new ArrayList<String>() {{
//            add("Cannot connect to the source: >http://kupol...in.ua/wordcounter/testData/no_text.html");
//        }};
//
//        final Map<String, Integer> wordStatistic = new HashMap<>();
//
//        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic);
//
//        final String resultStr = response.body().string();
//        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test(timeout = DEFAULT_TIMEOUT)
//    public void testCountWordsInDocumentViaUrl_pptx() throws Exception {
//        // given
//        final String pptxUrl =
//                "http://kupol.in.ua/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx";
//
//        // when
//        Request request = buildRequestWithParamValue(pptxUrl);
//        Response response = client.newCall(request).execute();
//
//        // then
//        if (!response.isSuccessful()) {
//            Assert.fail(createFailMessage(pptxUrl));
//        }
//
//        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
//            put("думи", 2);
//            put("мої", 1);
//        }};
//
//        List<String> expectedError = new ArrayList<>();
//
//        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 12);
//            put("statisticUniqueWords", 2);
//            put("statisticTotalCharacters", 15);
//            put("statisticTotalWords", 3);
//        }};
//
//        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic);
//
//        final String resultStr = response.body().string();
//        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test(timeout = DEFAULT_TIMEOUT)
//    public void testCountWordsInDocumentViaUrl_txt() throws Exception {
//        // given
//        final String txtUrl = "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
//
//        // when
//        Request request = buildRequestWithParamValue(txtUrl);
//        Response response = client.newCall(request).execute();
//
//        // then
//        if (!response.isSuccessful()) {
//            Assert.fail(createFailMessage(txtUrl));
//        }
//
//        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
//            put("nice", 1);
//            put("people", 1);
//        }};
//
//        List<String> expectedError = new ArrayList<>();
//
//        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 18);
//            put("statisticUniqueWords", 2);
//            put("statisticTotalCharacters", 22);
//            put("statisticTotalWords", 2);
//        }};
//
//        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic);
//
//        final String resultStr = response.body().string();
//        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test(timeout = DEFAULT_TIMEOUT)
//    public void testCountWordsInDocumentViaUrl_pdf() throws Exception {
//        // given
//        final String pdfUrl = "http://kupol.in.ua/wordcounter/testData/1500_words.pdf";
//
//        // when
//        Request request = buildRequestWithParamValue(pdfUrl);
//        Response response = client.newCall(request).execute();
//
//        // then
//        if (!response.isSuccessful()) {
//            Assert.fail(createFailMessage(pdfUrl));
//        }
//
//        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
//            put("word", 665);
//            put("тексте", 507);
//            put("мінімум", 253);
//            put("в", 75);
//        }};
//
//        List<String> expectedError = new ArrayList<>();
//
//        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 7548);
//            put("statisticUniqueWords", 4);
//            put("statisticTotalCharacters", 9147);
//            put("statisticTotalWords", 1500);
//        }};
//
//        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic);
//
//        final String resultStr = response.body().string();
//        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test(timeout = DEFAULT_TIMEOUT)
//    public void testCountWordsInDocumentViaUrl_pdfNoText() throws Exception {
//        // given
//        final String pdfUrl = "http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf";
//
//        // when
//        Request request = buildRequestWithParamValue(pdfUrl);
//        Response response = client.newCall(request).execute();
//
//        // then
//        if (!response.isSuccessful()) {
//            Assert.fail(createFailMessage(pdfUrl));
//        }
//
//        final Map<String, Integer> expectedCountedWords = new HashMap<>();
//
//        List<String> expectedError = new ArrayList<String>() {{
//            add("System cannot count text in the source as it is empty or contains non-readable content or symbols: >" +
//                    "http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf");
//        }};
//
//        final Map<String, Integer> wordStatistic = new HashMap<>();
//
//        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError, wordStatistic);
//
//        final String resultStr = response.body().string();
//        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);
//
//        Assert.assertEquals(expected, actual);
//    }
//
//    public static String createFailMessage(String requestedValue) {
//        return String.format("Cannot get response from %s with request: %s", COUNT_URL, requestedValue);
//    }
//
//    public Request buildRequestWithParamValue(String requestedValue) {
//        RequestBody formBody = new FormEncodingBuilder()
//                .add(PARAM_TEXT_COUNT, requestedValue)
//                .build();
//        final Request request = new Request.Builder()
//                .header(PARAM_LANGUAGE, LANGUAGE_DEFAULT_EN)
//                .url(COUNT_URL)
//                .post(formBody)
//                .build();
//        return request;
//    }
//}
