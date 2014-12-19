package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class PlainTextTypeImplIntegrationTest {

    @Autowired
    private PlainTextTypeImpl plainTextType;

    @Test
    public void testIsEligible() {
        //given
        final String url = "plain text type";

        //when
        boolean actualResult = plainTextType.isEligible(url);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() throws Exception {
        //given
        final String url = "https://dl.dropboxusercontent.com/u/12495182/About%20us.pdf";

        //when
        boolean actualResult = plainTextType.isEligible(url);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullLink() {
        //given
        final String url = null;

        //when
        plainTextType.isEligible(url);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyLink() {
        //given
        final String url = "";

        //when
        plainTextType.isEligible(url);

        //then
        //expected exception
    }
}