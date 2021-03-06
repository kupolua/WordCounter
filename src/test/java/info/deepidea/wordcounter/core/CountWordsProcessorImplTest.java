package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.service.*;
import info.deepidea.wordcounter.util.TextRefiner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountWordsProcessorImplTest {

    @Test
    public void testProcess() {
        // given
        final String inputText = "one two two,";

        TextTypeInquirer textTypeInquirer = mock(TextTypeInquirer.class);
        DocumentConverter documentConverter = mock(DocumentConverter.class);
        WordCounter wordCounter = mock(WordCounterImpl.class);
        TextRefiner textRefiner = mock(TextRefiner.class);

        when(textTypeInquirer.inquireTextType(inputText)).thenReturn(new PlainTextTypeImpl());
        when(documentConverter.getDocumentConverter(any(TextType.class))).thenReturn(new PlainToStringConverter());

        final Map<String, Integer> expectedResult = new HashMap();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);
        when(wordCounter.countWords(any(List.class))).thenReturn(expectedResult);

        CountWordsProcessor processor = new CountWordsProcessorImpl(
                textTypeInquirer, documentConverter, wordCounter, textRefiner);

        // when
        final ThreadResultContainer actual = processor.process(inputText);

        // then
        verify(textTypeInquirer, times(1)).inquireTextType(any(String.class));
        verify(documentConverter, times(1)).getDocumentConverter(any(TextType.class));
        verify(wordCounter, times(1)).countWords(any(List.class));
        verify(textRefiner, times(1)).refineText(any(String.class));

        assertEquals(expectedResult, actual.getCountedResult());
    }
}