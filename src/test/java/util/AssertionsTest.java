package util;

import com.qalight.javacourse.util.Assertions;
import org.junit.Test;

public class AssertionsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsNull()  {
        //given
        final String DATA = null;

        //when
        Assertions.assertStringIsNotNullOrEmpty(DATA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsEmpty()  {
        //given
        final String DATA = " ";

        //when
        Assertions.assertStringIsNotNullOrEmpty(DATA);
    }

    @Test
    public void testAssertStringIsNotNullOrEmpty() {
        //given
        final String DATA = "index.html";

        //when
        Assertions.assertStringIsNotNullOrEmpty(DATA);

        //then
        // no exception thrown
    }
}