package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.crawler.UrlReceiver;
import info.deepidea.wordcounter.service.*;
import info.deepidea.wordcounter.util.TextRefiner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountWordsProcessorImplTest {
    private TextTypeInquirer textTypeInquirer;
    private DocumentConverter documentConverter;
    private DocumentToStringConverter concreteConverter;
    private WordCounter wordCounter;
    private TextRefiner textRefiner;
    private WordStatistic statistic;
    private UrlReceiver urlReceiver;

    @Before
    public void setUp() {
        textTypeInquirer = mock(TextTypeInquirer.class);
        documentConverter = mock(DocumentConverter.class);
        concreteConverter = mock(DocumentToStringConverter.class);
        wordCounter = mock(WordCounterImpl.class);
        textRefiner = mock(TextRefiner.class);
        statistic = mock(WordStatistic.class);
        urlReceiver = mock(UrlReceiver.class);
    }

    @Test
    public void testProcess_text() {
        // given
        final boolean crawlingRequired = false;
        final boolean internalOnly = false;
        final String clientRequest = "one two two,";
        TextType concreteType = mock(TextType.class);

        final Map<String, Integer> expectedCountedResult = new HashMap();
        expectedCountedResult.put("one", 1);
        expectedCountedResult.put("two", 2);
        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 10);
            put("statisticUniqueWords", 2);
            put("statisticTotalCharacters", 12);
            put("statisticTotalWords", 3);
        }};

        ThreadResultContainer expectedContainer =
                new ThreadResultContainer(expectedCountedResult, expectedStatistic, Collections.emptyMap());

        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        CountWordsProcessor processor = new CountWordsProcessorImpl(
                textTypeInquirer, documentConverter, wordCounter, textRefiner, statistic, urlReceiver);

        // when
        final ThreadResultContainer actual = processor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(clientRequest);
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));
        verify(statistic, times(1)).getStatistic(any(String.class), any(List.class));
        verify(urlReceiver, never()).getUrlsFromPage(any(String.class), anyBoolean());

        assertEquals(expectedContainer, actual);
    }

    @Test
    public void testProcess_textCrawlingRequired() {
        // given
        final boolean crawlingRequired = true;
        final boolean internalOnly = false;
        final String clientRequest = "one two two,";
        TextType concreteType = mock(TextType.class);

        final Map<String, Integer> expectedCountedResult = new HashMap();
        expectedCountedResult.put("one", 1);
        expectedCountedResult.put("two", 2);
        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 10);
            put("statisticUniqueWords", 2);
            put("statisticTotalCharacters", 12);
            put("statisticTotalWords", 3);
        }};

        ThreadResultContainer expectedContainer =
                new ThreadResultContainer(expectedCountedResult, expectedStatistic, Collections.emptyMap());

        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        CountWordsProcessor processor = new CountWordsProcessorImpl(
                textTypeInquirer, documentConverter, wordCounter, textRefiner, statistic, urlReceiver);

        // when
        final ThreadResultContainer actual = processor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(clientRequest);
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));
        verify(statistic, times(1)).getStatistic(any(String.class), any(List.class));
        verify(urlReceiver, never()).getUrlsFromPage(any(String.class), anyBoolean());

        assertEquals(expectedContainer, actual);
    }

    @Test
    public void testProcess_urlWithoutCrawling() {
        // given
        final boolean crawlingRequired = false;
        final boolean internalOnly = false;
        final String clientRequest = "http://bbc.com";
        TextType concreteType = mock(TextType.class);

        final Map<String, Integer> expectedCountedResult = new HashMap();
        expectedCountedResult.put("ukraine", 1);
        expectedCountedResult.put("obama", 2);
        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 10);
            put("statisticUniqueWords", 2);
            put("statisticTotalCharacters", 12);
            put("statisticTotalWords", 3);
        }};

        ThreadResultContainer expectedContainer =
                new ThreadResultContainer(expectedCountedResult, expectedStatistic, Collections.emptyMap());

        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        CountWordsProcessor processor = new CountWordsProcessorImpl(
                textTypeInquirer, documentConverter, wordCounter, textRefiner, statistic, urlReceiver);

        // when
        final ThreadResultContainer actual = processor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(clientRequest);
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));
        verify(statistic, times(1)).getStatistic(any(String.class), any(List.class));
        verify(urlReceiver, never()).getUrlsFromPage(any(String.class), anyBoolean());

        assertEquals(expectedContainer, actual);
    }

    @Test
    public void testProcess_urlCrawlingRequired() {
        // given
        final boolean crawlingRequired = true;
        final boolean internalOnly = false;
        final String clientRequest = "http://bbc.com";
        TextType concreteType = mock(HtmlTextTypeImpl.class);

        final Map<String, Integer> expectedCountedResult = new HashMap();
        expectedCountedResult.put("ukraine", 1);
        expectedCountedResult.put("obama", 2);
        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 10);
            put("statisticUniqueWords", 2);
            put("statisticTotalCharacters", 12);
            put("statisticTotalWords", 3);
        }};
        final Map<String, Set<String>> expectedLinks = new HashMap<String, Set<String>>();
        expectedLinks.put(clientRequest, Collections.emptySet());

        ThreadResultContainer expectedContainer =
                new ThreadResultContainer(expectedCountedResult, expectedStatistic, expectedLinks);

        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        CountWordsProcessor processor = new CountWordsProcessorImpl(
                textTypeInquirer, documentConverter, wordCounter, textRefiner, statistic, urlReceiver);

        // when
        final ThreadResultContainer actual = processor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(clientRequest);
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));
        verify(statistic, times(1)).getStatistic(any(String.class), any(List.class));
        verify(urlReceiver, times(1)).getUrlsFromPage(any(String.class), anyBoolean());

        assertEquals(expectedContainer, actual);
    }

    @Test
    public void testProcess_invalidUrlCrawlingRequired() {
        // given
        final boolean crawlingRequired = true;
        final boolean internalOnly = false;
        final String clientRequest = "http://boo0bc.com";
        TextType concreteType = mock(HtmlTextTypeImpl.class);

        final Map<String, Integer> expectedCountedResult = new HashMap();
        expectedCountedResult.put("ukraine", 1);
        expectedCountedResult.put("obama", 2);
        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 10);
            put("statisticUniqueWords", 2);
            put("statisticTotalCharacters", 12);
            put("statisticTotalWords", 3);
        }};
        final Map<String, Set<String>> expectedLinks = new HashMap<String, Set<String>>();
        expectedLinks.put(clientRequest, Collections.emptySet());

        final String expectedError = "some error";

        ThreadResultContainer expectedContainer =
                new ThreadResultContainer(Collections.emptyMap(), expectedError, Collections.emptyMap(), Collections.emptyMap());

        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(concreteConverter.convertToString(clientRequest)).thenThrow(new RuntimeException("some error"));
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        CountWordsProcessor processor = new CountWordsProcessorImpl(
                textTypeInquirer, documentConverter, wordCounter, textRefiner, statistic, urlReceiver);

        // when
        final ThreadResultContainer actual = processor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(clientRequest);
        verify(wordCounter, never()).countWords(any(List.class));
        verify(textRefiner, never()).refineText(any(String.class));
        verify(statistic, never()).getStatistic(any(String.class), any(List.class));
        verify(urlReceiver, never()).getUrlsFromPage(any(String.class), anyBoolean());

        assertEquals(expectedContainer, actual);
    }
}