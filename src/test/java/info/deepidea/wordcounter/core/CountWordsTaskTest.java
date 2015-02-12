//package info.deepidea.wordcounter.core;
//
//import info.deepidea.wordcounter.service.ThreadResultContainer;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CountWordsTaskTest {
//    @Mock private CountWordsProcessor wordsProcessor;
//
//    @Test
//    public void testCall() throws Exception {
//        //given
//        final String clientRequest = "one two two,";
//
//        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 9);
//            put("statisticUniqueWords", 2);
//            put("statisticTotalCharacters", 11);
//            put("statisticTotalWords", 3);
//        }};
//        final Map<String, Integer> expectedResult = new HashMap<>();
//        expectedResult.put("one", 1);
//        expectedResult.put("two", 2);
//        ThreadResultContainer expectedContainer = new ThreadResultContainer(expectedResult, expectedStatistic);
//        when(wordsProcessor.process(anyString())).thenReturn(expectedContainer);
//
//        CountWordsTask wordsTask = new CountWordsTask(clientRequest, wordsProcessor);
//
//        //when
//        final ThreadResultContainer actualContainer = wordsTask.call();
//
//        //then
//        verify(wordsProcessor, times(1)).process(anyString());
//
//        assertEquals(expectedContainer, actualContainer);
//    }
//}