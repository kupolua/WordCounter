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
import static com.qalight.javacourse.utils.Util.*;

public class CountingWordsThreeUrlsFunctionalityTest {
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }
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
            Assert.fail(createFailMessage(COUNT_URL, requestedValue));
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
