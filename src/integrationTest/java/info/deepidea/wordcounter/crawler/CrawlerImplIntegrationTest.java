package info.deepidea.wordcounter.crawler;

import info.deepidea.wordcounter.core.CountWordsProcessor;
import info.deepidea.wordcounter.service.ThreadResultContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerImplIntegrationTest {

    @Mock private CountWordsProcessor processor;

    @Test
    public void testCrawl_checkThatAllUrlsAreParsedTheCorrectNumberOfTimes() throws Exception {
        //given
        final String first = "https://code.google.com/p/lightcrawler/";
        final String second = "https://code.google.com/p/lightcrawler/2/";
        final String third = "https://code.google.com/p/lightcrawler/2/1";
        final String fourth = "https://code.google.com/p/lightcrawler/2/2";

        Set<String> googleResults1 = new HashSet<>();
        googleResults1.add(first);
        googleResults1.add(second);

        Set<String> googleResults2 = new HashSet<>();
        googleResults2.add(third);
        googleResults2.add(fourth);

        Map<String, Set<String>>  urls1 = new HashMap<String, Set<String>>();
        urls1.put(first, googleResults1);

        Map<String, Set<String>>  urls2 = new HashMap<String, Set<String>>();
        urls2.put(second, googleResults2);

        ThreadResultContainer container1 =
                new ThreadResultContainer(Collections.emptyMap(), Collections.emptyMap(), urls1);
        ThreadResultContainer container2 =
                new ThreadResultContainer(Collections.emptyMap(), Collections.emptyMap(), urls2);
        ThreadResultContainer emptyContainer =
                new ThreadResultContainer(Collections.emptyMap(), Collections.emptyMap(), urls2);

        when(processor.process(eq(first), anyBoolean(), anyBoolean())).thenReturn(container1);
        when(processor.process(eq(second), anyBoolean(), anyBoolean())).thenReturn(container2);
        when(processor.process(eq(third), anyBoolean(), anyBoolean())).thenReturn(emptyContainer);
        when(processor.process(eq(fourth), anyBoolean(), anyBoolean())).thenReturn(emptyContainer);

        Crawler crawler = new CrawlerImpl(2, true, first, processor);

        //when
        final List<ThreadResultContainer> actual = crawler.crawl();

        //then
        final List<ThreadResultContainer> expected = new ArrayList<ThreadResultContainer>();
        expected.add(container1);
        expected.add(container2);
        expected.add(emptyContainer);
        expected.add(emptyContainer);

        verify(processor, times(1)).process(eq(first), anyBoolean(), anyBoolean());
        verify(processor, times(2)).process(anyString(), eq(true), anyBoolean());
        verify(processor, times(2)).process(anyString(), eq(false), anyBoolean());

        assertEquals(expected,actual);
    }
}