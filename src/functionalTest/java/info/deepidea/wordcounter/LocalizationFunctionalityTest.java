package info.deepidea.wordcounter;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static info.deepidea.wordcounter.utils.Constants.*;

public class LocalizationFunctionalityTest {
    private static final String URL_HOME_PAGE = SERVER_NAME + PORT + CONTEXT;
    private OkHttpClient client;

    @Before
    public void setup() {
        client = new OkHttpClient();
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textEN() throws Exception {
        // given
        final String languageType = LANGUAGE_DEFAULT_EN;
        final String expected = "Get the statistic of most frequently used words in one click!";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getTextMarkerFromResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textRU() throws Exception {
        // given
        final String languageType = LANGUAGE_RU;
        final String expected = "Получите статистику наиболее часто используемых слов в один миг!";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getTextMarkerFromResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textUA() throws Exception {
        // given
        final String languageType = LANGUAGE_UK;
        final String expected = "Отримайте статистику слів, що найчастіше зустрічаються у тексті, всього за одну" +
                " мить!";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getTextMarkerFromResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_textDefault() throws Exception {
        // given
        final String languageType = LANGUAGE_DE;
        final String expected = "Get the statistic of most frequently used words in one click!";

        Request request = buildRequestWithParamValue(languageType);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage());
        }

        String actual = getTextMarkerFromResponseBody(response);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testLanguage_responseHeaderRU() throws Exception {
        // given
        final String languageType = LANGUAGE_RU;
        final String expected = LANGUAGE_RU;

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

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testDefaultLanguage_responseHeader() throws Exception {
        // given
        final String languageType = LANGUAGE_DE;
        final String expected = LANGUAGE_DE;

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
        return String.format("Cannot get response from %s", URL_HOME_PAGE);
    }

    public Request buildRequestWithParamValue(String languageType) {
        final Request request = new Request.Builder()
                .header(PARAM_LANGUAGE, languageType)
                .url(URL_HOME_PAGE)
                .build();
        return request;
    }

    private String getTextMarkerFromResponseBody(Response response) throws IOException {
        final Pattern pattern = Pattern.compile("<div id=\"p1\">(.+?)</div>");

        String responseBody = response.body().string();
        final Matcher matcher = pattern.matcher(responseBody);
        matcher.find();
        String actual = matcher.group(1);
        return actual;
    }
}
