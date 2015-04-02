package info.deepidea.wordcounter.service;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class HtmlToStringConverterTest {
    @Mock private ParsingProcessor processor;
    private HtmlToStringConverter converter;

    @Before
    public void setup() {
        converter = new HtmlToStringConverter(processor);
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType docTextType = new HtmlTextTypeImpl();

        //when
        boolean actualResult = converter.isEligible(docTextType);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {
        //given
        final TextType docTextType = new PdfTextTypeImpl();

        //when
        boolean actualResult = converter.isEligible(docTextType);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void convertToString_crawlingIsNotRequired() throws Exception {
        //given
        final String input = "http://html-examples.com/example.html";
        final String expected = "some text";
        final RequestContainer requestContainer = new RequestContainer(input);
        final ConvertedDataContainer dataContainer = new ConvertedDataContainer(input, expected);

        when(processor.parsePage(requestContainer)).thenReturn(dataContainer);

        //when
        final ConvertedDataContainer actual = converter.convertToString(requestContainer);

        //then
        verify(processor, times(1)).parsePage(requestContainer);
        Assert.assertEquals(expected, actual.getPlainText());
        Assert.assertTrue(actual.getRelatedLinks().isEmpty());
    }

    @Test
    public void convertToString_internalCrawlingRequired() throws Exception {
        //given
        final String input = "https://code.google.com/p/lightcrawler/";
        final String expectedText = "some text";
        final Set<String> urlsFromPage = new HashSet<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1/");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2/");
        urlsFromPage.add("http://bbc.com/");

        final RequestContainer requestContainer = new RequestContainer(input, true, true);
        final ConvertedDataContainer dataContainer = new ConvertedDataContainer(input, expectedText);
        dataContainer.setRawUrls(urlsFromPage);

        when(processor.parsePage(requestContainer)).thenReturn(dataContainer);

        //when
        final ConvertedDataContainer actual = converter.convertToString(requestContainer);

        //then
        final Set<String> filteredUrls = new HashSet<String>();
        filteredUrls.add("https://code.google.com/p/lightcrawler/1/");
        filteredUrls.add("https://code.google.com/p/lightcrawler/2/");

        final Map<String, Set<String>> expected = new HashMap<>();
        expected.put(input, filteredUrls);

        verify(processor, times(1)).parsePage(requestContainer);
        Assert.assertEquals(expected, actual.getRelatedLinks());
    }

    @Test
    public void convertToString_anyUrlsCrawlingRequired() throws Exception {
        //given
        final String input = "https://code.google.com/p/lightcrawler/";
        final String expectedText = "some text";
        final Set<String> urlsFromPage = new HashSet<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1/");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2/");
        urlsFromPage.add("http://bbc.com/");

        final RequestContainer requestContainer = new RequestContainer(input, true, false);
        final ConvertedDataContainer dataContainer = new ConvertedDataContainer(input, expectedText);
        dataContainer.setRawUrls(urlsFromPage);

        when(processor.parsePage(requestContainer)).thenReturn(dataContainer);

        //when
        final ConvertedDataContainer actual = converter.convertToString(requestContainer);

        //then
        final Set<String> filteredUrls = new HashSet<String>();
        filteredUrls.add("https://code.google.com/p/lightcrawler/1/");
        filteredUrls.add("https://code.google.com/p/lightcrawler/2/");
        filteredUrls.add("http://bbc.com/");

        final Map<String, Set<String>> expected = new HashMap<>();
        expected.put(input, filteredUrls);

        verify(processor, times(1)).parsePage(requestContainer);
        Assert.assertEquals(expected, actual.getRelatedLinks());
    }

    @Test
    public void convertToString_internalCrawlingRequired_binaryTypesFiltering() throws Exception {
        //given
        final String input = "https://code.google.com/p/lightcrawler/";
        final String expectedText = "some text";
        final Set<String> urlsFromPage = new HashSet<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1.gif");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2.zip");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/start/");

        final RequestContainer requestContainer = new RequestContainer(input, true, true);
        final ConvertedDataContainer dataContainer = new ConvertedDataContainer(input, expectedText);
        dataContainer.setRawUrls(urlsFromPage);

        when(processor.parsePage(requestContainer)).thenReturn(dataContainer);

        //when
        final ConvertedDataContainer actual = converter.convertToString(requestContainer);

        //then
        final Set<String> filteredUrls = new HashSet<String>();
        filteredUrls.add("https://code.google.com/p/lightcrawler/start/");

        final Map<String, Set<String>> expected = new HashMap<>();
        expected.put(input, filteredUrls);

        verify(processor, times(1)).parsePage(requestContainer);
        Assert.assertEquals(expected, actual.getRelatedLinks());
    }

    @Test
    public void convertToString_anyUrlsCrawlingRequired_fragmentedUrlsFiltering() throws Exception {
        //given
        final String input = "https://code.google.com/p/lightcrawler/";
        final String expectedText = "some text";
        final Set<String> urlsFromPage = new HashSet<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1.html#start");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2/");
        urlsFromPage.add("http://bbc.com/");

        final RequestContainer requestContainer = new RequestContainer(input, true, false);
        final ConvertedDataContainer dataContainer = new ConvertedDataContainer(input, expectedText);
        dataContainer.setRawUrls(urlsFromPage);

        when(processor.parsePage(requestContainer)).thenReturn(dataContainer);

        //when
        final ConvertedDataContainer actual = converter.convertToString(requestContainer);

        //then
        final Set<String> filteredUrls = new HashSet<String>();
        filteredUrls.add("https://code.google.com/p/lightcrawler/2/");
        filteredUrls.add("http://bbc.com/");

        final Map<String, Set<String>> expected = new HashMap<>();
        expected.put(input, filteredUrls);

        verify(processor, times(1)).parsePage(requestContainer);
        Assert.assertEquals(expected, actual.getRelatedLinks());
    }

    @Test
    public void convertToString_internalCrawlingRequired_subdomainsHandling_example1() throws Exception {
        //given
        final String input = "https://code.google.com/p/lightcrawler/";
        final String expectedText = "some text";
        final Set<String> urlsFromPage = new HashSet<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1/");
        urlsFromPage.add("https://google.com/search/");
        urlsFromPage.add("http://bbc.com/");

        final RequestContainer requestContainer = new RequestContainer(input, true, true);
        final ConvertedDataContainer dataContainer = new ConvertedDataContainer(input, expectedText);
        dataContainer.setRawUrls(urlsFromPage);

        when(processor.parsePage(requestContainer)).thenReturn(dataContainer);

        //when
        final ConvertedDataContainer actual = converter.convertToString(requestContainer);

        //then
        final Set<String> filteredUrls = new HashSet<String>();
        filteredUrls.add("https://code.google.com/p/lightcrawler/1/");

        final Map<String, Set<String>> expected = new HashMap<>();
        expected.put(input, filteredUrls);

        verify(processor, times(1)).parsePage(requestContainer);
        Assert.assertEquals(expected, actual.getRelatedLinks());
    }

    @Test
    public void convertToString_internalCrawlingRequired_subdomainsHandling_example2() throws Exception {
        //given
        final String input = "https://google.com/search/";
        final String expectedText = "some text";
        final Set<String> urlsFromPage = new HashSet<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1/");
        urlsFromPage.add("https://google.com/");
        urlsFromPage.add("http://bbc.com/");

        final RequestContainer requestContainer = new RequestContainer(input, true, true);
        final ConvertedDataContainer dataContainer = new ConvertedDataContainer(input, expectedText);
        dataContainer.setRawUrls(urlsFromPage);

        when(processor.parsePage(requestContainer)).thenReturn(dataContainer);

        //when
        final ConvertedDataContainer actual = converter.convertToString(requestContainer);

        //then
        final Set<String> filteredUrls = new HashSet<String>();
        filteredUrls.add("https://google.com/");

        final Map<String, Set<String>> expected = new HashMap<>();
        expected.put(input, filteredUrls);

        verify(processor, times(1)).parsePage(requestContainer);
        Assert.assertEquals(expected, actual.getRelatedLinks());
    }
}