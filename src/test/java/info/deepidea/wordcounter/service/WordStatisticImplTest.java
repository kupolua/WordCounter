package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.WordCounterArgumentException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class WordStatisticImplTest {
    private WordStatistic statistic;

    @Before
    public void setUp() throws Exception {
        statistic = new WordStatisticImpl();
    }

    @Test
    public void testGetStatistic() throws Exception {
        // given
        final String plainText = "one two two";
        final List<String> refinedWords = Arrays.asList("one", "two", "two");

        // when
        Map<String, Integer> actualStatistic = statistic.getStatistic(plainText, refinedWords);

        // then
        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 9);
            put("statisticUniqueWords", 2);
            put("statisticTotalCharacters", 11);
            put("statisticTotalWords", 3);
        }};

        assertEquals(expectedStatistic, actualStatistic);
    }

    @Test(expected = WordCounterArgumentException.class)
    public void testGetStatistic_emptyText() throws Exception {
        // given
        final String plainText = "";
        final List<String> refinedWords = Arrays.asList("one", "two", "two");

        // when
        Map<String, Integer> actualStatistic = statistic.getStatistic(plainText, refinedWords);

        // then
        // expected exception
    }

    @Test(expected = WordCounterArgumentException.class)
    public void testGetStatistic_emptyWordsList() throws Exception {
        // given
        final String plainText = "one two two";
        final List<String> refinedWords = Collections.EMPTY_LIST;

        // when
        Map<String, Integer> actualStatistic = statistic.getStatistic(plainText, refinedWords);

        // then
        // expected exception
    }
}