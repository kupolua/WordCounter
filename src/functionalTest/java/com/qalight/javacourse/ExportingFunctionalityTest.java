package com.qalight.javacourse;

import com.squareup.okhttp.*;
import org.apache.tika.Tika;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

import static com.qalight.javacourse.utils.Constants.*;

public class ExportingFunctionalityTest {
    private static final String COUNT_REQUEST_PDF = "downloadPDF";
    private static final String COUNT_URL_PDF = SERVER_NAME + PORT + CONTEXT + COUNT_REQUEST_PDF;
    private static final String COUNT_REQUEST_XLS = "downloadExcel";
    private static final String COUNT_URL_XLS = SERVER_NAME + PORT + CONTEXT + COUNT_REQUEST_XLS;
    private static final String MEDIA_TYPE_PDF = "application/pdf;charset=UTF-8";
    private static final String MEDIA_TYPE_XLS = "application/vnd.ms-excel;charset=UTF-8";
    private static final String EXPECTED_PDF = "expectedPdf.pdf";
    private static final String EXPECTED_XLS = "expectedXls.xls";
    private static final String LANGUAGE_TYPE = "ru-RU,en;q=0.5";

    private OkHttpClient client;
    private Tika documentConverter;

    @Before
    public void setup() {
        client = new OkHttpClient();
        documentConverter = new Tika();
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testExportPdf() throws Exception {
        // given
        final String requestedTextCount = "one one one one ёлка ёлка ёлка two two two білка білка білка объем объем" +
                " объем їжак їжак объём ученики і але имя слово a но дом друг єнот время та the человек народ r завет" +
                " сказал";
        final String requestedSortingOrder = "VALUE_DESCENDING";
        final String requestedIsFilterWords = "false";
        Request request = buildRequestWithParamValue(COUNT_URL_PDF, requestedTextCount, requestedSortingOrder,
                requestedIsFilterWords, MEDIA_TYPE_PDF);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(COUNT_URL_PDF, requestedTextCount, requestedSortingOrder,
                    requestedIsFilterWords));
        }
        File pdf = new File(PATH_RESOURCES + EXPECTED_PDF);
        String expectedPdf = documentConverter.parseToString(pdf);

        InputStream inputPdf = response.body().byteStream();
        String actualPdf = documentConverter.parseToString(inputPdf);

        Assert.assertEquals(expectedPdf, actualPdf);
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    public void testExportXls() throws Exception {
        // given
        final String requestedTextCount = "one one one one ёлка ёлка ёлка two two two білка білка білка объем объем" +
                " объем їжак їжак объём ученики і але имя слово a но дом друг єнот время та the человек народ r завет" +
                " сказал";
        final String requestedSortingOrder = "VALUE_DESCENDING";
        final String requestedIsFilterWords = "false";
        Request request = buildRequestWithParamValue(COUNT_URL_XLS, requestedTextCount, requestedSortingOrder,
                requestedIsFilterWords, MEDIA_TYPE_XLS);

        // when
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(COUNT_URL_XLS, requestedTextCount, requestedSortingOrder,
                    requestedIsFilterWords));
        }
        File xls = new File(PATH_RESOURCES + EXPECTED_XLS);
        String expectedXls = documentConverter.parseToString(xls);

        InputStream expXls = response.body().byteStream();
        String actualXls = documentConverter.parseToString(expXls);

        Assert.assertEquals(expectedXls, actualXls);
    }

    private String createFailMessage(String countUrl, String requestedTextCount, String requestedSortingOrder,
                                     String requestedIsFilterWords) {
        return "cannot get response from " + countUrl + " with request: " + requestedTextCount + " sorting by: " +
                requestedSortingOrder + " filtered is" + requestedIsFilterWords;
    }

    public Request buildRequestWithParamValue(String countUrl, String requestedTextCount, String requestedSortingOrder,
                                              String requestedIsFilterWords, String mediaType) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedTextCount)
                .add(PARAM_SORTING_ORDER, requestedSortingOrder)
                .add(PARAM_IS_FILTER_WORDS, requestedIsFilterWords)
                .build();
        final Request request = new Request.Builder()
                .header("Content-Type", mediaType)
                .header("Accept-Language", LANGUAGE_TYPE)
                .url(countUrl)
                .post(formBody)
                .build();
        return request;
    }
}
