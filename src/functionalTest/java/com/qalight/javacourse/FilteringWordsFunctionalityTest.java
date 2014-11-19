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

import static com.qalight.javacourse.utils.Constants.*;

public class FilteringWordsFunctionalityTest {
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }

    @Ignore
    @Test(timeout = DEFAULT_TIMEOUT)
    public void testFilterWords_latin() throws Exception {
        // given
        final String cyrillicPage = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
        final String isFiltering = "true";

        Request request = buildRequest(cyrillicPage, isFiltering);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(cyrillicPage));
        }

        final Map<String, Integer> expectedCountedWords = new HashMap<String, Integer>() {{
            put("test", 3);
            put("santa-monica", 1);
        }};

        List<String> expectedError = new ArrayList<>();

        final WordCounterResultContainerImpl expected = new WordCounterResultContainerImpl(expectedCountedWords, expectedError);

        final String resultStr = response.body().string();
        final ObjectMapper objectMapper = new ObjectMapper();
        final WordCounterResultContainerImpl actual = objectMapper.readValue(resultStr, WordCounterResultContainerImpl.class);

        Assert.assertEquals(expected, actual);
    }

    private String createFailMessage(String requestedValue) {
        return "cannot get response from " + COUNT_URL + " with request: " + requestedValue;
    }

    public Request buildRequest(String requestedValue, String isFiltering) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedValue)
                .add(PARAM_IS_FILTER_WORDS, isFiltering)
                .build();
        final Request request = new Request.Builder()
                .url(COUNT_URL)
                .post(formBody)
                .build();
        return request;
    }
}
