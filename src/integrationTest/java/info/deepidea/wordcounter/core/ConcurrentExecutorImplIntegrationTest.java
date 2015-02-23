package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.service.ThreadResultContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class ConcurrentExecutorImplIntegrationTest {

    @Autowired
    private ConcurrentExecutor executor;

    @Test
    public void testCountAsynchronously_singleInput(){
        // given
        final int depth = 0;
        final boolean internalOnly = true;
        Set<String> input = new HashSet<String>();
        input.add("word word, word");

        // when
        List<ThreadResultContainer> actual = executor.countAsynchronously(input, depth, internalOnly);

        // then
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("word", 3);
        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 13);
            put("statisticUniqueWords", 1);
            put("statisticTotalCharacters", 15);
            put("statisticTotalWords", 3);
        }};

        ThreadResultContainer expectedConteiner =
                new ThreadResultContainer(expectedResult, expectedStatistic, Collections.emptyMap());

        assertEquals(1, actual.size());
        assertEquals(expectedConteiner, actual.get(0));
    }

    @Test
    public void testCountAsynchronously_multipleInput(){
        // given
        final int depth = 0;
        final boolean internalOnly = true;
        Set<String> input = new LinkedHashSet<>();
        input.add("word word, word");
        input.add("hello hello");
        input.add("jump");

        // when
        List<ThreadResultContainer> actual = executor.countAsynchronously(input, depth, internalOnly);

        // then
        final Map<String, Integer> expectedStatistic0 = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 13);
            put("statisticUniqueWords", 1);
            put("statisticTotalCharacters", 15);
            put("statisticTotalWords", 3);
        }};
        Map<String, Integer> expectedResult0 = new HashMap<>();
        expectedResult0.put("word", 3);
        ThreadResultContainer container0 =
                new ThreadResultContainer(expectedResult0, expectedStatistic0, Collections.emptyMap());

        final Map<String, Integer> expectedStatistic1 = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 10);
            put("statisticUniqueWords", 1);
            put("statisticTotalCharacters", 11);
            put("statisticTotalWords", 2);
        }};
        Map<String, Integer> expectedResult1 = new HashMap<>();
        expectedResult1.put("hello", 2);
        ThreadResultContainer container1 =
                new ThreadResultContainer(expectedResult1, expectedStatistic1, Collections.emptyMap());

        final Map<String, Integer> expectedStatistic2 = new HashMap<String, Integer>() {{
            put("statisticCharactersWithoutSpaces", 4);
            put("statisticUniqueWords", 1);
            put("statisticTotalCharacters", 4);
            put("statisticTotalWords", 1);
        }};
        Map<String, Integer> expectedResult2 = new HashMap<>();
        expectedResult2.put("jump", 1);
        ThreadResultContainer container2 =
                new ThreadResultContainer(expectedResult2, expectedStatistic2, Collections.emptyMap());

        List<ThreadResultContainer> expected = new ArrayList<>();
        expected.add(container0);
        expected.add(container1);
        expected.add(container2);

        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }
}