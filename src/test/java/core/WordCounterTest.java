package core;

import com.qalight.javacourse.core.WordCounter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class WordCounterTest {

    private WordCounter counter;

    @Before
    public void setup(){
        counter = new WordCounter();
    }

    @Test
    public void testCountWords() {
        // given
        List<String> words = Arrays.asList("one", "one", "one", "one", "two", "two", "two");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("one", 4);
        expected.put("two", 3);

        //when
        Map<String, Integer> actual = counter.countWords(words);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCountWords_empty() {
        // given
        List<String> words = Arrays.asList("");

        Map<String, Integer> expected = new HashMap<>();

        //when
        Map<String, Integer> actual = counter.countWords(words);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCountWords_null() {
        // given
        final List<String> words = null;
        final String expectedException = "java.lang.NullPointerException: " +
                "In WordCounter.countWords(List<String> refinedWords) received parameter is NULL.";

        //when
        NullPointerException actualException = null;
        try {
            counter.countWords(words);
        } catch (NullPointerException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedException, actualException.toString());
    }
}
