package info.deepidea.wordcounter.crawler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlReceiverImplTest {
    @Mock
    ParsingProcessor processor;

    @Test
    public void testGetInternalUrlsFromPage() throws Exception {
        //given
        final String url = "https://code.google.com/p/lightcrawler/";
        final boolean internalOnly = true;

        final List<String> urlsFromPage = new ArrayList<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1/");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2/");
        urlsFromPage.add("http://bbc.com/");

        CrawledUrlsContainer urlsContainer = new CrawledUrlsContainer(url, urlsFromPage);

        when(processor.parsePage(url)).thenReturn(urlsContainer);

        UrlReceiver receiver = new UrlReceiverImpl(processor);

        //when
        Set<String> actual = receiver.getUrlsFromPage(url, internalOnly);

        //then
        final Set<String> expected = new HashSet<String>();
        expected.add("https://code.google.com/p/lightcrawler/1/");
        expected.add("https://code.google.com/p/lightcrawler/2/");

        verify(processor).parsePage(url);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAnyUrlsFromPage() throws Exception {
        //given
        final String url = "https://code.google.com/p/lightcrawler/";
        final boolean internalOnly = false;

        final List<String> urlsFromPage = new ArrayList<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1/");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2/");
        urlsFromPage.add("http://bbc.com/");

        CrawledUrlsContainer urlsContainer = new CrawledUrlsContainer(url, urlsFromPage);

        when(processor.parsePage(url)).thenReturn(urlsContainer);

        UrlReceiver receiver = new UrlReceiverImpl(processor);

        //when
        Set<String> actual = receiver.getUrlsFromPage(url, internalOnly);

        //then
        final Set<String> expected = new HashSet<String>();
        expected.add("https://code.google.com/p/lightcrawler/1/");
        expected.add("https://code.google.com/p/lightcrawler/2/");
        expected.add("http://bbc.com/");

        verify(processor).parsePage(url);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetInternalUrlsFromPage_binaryTypesFiltering() throws Exception {
        //given
        final String url = "https://code.google.com/p/lightcrawler/";
        final boolean internalOnly = true;

        final List<String> urlsFromPage = new ArrayList<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1.gif");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2.zip");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/start/");

        CrawledUrlsContainer urlsContainer = new CrawledUrlsContainer(url, urlsFromPage);

        when(processor.parsePage(url)).thenReturn(urlsContainer);

        UrlReceiver receiver = new UrlReceiverImpl(processor);

        //when
        Set<String> actual = receiver.getUrlsFromPage(url, internalOnly);

        //then
        final Set<String> expected = new HashSet<String>();
        expected.add("https://code.google.com/p/lightcrawler/start/");

        verify(processor).parsePage(url);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAnyUrlsFromPage_binaryTypesFiltering() throws Exception {
        //given
        final String url = "https://code.google.com/p/lightcrawler/";
        final boolean internalOnly = false;

        final List<String> urlsFromPage = new ArrayList<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1.gif");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2.zip");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/start/");
        urlsFromPage.add("http://bbc.com/");

        CrawledUrlsContainer urlsContainer = new CrawledUrlsContainer(url, urlsFromPage);

        when(processor.parsePage(url)).thenReturn(urlsContainer);

        UrlReceiver receiver = new UrlReceiverImpl(processor);

        //when
        Set<String> actual = receiver.getUrlsFromPage(url, internalOnly);

        //then
        final Set<String> expected = new HashSet<String>();
        expected.add("https://code.google.com/p/lightcrawler/start/");
        expected.add("http://bbc.com/");

        verify(processor).parsePage(url);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlsFromPage_incorrectUrl() throws Exception {
        //given
        final String url = "boobce23.net";
        final boolean internalOnly = true;

        final List<String> emptyList = Collections.emptyList();

        CrawledUrlsContainer urlsContainer = new CrawledUrlsContainer(url, emptyList);

        when(processor.parsePage(url)).thenReturn(urlsContainer);

        UrlReceiver receiver = new UrlReceiverImpl(processor);

        //when
        Set<String> actual = receiver.getUrlsFromPage(url, internalOnly);

        //then
        final Set<String> expected = Collections.emptySet();

        verify(processor).parsePage(url);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetUrlsFromPage_filteringUrlsWithTheFragment() throws Exception {
        //given
        final String url = "https://code.google.com/p/lightcrawler/";
        final boolean internalOnly = false;

        final List<String> urlsFromPage = new ArrayList<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1.html#start");
        urlsFromPage.add("https://code.google.com/p/lightcrawler/2/");
        urlsFromPage.add("http://bbc.com/");

        CrawledUrlsContainer urlsContainer = new CrawledUrlsContainer(url, urlsFromPage);

        when(processor.parsePage(url)).thenReturn(urlsContainer);

        UrlReceiver receiver = new UrlReceiverImpl(processor);

        //when
        Set<String> actual = receiver.getUrlsFromPage(url, internalOnly);

        //then
        final Set<String> expected = new HashSet<String>();
        expected.add("https://code.google.com/p/lightcrawler/2/");
        expected.add("http://bbc.com/");

        verify(processor).parsePage(url);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetInternalUrlsFromPage_subdomainsHandling() throws Exception {
        //given
        final String url = "https://code.google.com/p/lightcrawler/";
        final boolean internalOnly = true;

        final List<String> urlsFromPage = new ArrayList<String>();
        urlsFromPage.add("https://code.google.com/p/lightcrawler/1/");
        urlsFromPage.add("https://google.com/search/");
        urlsFromPage.add("http://bbc.com/");

        CrawledUrlsContainer urlsContainer = new CrawledUrlsContainer(url, urlsFromPage);

        when(processor.parsePage(url)).thenReturn(urlsContainer);

        UrlReceiver receiver = new UrlReceiverImpl(processor);

        //when
        Set<String> actual = receiver.getUrlsFromPage(url, internalOnly);

        //then
        final Set<String> expected = new HashSet<String>();
        expected.add("https://code.google.com/p/lightcrawler/1/");

        verify(processor).parsePage(url);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetInternalUrlsFromPage_subdomainsFiltering() throws Exception {
        //given
        final String url = "https://google.com/search/";
        final boolean internalOnly = true;

        final List<String> urlsFromPage = new ArrayList<String>();
        urlsFromPage.add("https://code.google.com/p/");
        urlsFromPage.add("https://google.com/search/target/");
        urlsFromPage.add("http://bbc.com/");

        CrawledUrlsContainer urlsContainer = new CrawledUrlsContainer(url, urlsFromPage);

        when(processor.parsePage(url)).thenReturn(urlsContainer);

        UrlReceiver receiver = new UrlReceiverImpl(processor);

        //when
        Set<String> actual = receiver.getUrlsFromPage(url, internalOnly);

        //then
        final Set<String> expected = new HashSet<String>();
        expected.add("https://google.com/search/target/");

        verify(processor).parsePage(url);

        Assert.assertEquals(expected, actual);
    }
}