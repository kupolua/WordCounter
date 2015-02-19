package info.deepidea.wordcounter.crawler;

import info.deepidea.wordcounter.core.CountWordsProcessor;
import info.deepidea.wordcounter.service.ThreadResultContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerImplTest {

    @Mock private ForkJoinPool pool;
    @Mock private CountWordsProcessor processor;
    @Mock private Forker forker;

    private String initial;
    private List<ThreadResultContainer> firstResult;
    private List<ThreadResultContainer> emptyJoinedResults;


    @Before
    public void setUp() {
        initial = "https://code.google.com/p/lightcrawler/";

        Map<String,Set<String>> links = new HashMap<>();
        links.put(initial, new HashSet<String>(Arrays.asList("https://code.google.com/p/lightcrawler/1/",
                "https://code.google.com/p/lightcrawler/2/")));
        firstResult = new ArrayList<ThreadResultContainer>();
        firstResult.add(new ThreadResultContainer(Collections.emptyMap(), Collections.emptyMap(), links));

        ThreadResultContainer container1 = new ThreadResultContainer(Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());
        ThreadResultContainer container2 = new ThreadResultContainer(Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());
        emptyJoinedResults = Arrays.asList(container1, container2);
    }

    @Test
    public void testCrawl_oneDepth() throws Exception {
        //given
        CrawlerImpl spyCrawler = Mockito.spy(new CrawlerImpl(1, true, initial, processor));

        doReturn(forker).when(spyCrawler).getForker(anySet(), anyBoolean());
        doReturn(pool).when(spyCrawler).getForkJoinPool();
        when(pool.invoke(forker)).thenReturn(firstResult, emptyJoinedResults);

        //when
        final List<ThreadResultContainer> actual = spyCrawler.crawl();

        //then
        final List<ThreadResultContainer> expected = new ArrayList<>();
        expected.addAll(firstResult);
        expected.addAll(emptyJoinedResults);

        verify(spyCrawler).getForkJoinPool();
        verify(spyCrawler, times(1)).getForker(anySet(), eq(true));
        verify(spyCrawler, times(1)).getForker(anySet(), eq(false));
        verify(pool, times(2)).invoke(forker);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCrawl_twoDepths() throws Exception {
        //given
        CrawlerImpl spyCrawler = Mockito.spy(new CrawlerImpl(2, true, initial, processor));

        Map<String,Set<String>> crawledLinks1 = new HashMap<String,Set<String>>();
        crawledLinks1.put("https://code.google.com/p/lightcrawler/1/",
                new HashSet<String>(Arrays.asList(
                        "https://code.google.com/p/lightcrawler/1/1", "https://code.google.com/p/lightcrawler/1/2")));

        Map<String,Set<String>> crawledLinks2 = new HashMap<String,Set<String>>();
        crawledLinks2.put("https://code.google.com/p/lightcrawler/2/",
                new HashSet<String>(Arrays.asList(
                        "https://code.google.com/p/lightcrawler/2/1", "https://code.google.com/p/lightcrawler/2/2")));

        List<ThreadResultContainer> joinedResultsWithUrls = getJoinedResults(Arrays.asList(crawledLinks1, crawledLinks2));

        doReturn(forker).when(spyCrawler).getForker(anySet(), anyBoolean());
        doReturn(pool).when(spyCrawler).getForkJoinPool();
        when(pool.invoke(forker)).thenReturn(firstResult, joinedResultsWithUrls, emptyJoinedResults);

        //when
        final List<ThreadResultContainer> actual = spyCrawler.crawl();

        //then
        final List<ThreadResultContainer> expected = new ArrayList<ThreadResultContainer>();
        expected.addAll(firstResult);
        expected.addAll(joinedResultsWithUrls);
        expected.addAll(emptyJoinedResults);

        verify(spyCrawler).getForkJoinPool();
        verify(spyCrawler, times(2)).getForker(anySet(), eq(true));
        verify(spyCrawler, times(1)).getForker(anySet(), eq(false));
        verify(pool, times(3)).invoke(forker);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrawl_threeDepths() throws Exception {
        //given
        CrawlerImpl spyCrawler = Mockito.spy(new CrawlerImpl(3, true, initial, processor));

        doReturn(forker).when(spyCrawler).getForker(anySet(), anyBoolean());
        doReturn(pool).when(spyCrawler).getForkJoinPool();
        when(pool.invoke(forker)).thenReturn(firstResult);

        //when
        final List<ThreadResultContainer> actual = spyCrawler.crawl();

        //then
        verify(spyCrawler, never()).getForkJoinPool();
        verify(spyCrawler, never()).getForker(anySet(), anyBoolean());
        verify(pool, never()).invoke(forker);

        //exception expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrawl_negativeDepth() throws Exception {
        //given
        CrawlerImpl spyCrawler = Mockito.spy(new CrawlerImpl(-1, true, initial, processor));

        doReturn(forker).when(spyCrawler).getForker(anySet(), anyBoolean());
        doReturn(pool).when(spyCrawler).getForkJoinPool();
        when(pool.invoke(forker)).thenReturn(firstResult);

        //when
        final List<ThreadResultContainer> actual = spyCrawler.crawl();

        //then
        verify(spyCrawler, never()).getForkJoinPool();
        verify(spyCrawler, never()).getForker(anySet(), anyBoolean());
        verify(pool, never()).invoke(forker);
        //exception expected
    }

    @Test
    public void testCrawl_zeroDepth() throws Exception {
        //given
        CrawlerImpl spyCrawler = Mockito.spy(new CrawlerImpl(0, true, initial, processor));

        List<ThreadResultContainer> expectedResult =
                getJoinedResults(Arrays.asList(new HashMap<String, Set<String>>()));

        doReturn(forker).when(spyCrawler).getForker(anySet(), anyBoolean());
        doReturn(pool).when(spyCrawler).getForkJoinPool();
        when(pool.invoke(forker)).thenReturn(expectedResult);

        //when
        final List<ThreadResultContainer> actual = spyCrawler.crawl();

        //then
        verify(spyCrawler).getForkJoinPool();
        verify(spyCrawler).getForker(anySet(), eq(false));
        verify(pool).invoke(forker);

        Assert.assertEquals(expectedResult, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrawl_emptyUserRequest() throws Exception {
        //given
        initial = "";
        CrawlerImpl spyCrawler = Mockito.spy(new CrawlerImpl(2, true, initial, processor));

        doReturn(forker).when(spyCrawler).getForker(anySet(),anyBoolean());
        doReturn(pool).when(spyCrawler).getForkJoinPool();
        when(pool.invoke(forker)).thenReturn(firstResult);

        //when
        final List<ThreadResultContainer> actual = spyCrawler.crawl();

        //then
        verify(spyCrawler, never()).getForkJoinPool();
        verify(spyCrawler, never()).getForker(anySet(), anyBoolean());
        verify(pool, never()).invoke(forker);
        //exception expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrawl_nullInitialSet() throws Exception {
        //given
        initial = null;
        CrawlerImpl spyCrawler = Mockito.spy(new CrawlerImpl(2, true, initial, processor));

        doReturn(forker).when(spyCrawler).getForker(anySet(),anyBoolean());
        doReturn(pool).when(spyCrawler).getForkJoinPool();
        when(pool.invoke(forker)).thenReturn(firstResult);

        //when
        final List<ThreadResultContainer> actual = spyCrawler.crawl();

        //then
        verify(spyCrawler, never()).getForkJoinPool();
        verify(spyCrawler, never()).getForker(anySet(), anyBoolean());
        verify(pool, never()).invoke(forker);
        //exception expected
    }

    @Test
    public void testCrawl_textInput() throws Exception {
        //given
        initial = "one, one";

        CrawlerImpl spyCrawler = Mockito.spy(new CrawlerImpl(2, true, initial, processor));

        Map<String, Integer> words = new HashMap<String, Integer>(){{
            put("one", 2);
        }};

        List<ThreadResultContainer> countedResult =
                Arrays.asList(new ThreadResultContainer(words, Collections.emptyMap(), Collections.emptyMap()));

        doReturn(forker).when(spyCrawler).getForker(anySet(), anyBoolean());
        doReturn(pool).when(spyCrawler).getForkJoinPool();
        when(pool.invoke(forker)).thenReturn(countedResult);

        //when
        final List<ThreadResultContainer> actual = spyCrawler.crawl();

        //then
        final List<ThreadResultContainer> expected = new ArrayList<>();
        expected.addAll(countedResult);

        verify(spyCrawler).getForkJoinPool();
        verify(spyCrawler).getForker(anySet(), anyBoolean());
        verify(pool).invoke(forker);

        Assert.assertEquals(expected, actual);
    }

    private static List<ThreadResultContainer> getJoinedResults(List<Map<String, Set<String>>> urls) {
        List<ThreadResultContainer> joinedResult = new ArrayList<>();
        for (Map<String, Set<String>> each : urls) {
            ThreadResultContainer container =
                    new ThreadResultContainer(Collections.emptyMap(), Collections.emptyMap(), each);
            joinedResult.add(container);
        }
        return joinedResult;
    }
}