package javacourse.jUnit;

import com.qalight.javacourse.core.WordCounter;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vova on 12.08.2014.
 */
public class WordCounterTest {

    @Test
    public void testCountWords() {
        // given values
        String refinedText = "привет привет как как как твои дела отлично отлично один-один один-один two-two";

        Map<String, Integer> expected = new HashMap<>();
        expected.put("привет", 2);
        expected.put("как", 3);
        expected.put("твои", 1);
        expected.put("дела", 1);
        expected.put("отлично", 2);
        expected.put("один-один", 2);
        expected.put("two-two", 2);

        //when
        WordCounter wordCounter = new WordCounter();
        Map<String, Integer> actual = wordCounter.countWords(refinedText);

        //then
        Assert.assertEquals(expected, expected);
    }
}
