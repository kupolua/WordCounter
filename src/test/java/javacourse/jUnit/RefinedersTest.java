package javacourse.jUnit;

import com.qalight.javacourse.util.Refineder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Vova on 11.08.2014.
 */
public class RefinedersTest {

    @Test
    public void testGetRefineText() {

        // given values
        String text = "Привет! Как твои дела? Отлично! Один-один, два-два, two - two!@#$%^&*()_+=!123456789";
        String expectedList = "привет как твои дела отлично один-один два-два two-two";
        //when
        String actualList = Refineder.getRefineText(text);
        //then
        Assert.assertEquals(expectedList, expectedList);

    }
}
