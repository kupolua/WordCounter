package util;

import com.qalight.javacourse.util.TextRefiner;
import org.junit.Assert;
import org.junit.Test;

public class TextRefinerTest {

    @Test
    public void testGetRefineText() {

        // given values
        String text = "One, one ONE oNE  Two  two, two!@#$%^&*()_+=!123456789";
        String expected = "one  one one one  two  two  two                       ";
        //when
        String actual = TextRefiner.getRefineText(text);
        //then
        Assert.assertEquals(expected, actual);

    }
}
