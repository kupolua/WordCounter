package service;

import com.qalight.javacourse.service.PdfTextTypeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PdfTextTypeImplITTest {

    PdfTextTypeImpl pdfTextType;

    @Before
    public void setup() {
        pdfTextType = new PdfTextTypeImpl();
    }

    @Test
    public void testIsEligible_okUrl() {
        // given
        final String dataSourceLink = "http://defas.com.ua/java/Policy_of_.UA.pdf";
        final boolean expectedResult = true;

        //when
        final boolean actualResult = pdfTextType.isEligible(dataSourceLink);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIsEligible_improperUrl() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/testingPage.html";
        final boolean expectedResult = false;

        //when
        final boolean actualResult = pdfTextType.isEligible(dataSourceLink);

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
            pdfTextType.isEligible(dataSourceLink);
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
            pdfTextType.isEligible(dataSourceLink);
        } catch (NullPointerException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedExceptoin, actualException.toString());
    }

}