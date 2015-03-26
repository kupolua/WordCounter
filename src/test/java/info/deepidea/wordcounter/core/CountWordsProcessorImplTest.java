package info.deepidea.wordcounter.core;

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
    private ConvertedDataContainer dataContainer;
    private CountWordsProcessorImpl spyProcessor;

    @Before
    public void setUp() {
        textTypeInquirer = mock(TextTypeInquirer.class);
        documentConverter = mock(DocumentConverter.class);
        concreteConverter = mock(DocumentToStringConverter.class);
        wordCounter = mock(WordCounterImpl.class);
        textRefiner = mock(TextRefiner.class);
        statistic = mock(WordStatistic.class);
        dataContainer = mock(ConvertedDataContainer.class);
        spyProcessor = spy(new CountWordsProcessorImpl(
                textTypeInquirer, documentConverter, wordCounter, textRefiner, statistic));
    }

    @Test
    public void testProcess_text() {
        // given
        final boolean crawlingRequired = false;
        final boolean internalOnly = false;
        final String clientRequest = "one two two,";
        final RequestContainer requestContainer = new RequestContainer(clientRequest, crawlingRequired, internalOnly);
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
        doReturn(requestContainer).when(spyProcessor).getRequestContainer(clientRequest, crawlingRequired, internalOnly);
        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(concreteConverter.convertToString(requestContainer)).thenReturn(dataContainer);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        // when
        final ThreadResultContainer actual = spyProcessor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(requestContainer);
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));
        verify(statistic, times(1)).getStatistic(any(String.class), any(List.class));

        assertEquals(expectedContainer, actual);
    }

    @Test
    public void testProcess_textCrawlingRequired() {
        // given
        final boolean crawlingRequired = true;
        final boolean internalOnly = false;
        final String clientRequest = "one two two,";
        final RequestContainer requestContainer = new RequestContainer(clientRequest, crawlingRequired, internalOnly);
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
        doReturn(requestContainer).when(spyProcessor).getRequestContainer(clientRequest, crawlingRequired, internalOnly);
        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(concreteConverter.convertToString(requestContainer)).thenReturn(dataContainer);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        // when
        final ThreadResultContainer actual = spyProcessor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(requestContainer);
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));
        verify(statistic, times(1)).getStatistic(any(String.class), any(List.class));

        assertEquals(expectedContainer, actual);
    }

    @Test
    public void testProcess_urlWithoutCrawling() {
        // given
        final boolean crawlingRequired = false;
        final boolean internalOnly = false;
        final String clientRequest = "http://bbc.com";
        final RequestContainer requestContainer = new RequestContainer(clientRequest, crawlingRequired, internalOnly);
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

        doReturn(requestContainer).when(spyProcessor).getRequestContainer(clientRequest, crawlingRequired, internalOnly);
        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(concreteConverter.convertToString(requestContainer)).thenReturn(dataContainer);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        // when
        final ThreadResultContainer actual = spyProcessor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(requestContainer);
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));
        verify(statistic, times(1)).getStatistic(any(String.class), any(List.class));

        assertEquals(expectedContainer, actual);
    }

    @Test
    public void testProcess_urlCrawlingRequired() {
        // given
        final boolean crawlingRequired = true;
        final boolean internalOnly = false;
        final String clientRequest = "http://bbc.com";
        final RequestContainer requestContainer = new RequestContainer(clientRequest, crawlingRequired, internalOnly);
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

        doReturn(requestContainer).when(spyProcessor).getRequestContainer(clientRequest, crawlingRequired, internalOnly);
        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(concreteConverter.convertToString(requestContainer)).thenReturn(dataContainer);
        when(dataContainer.getRelatedLinks()).thenReturn(expectedLinks);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        // when
        final ThreadResultContainer actual = spyProcessor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(requestContainer);
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));
        verify(statistic, times(1)).getStatistic(any(String.class), any(List.class));

        assertEquals(expectedContainer, actual);
    }

    @Test
    public void testProcess_invalidUrlCrawlingRequired() {
        // given
        final boolean crawlingRequired = true;
        final boolean internalOnly = false;
        final String clientRequest = "http://boo0bc.com";
        final RequestContainer requestContainer = new RequestContainer(clientRequest, crawlingRequired, internalOnly);
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
                new ThreadResultContainer(expectedError);

        doReturn(requestContainer).when(spyProcessor).getRequestContainer(clientRequest, crawlingRequired, internalOnly);
        when(textTypeInquirer.inquireTextType(clientRequest)).thenReturn(concreteType);
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(concreteConverter);
        when(concreteConverter.convertToString(requestContainer)).thenThrow(new RuntimeException("some error"));
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedCountedResult);
        when(statistic.getStatistic(any(String.class), any(List.class))).thenReturn(expectedStatistic);

        // when
        final ThreadResultContainer actual = spyProcessor.process(clientRequest, crawlingRequired, internalOnly);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(concreteConverter, times(1)).convertToString(requestContainer);
        verify(wordCounter, never()).countWords(any(List.class));
        verify(textRefiner, never()).refineText(any(String.class));
        verify(statistic, never()).getStatistic(any(String.class), any(List.class));

        assertEquals(expectedContainer, actual);
    }
}