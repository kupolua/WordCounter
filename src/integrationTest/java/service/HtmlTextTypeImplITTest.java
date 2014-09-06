package service;

import com.qalight.javacourse.service.HtmlTextTypeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HtmlTextTypeImplITTest {

    HtmlTextTypeImpl htmlTextType;

    @Before
    public void setup() {
        htmlTextType = new HtmlTextTypeImpl();
    }

    @Test
    public void testIsEligible_html() {
        //given
        final String DATA_SOURCE_LINK = "http://defas.com.ua/java/testingPage.html";

        //when
        final boolean actualResult = htmlTextType.isEligible(DATA_SOURCE_LINK);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_xml() {
        //given
        final String DATA_SOURCE_LINK = "http://www.xmlfiles.com/examples/cd_catalog.xml";

        //when
        final boolean actualResult = htmlTextType.isEligible(DATA_SOURCE_LINK);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_improperUrl() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/Policy_of_.UA.pdf";
        final boolean expectedResult = false;

        //when
        final boolean actualResult = htmlTextType.isEligible(dataSourceLink);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIsEligible_emptyUrl() {
        //given
        final String dataSourceLink = "";
        final String expectedExceptoin = "java.lang.IllegalArgumentException: " +
                "It is impossible to determine the type of the document because the link is empty.";

        //when
        Exception actualException = null;
        try {
            htmlTextType.isEligible(dataSourceLink);
        } catch (IllegalArgumentException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedExceptoin, actualException.toString());
    }

    @Test
    public void testIsEligible_nullUrl() {
        //given
        final String dataSourceLink = null;
        final String expectedExceptoin = "java.lang.NullPointerException: " +
                "It is impossible to determine the type of the document because the link is null.";

        //when
        Exception actualException = null;
        try {
            htmlTextType.isEligible(dataSourceLink);
        } catch (NullPointerException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedExceptoin, actualException.toString());
    }

}