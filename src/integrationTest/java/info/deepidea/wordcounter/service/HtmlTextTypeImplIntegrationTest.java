package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class HtmlTextTypeImplIntegrationTest {

    @Autowired
    HtmlTextTypeImpl htmlTextType;

    @Test
    public void testIsEligible_woExtension() {
        //given
        final String url = "http://www.w3schools.com/xml/cd_catalog";

        //when
        final boolean actualResult = htmlTextType.isEligible(url);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_html() {
        //given
        final String url = "http://sergeyteplyakov.blogspot.com/2014/09/the-dependency-inversion-principle.html";

        //when
        final boolean actualResult = htmlTextType.isEligible(url);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_xml() {
        //given
        final String url = "http://www.w3schools.com/xml/cd_catalog.xml";

        //when
        final boolean actualResult = htmlTextType.isEligible(url);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_improperType() {
        //given
        final String textHttpHeader = "http://www.w3schools.com/xml/cd_catalog.pdf";

        //when
        final boolean actualResult = htmlTextType.isEligible(textHttpHeader);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyUrl() {
        //given
        final String textHttpHeader = "";

        //when
        htmlTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullUrl() {
        //given
        final String textHttpHeader = null;

        //when
        htmlTextType.isEligible(textHttpHeader);

        //then expected exception
    }

}