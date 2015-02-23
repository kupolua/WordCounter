package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.service.ThreadResultContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class CountWordsProcessorImplIntegrationTest {

    @Autowired
    CountWordsProcessor processor;

    @Test
    public void testProcess_textInput() throws Exception {
        // given
        final boolean crawlingRequired = false;
        final boolean internalOnly = false;
        String input = "one, two,  two";

        // when
        ThreadResultContainer actual = processor.process(input, crawlingRequired, internalOnly);

        // then
        Map<String, Integer> expected = new HashMap<>();
        expected.put("one", 1);
        expected.put("two", 2);

        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 11);
            put("statisticUniqueWords", 2);
            put("statisticTotalCharacters", 14);
            put("statisticTotalWords", 3);
        }};

        assertEquals(expected, actual.getCountedResult());
        assertEquals(expectedStatistic, actual.getWordStatistic());
        assertTrue(actual.getRelatedLinks().isEmpty());
    }

    @Test(timeout = 120_000)
    public void testProcess_bigPdf() {
        // given
        final boolean crawlingRequired = false;
        final boolean internalOnly = false;
        String input = "http://95.158.60.148:8008/kpl/King-James-Bible-KJV-Bible-PDF.pdf";

        // when
        ThreadResultContainer actual = processor.process(input, crawlingRequired, internalOnly);


        // then
        assertTrue(actual.getCountedResult().size() > 100);
    }
}