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

import static com.qalight.javacourse.utils.Constants.*;

public class InformationAboutFunctionalityTest {
    private static final String ABOUT = "about";
    private static final String URL_ABOUT = SERVER_NAME + PORT + CONTEXT + ABOUT;
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }

    // given
    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textEN() throws Exception {
        final String expected = "About Word Counter";

        Request request = buildRequestWithParamValue();

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    private String createFailMessage() {
        return "Cannot get response from " + URL_ABOUT;
    }

    public Request buildRequestWithParamValue() {
        final Request request = new Request.Builder()
                .header(PARAM_LANGUAGE, LANGUAGE_DEFAULT_EN)
                .url(URL_ABOUT)
                .build();
        return request;
    }

    private String getResponseBody(Response response) throws IOException {
        final Pattern pattern = Pattern.compile("<div id=\"aboutWCHead\">(.+?)</div>");

        String responseBody = response.body().string();
        final Matcher matcher = pattern.matcher(responseBody);
        matcher.find();
        String actual = matcher.group(1);
        return actual;
    }
}
