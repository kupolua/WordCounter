package com.qalight.javacourse;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.qalight.javacourse.Constants.*;

public class RestWordCounterLanguageTest {
    private static final String COUNT_URL = SERVER_NAME + PORT + CONTEXT;
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textEN() throws Exception {
        // given
        final String languageType = "en-EN,en;q=0.5";
        final String expected = "Get the most frequently used words in one click!";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textRU() throws Exception {
        // given
        final String languageType = "ru-RU,en;q=0.5";
        final String expected = "Получите список наиболее часто используемых слов в один миг!";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textUA() throws Exception {
        // given
        final String languageType = "uk,ru;q=0.8,en-US;q=0.6,en;q=0.4";
        final String expected = "Отримайте перелік слів, що найчастіше зустрічаються у тексті, всього за одну мить!";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textDefault() throws Exception {
        // given
        final String languageType = "de-DE,de;q=0.8,de-DE;q=0.6,en;q=0.4";
        final String expected = "Get the most frequently used words in one click!";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    //todo it's just for example. Should we test response headers?
    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_responseHeaderRU() throws Exception {
        // given
        final String languageType = "ru-RU,en;q=0.5";
        final String expected = "ru-RU";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = response.header("Content-Language");

        Assert.assertEquals(expected, actual);
    }

    //todo it's just for example. Should we test response headers?
    @Test(timeout = DEFAULT_TIMEOUT)
    public void testDefaultLanguage_responseHeader() throws Exception {
        // given
        final String languageType = "de-DE,en;q=0.5";
        //todo config Spring to response header Content-Language in en-EN as default
        final String expected = "de-DE";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = response.header("Content-Language");

        Assert.assertEquals(expected, actual);
    }

    private String createFailMessage() {
        return "cannot get response from " + COUNT_URL;
    }

    public Request buildRequestWithParamValue(String languageType) {
        final Request request = new Request.Builder()
                .header("Accept-Language", languageType)
                .url(COUNT_URL)
                .build();
        return request;
    }

    private String getResponseBody(Response response) throws IOException {
        final Pattern pattern = Pattern.compile("<div id=\"p1\">(.+?)</div>");

        String responseBody = response.body().string();
        final Matcher matcher = pattern.matcher(responseBody);
        matcher.find();
        String actual = matcher.group(1);
        return actual;
    }
}
