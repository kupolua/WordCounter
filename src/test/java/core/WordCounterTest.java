package core;

import com.qalight.javacourse.core.WordCounter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounterTest {

    private WordCounter counter;

    @Before
    public void setup(){
        counter = new WordCounter();
    }

    @Test
    public void testCountWords() {
        // given values
        List<String> words = Arrays.asList("one", "one", "one", "one", "two", "two", "two");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("one", 4);
        expected.put("two", 3);

        //when
        Map<String, Integer> actual = counter.countWords(words);

        //then
        Assert.assertEquals(expected, actual);
    }
}
