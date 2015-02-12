//package info.deepidea.wordcounter.core;
//
//import info.deepidea.wordcounter.service.ThreadResultContainer;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.*;
//
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertTrue;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
//public class ConcurrentExecutorImplIntegrationTest {
//
//    @Autowired
//    private ConcurrentExecutor executor;
//
//    @Test
//    public void testCountAsynchronously_singleInput(){
//        // given
//        Set<String> input = new HashSet<>();
//        input.add("word word, word");
//
//        // when
//        List<ThreadResultContainer> actual = executor.countAsynchronously(input);
//
//        // then
//        Map<String, Integer> expectedResult = new HashMap<>();
//        expectedResult.put("word", 3);
//
//        assertEquals(1, actual.size());
//        assertEquals(expectedResult, actual.get(0).getCountedResult());
//    }
//
//    @Test
//    public void testCountAsynchronously_multipleInput(){
//        // given
//        Set<String> input = new LinkedHashSet<>();
//        input.add("word word, word");
//        input.add("hello hello");
//        input.add("jump");
//
//        // when
//        List<ThreadResultContainer> actual = executor.countAsynchronously(input);
//
//        // then
//        final Map<String, Integer> expectedStatistic0 = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 13);
//            put("statisticUniqueWords", 1);
//            put("statisticTotalCharacters", 15);
//            put("statisticTotalWords", 3);
//        }};
//        Map<String, Integer> expectedResult0 = new HashMap<>();
//        expectedResult0.put("word", 3);
//        ThreadResultContainer container0 = new ThreadResultContainer(expectedResult0, expectedStatistic0);
//
//        final Map<String, Integer> expectedStatistic1 = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 10);
//            put("statisticUniqueWords", 1);
//            put("statisticTotalCharacters", 11);
//            put("statisticTotalWords", 2);
//        }};
//        Map<String, Integer> expectedResult1 = new HashMap<>();
//        expectedResult1.put("hello", 2);
//        ThreadResultContainer container1 = new ThreadResultContainer(expectedResult1, expectedStatistic1);
//
//        final Map<String, Integer> expectedStatistic2 = new HashMap<String, Integer>() {{
//            put("statisticСharactersWithoutSpaces", 4);
//            put("statisticUniqueWords", 1);
//            put("statisticTotalCharacters", 4);
//            put("statisticTotalWords", 1);
//        }};
//        Map<String, Integer> expectedResult2 = new HashMap<>();
//        expectedResult2.put("jump", 1);
//        ThreadResultContainer container2 = new ThreadResultContainer(expectedResult2, expectedStatistic2);
//
//
//        List<ThreadResultContainer> expected = new ArrayList<>();
//        expected.add(container0);
//        expected.add(container1);
//        expected.add(container2);
//
//        assertEquals(3, actual.size());
//        assertTrue(equalCollection(expected, actual));
//    }
//
//    private boolean equalCollection(List<ThreadResultContainer> expected, List<ThreadResultContainer> actual) {
//        for(int x = 0; x < expected.size(); x++){
//            ThreadResultContainer expectedContainer = expected.get(x);
//            ThreadResultContainer actualContainer = actual.get(x);
//            if (!expectedContainer.equals(actualContainer)) {
//                return false;
//            }
//        }
//        return true;
//    }
//}