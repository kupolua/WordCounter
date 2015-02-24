package info.deepidea.wordcounter;

import com.squareup.okhttp.*;
import org.apache.tika.Tika;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

import static info.deepidea.wordcounter.utils.Constants.*;

public class ExportingFunctionalityTest {
    private static final String COUNT_REQUEST_PDF = "downloadPDF";
    private static final String COUNT_URL_PDF = SERVER_NAME + PORT + CONTEXT + COUNT_REQUEST_PDF;
    private static final String COUNT_REQUEST_XLS = "downloadExcel";
    private static final String COUNT_URL_XLS = SERVER_NAME + PORT + CONTEXT + COUNT_REQUEST_XLS;
    private static final String MEDIA_TYPE_PDF = "application/pdf;charset=UTF-8";
    private static final String MEDIA_TYPE_XLS = "application/vnd.ms-excel;charset=UTF-8";

    private OkHttpClient client;
    private Tika documentConverter;

    @Before
    public void setup() {
        client = new OkHttpClient();
        documentConverter = new Tika();
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 44
    public void testExportToPdf_cyrillicHtml() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_DESCENDING;
        final String requestedIsFilterWords = "false";

        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_PDF,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_PDF,
                LANGUAGE_DEFAULT_EN);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_PDF, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File pdf = new File(PATH_RESOURCES + "expectedEnFromCyrillicHtml.pdf");
        String expectedPdf = documentConverter.parseToString(pdf);

        InputStream inputPdf = response.body().byteStream();
        String actualPdf = documentConverter.parseToString(inputPdf);

        Assert.assertEquals(expectedPdf, actualPdf);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 45
    public void testExportToPdfRu_latinHtml() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_DESCENDING;
        final String requestedIsFilterWords = "false";

        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_PDF,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_PDF,
                LANGUAGE_RU);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_PDF, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File pdf = new File(PATH_RESOURCES + "expectedRuFromLatinHtml.pdf");
        String expectedPdf = documentConverter.parseToString(pdf);

        InputStream inputPdf = response.body().byteStream();
        String actualPdf = documentConverter.parseToString(inputPdf);

        Assert.assertEquals(expectedPdf, actualPdf);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 46
    public void testExportToPdfUk_noTextPptx() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/Ppt_no_text.pptx";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_DESCENDING;
        final String requestedIsFilterWords = "false";

        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_PDF,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_PDF,
                LANGUAGE_UK);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_PDF, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File pdf = new File(PATH_RESOURCES + "expectedUkFromNoTextPptx.pdf");
        String expectedPdf = documentConverter.parseToString(pdf);

        InputStream inputPdf = response.body().byteStream();
        String actualPdf = documentConverter.parseToString(inputPdf);

        Assert.assertEquals(expectedPdf, actualPdf);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 47
    public void testExportToXlsEn_cyrillicHtml() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_DESCENDING;
        final String requestedIsFilterWords = "false";

        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_XLS,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_XLS,
                LANGUAGE_DEFAULT_EN);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_XLS, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File xls = new File(PATH_RESOURCES + "expectedEnFromCyrillicHtml.xls");
        String expectedXls = documentConverter.parseToString(xls);

        InputStream expXls = response.body().byteStream();
        String actualXls = documentConverter.parseToString(expXls);

        Assert.assertEquals(expectedXls, actualXls);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 48
    public void testExportToXlsDe_latinHtml() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_DESCENDING;
        final String requestedIsFilterWords = "false";

        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_XLS,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_XLS,
                LANGUAGE_DE);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_XLS, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File xls = new File(PATH_RESOURCES + "expectedEnFromLatinHtml.xls");
        String expectedXls = documentConverter.parseToString(xls);

        InputStream expXls = response.body().byteStream();
        String actualXls = documentConverter.parseToString(expXls);

        Assert.assertEquals(expectedXls, actualXls);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 49
    public void testExportToXlsUk_noTextPptx() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/Ppt_no_text.pptx";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_DESCENDING;
        final String requestedIsFilterWords = "false";

        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_XLS,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_XLS,
                LANGUAGE_UK);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_XLS, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File xls = new File(PATH_RESOURCES + "expectedUkFromNoTextPptx.xls");
        String expectedXls = documentConverter.parseToString(xls);

        InputStream expXls = response.body().byteStream();
        String actualXls = documentConverter.parseToString(expXls);

        Assert.assertEquals(expectedXls, actualXls);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 50
    public void testExportPdfDe_latinHtml_Filter() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_DESCENDING;
        final String requestedIsFilterWords = "true";
        final String expectedPdfFiltering = "expectedEnPdfFromLatinHtmlFilter.pdf";


        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_PDF,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_PDF,
                LANGUAGE_DE);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_PDF, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File pdf = new File(PATH_RESOURCES + expectedPdfFiltering);
        String expectedPdf = documentConverter.parseToString(pdf);

        InputStream inputPdf = response.body().byteStream();
        String actualPdf = documentConverter.parseToString(inputPdf);

        Assert.assertEquals(expectedPdf, actualPdf);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 51
    public void testExportPdfRu_ruAlphabet_SortKeyAscend() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/RU_alphabet.docx";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = KEY_ASCENDING;
        final String requestedIsFilterWords = "false";
        final String expectedPdfFiltering = "expectedRuFromRuAlphabetDocxKeyAscend.pdf";


        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_PDF,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_PDF,
                LANGUAGE_RU);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_PDF, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File pdf = new File(PATH_RESOURCES + expectedPdfFiltering);
        String expectedPdf = documentConverter.parseToString(pdf);

        InputStream inputPdf = response.body().byteStream();
        String actualPdf = documentConverter.parseToString(inputPdf);

        Assert.assertEquals(expectedPdf, actualPdf);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 52
    public void testExportPdfRu_allLangOdt_FilterValueAscend() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/all_lang.odt";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_ASCENDING;
        final String requestedIsFilterWords = "true";
        final String expectedPdfFiltering = "expectedRuAllLangOdtFilterValueAscend.pdf";


        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_PDF,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_PDF,
                LANGUAGE_RU);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_PDF, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File pdf = new File(PATH_RESOURCES + expectedPdfFiltering);
        String expectedPdf = documentConverter.parseToString(pdf);

        InputStream inputPdf = response.body().byteStream();
        String actualPdf = documentConverter.parseToString(inputPdf);

        Assert.assertEquals(expectedPdf, actualPdf);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 53
    public void testExportXlsRu_cyrillicHtml_FilterValueDescend() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = VALUE_DESCENDING;
        final String requestedIsFilterWords = "true";
        final String expectedXlsFiltering = "expectedRuFromCyrillicHtmlFilter.xls";


        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_XLS,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_XLS,
                LANGUAGE_RU);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_XLS, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File xls = new File(PATH_RESOURCES + expectedXlsFiltering);
        String expected = documentConverter.parseToString(xls);

        InputStream input = response.body().byteStream();
        String actual = documentConverter.parseToString(input);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 54
    public void testExportXlsRu_enAlphabet_KeyDescend() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/EN_alphabet.docx";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = KEY_DESCENDING;
        final String requestedIsFilterWords = "false";
        final String expectedXls = "expectedRuEnAlphabetDocxKeyAscend.xls";


        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_XLS,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_XLS,
                LANGUAGE_RU);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_XLS, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File xls = new File(PATH_RESOURCES + expectedXls);
        String expected = documentConverter.parseToString(xls);

        InputStream input = response.body().byteStream();
        String actual = documentConverter.parseToString(input);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 55
    public void testExportXlsRu_allLangOdt_FilterKeyAscend() throws Exception {
        // given
        final String requestedTextCount = "http://kupol.in.ua/wordcounter/testData/all_lang.odt";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = KEY_ASCENDING;
        final String requestedIsFilterWords = "true";
        final String expectedXls = "expectedRuFromAllLangOdtFilterKeyAsc.xls";


        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_XLS,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_XLS,
                LANGUAGE_RU);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_XLS, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File xls = new File(PATH_RESOURCES + expectedXls);
        String expected = documentConverter.parseToString(xls);

        InputStream input = response.body().byteStream();
        String actual = documentConverter.parseToString(input);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 56
    public void testExportPdfRu_brokenUrlAndNormalUrl_FilterKeyAscend() throws Exception {
        // given
        final String requestedTextCount = "http://kupol....in.ua/wordcounter/testData/test_page_latin.html \n" +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = KEY_DESCENDING;
        final String requestedIsFilterWords = "true";
        final String expectedPdf = "expectedRuFromLettersNumbersWithError.pdf";


        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_PDF,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_XLS,
                LANGUAGE_RU);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_XLS, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File pdf = new File(PATH_RESOURCES + expectedPdf);
        String expected = documentConverter.parseToString(pdf);

        InputStream input = response.body().byteStream();
        String actual = documentConverter.parseToString(input);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = DEFAULT_TIMEOUT) // line 57
    public void testExportXlsRu_brokenUrlAndNormalUrl_FilterKeyAscend() throws Exception {
        // given
        final String requestedTextCount = "http://kupol....in.ua/wordcounter/testData/test_page_latin.html \n" +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String depth = "0";
        final String internalOnly = "true";
        final String requestedSortingOrder = KEY_DESCENDING;
        final String requestedIsFilterWords = "true";
        final String expectedXls = "expectedRuFromLettersNumbersWithError.xls";

        // when
        Request request = buildRequestWithParamValue(
                COUNT_URL_XLS,
                requestedTextCount,
                depth,
                internalOnly,
                requestedSortingOrder,
                requestedIsFilterWords,
                MEDIA_TYPE_XLS,
                LANGUAGE_RU);
        Response response = client.newCall(request).execute();

        // then
        if (!response.isSuccessful()) {
            Assert.fail(createFailMessage(
                    COUNT_URL_XLS, requestedTextCount, requestedSortingOrder, requestedIsFilterWords));
        }

        File xls = new File(PATH_RESOURCES + expectedXls);
        String expected = documentConverter.parseToString(xls);

        InputStream input = response.body().byteStream();
        String actual = documentConverter.parseToString(input);

        Assert.assertEquals(expected, actual);
    }

    private String createFailMessage(String countUrl,
                                     String requestedTextCount,
                                     String requestedSortingOrder,
                                     String requestedIsFilterWords) {
        return "Cannot get response from " + countUrl + " with request: " + requestedTextCount + " sorting by: " +
                requestedSortingOrder + " filtered is" + requestedIsFilterWords;
    }

    public Request buildRequestWithParamValue(String countUrl, String requestedTextCount,
                                              String depth, String internalOnly,
                                              String requestedSortingOrder, String requestedIsFilterWords,
                                              String mediaType, String languageParam) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedTextCount)
                .add(DEPTH, depth)
                .add(INTERNAL_ONLY, internalOnly)
                .add(PARAM_SORTING_ORDER, requestedSortingOrder)
                .add(PARAM_IS_FILTER_WORDS, requestedIsFilterWords)
                .build();
        final Request request = new Request.Builder()
                .header(PARAM_CONTENT_TYPE, mediaType)
                .header(PARAM_LANGUAGE, languageParam)
                .url(countUrl)
                .post(formBody)
                .build();
        return request;
    }
}
