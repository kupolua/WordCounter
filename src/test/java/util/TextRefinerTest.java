package util;

import com.qalight.javacourse.util.TextRefiner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class TextRefinerTest {

    private TextRefiner refiner;

    @Before
    public void setup(){
        refiner = new TextRefiner();
    }

    @Test
    public void testGetRefinedText() {

        //given
        String text = "One, one ONE-oNE  Two  two, two!, three, three, усіх";
        //when
        List<String> actual = refiner.getRefinedText(text);
        //then
        List<String> expected = Arrays.asList("one", "one", "one-one", "two", "two", "two", "three", "three", "усіх");
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testEmptyParameter() {
        //given
        String text = "";
        //when
        List<String> actual = refiner.getRefinedText(text);
        //then
        List<String> expected = Arrays.asList("");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameter() {
        String text = null;
        refiner.getRefinedText(text);
    }
}
