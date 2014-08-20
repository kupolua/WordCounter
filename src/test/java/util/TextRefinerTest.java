package util;

import com.qalight.javacourse.util.TextRefiner;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TextRefinerTest {

    @Test
    public void testGetRefinedText() {

        //given
        String text = "One, one ONE-oNE  Two  two, two!";
        //when
        List<String> actual = TextRefiner.getRefinedText(text);
        //then
        List<String> expected = Arrays.asList("one", "one", "one-one", "two", "two", "two");
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testEmptyParameter() {
        //given
        String text = "";
        //when
        List<String> actual = TextRefiner.getRefinedText(text);
        //then
        List<String> expected = Arrays.asList("");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameter() {
        String text = null;
        TextRefiner.getRefinedText(text);
    }
}
