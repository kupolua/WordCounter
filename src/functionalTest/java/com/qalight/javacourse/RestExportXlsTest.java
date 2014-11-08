package com.qalight.javacourse;

import com.squareup.okhttp.*;
import org.apache.tika.Tika;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

public class RestExportXlsTest {
    private static final long SECOND = 1_000;
    private static final long DEFAULT_TIMEOUT = 30 * SECOND;
    private static final int PORT = 8080;
    private static final String PARAM_TEXT_COUNT = "textCount";
    private static final String PARAM_SORTING_ORDER = "sortingOrder";
    private static final String PARAM_IS_FILTER_WORDS = "isFilterWords";
    private static final String SERVER_NAME = "http://localhost:";
    private static final String CONTEXT = "/WordCounter/";
    private static final String COUNT_REQUEST = "downloadExcel";
    private static final String MEDIA_TYPE = "application/vnd.ms-excel;charset=UTF-8";
    private static final String LANGUAGE_TYPE = "ru-RU,en;q=0.5";
    private static final String COUNT_URL = SERVER_NAME + PORT + CONTEXT + COUNT_REQUEST;
    private static final String PATH_RESOURCES = "src/functionalTest/resources/";
    private static final String EXPECTED_XLS = "expectedXls.xls";
    private OkHttpClient client;
    private Tika documentConverter;

    @Before
    public void setup() {
        client = new OkHttpClient();
        documentConverter = new Tika();
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testExportXls() throws Exception {
        // given
        final String requestedTextCount = "one one one one ёлка ёлка ёлка two two two білка білка білка объем объем" +
                " объем їжак їжак объём ученики і але имя слово a но дом друг єнот время та the человек народ r завет" +
                " сказал";
        final String requestedSortingOrder = "VALUE_DESCENDING";
        final String requestedIsFilterWords = "false";
        Request request = buildRequestWithParamValue(requestedTextCount, requestedSortingOrder, requestedIsFilterWords);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }
        File xls = new File(PATH_RESOURCES + EXPECTED_XLS);
        String expectedXls = documentConverter.parseToString(xls);

        InputStream expXls = response.body().byteStream();
        String actualXls = documentConverter.parseToString(expXls);

        Assert.assertEquals(expectedXls, actualXls);
    }

    private String createFailMessage(String requestedTextCount, String requestedSortingOrder,
                                     String requestedIsFilterWords) {
        return "cannot get response from " + COUNT_URL + " with request: " + requestedTextCount + " sorting by: " +
                requestedSortingOrder + " filtered is" + requestedIsFilterWords;
    }

    public Request buildRequestWithParamValue(String requestedTextCount, String requestedSortingOrder,
                                              String requestedIsFilterWords) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedTextCount)
                .add(PARAM_SORTING_ORDER, requestedSortingOrder)
                .add(PARAM_IS_FILTER_WORDS, requestedIsFilterWords)
                .build();
        final Request request = new Request.Builder()
                .header("Content-Type", MEDIA_TYPE)
                .header("Accept-Language", LANGUAGE_TYPE)
                .url(COUNT_URL)
                .post(formBody)
                .build();
        return request;
    }
}
