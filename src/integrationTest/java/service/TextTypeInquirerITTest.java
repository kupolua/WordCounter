package service;

import com.qalight.javacourse.service.DocTextTypeImpl;
import com.qalight.javacourse.service.TextType;
import com.qalight.javacourse.service.TextTypeInquirer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextTypeInquirerITTest {

    private TextTypeInquirer textTypeInquirer;

    @Before
    public void setup() {
        textTypeInquirer = new TextTypeInquirer();
    }

    @Test
    public void testInquireTextType_html() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/testingPage.html";
        final String expectedResult = "com.qalight.javacourse.service.HtmlTextTypeImpl@";

        //when
        final TextType actualResult = textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        Assert.assertTrue(actualResult.toString().startsWith(expectedResult));
    }

    @Test
    public void testInquireTextType_doc() {
        //given
        final String dataSourceLink = "http://www.snee.com/xml/xslt/sample.doc";

        //when
        final TextType actualResult = textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        Assert.assertTrue(actualResult instanceof DocTextTypeImpl);
    }

    @Test
    public void testInquireTextType_pdf() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/Policy_of_.UA.pdf";
        final String expectedResult = "com.qalight.javacourse.service.PdfTextTypeImpl@";

        //when
        final TextType actualResult = textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        Assert.assertTrue(actualResult.toString().startsWith(expectedResult));
    }

    @Test
    public void testInquireTextType_usupportedFormat() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/fdbasecd.iso";
        final String expectedResult = "java.lang.IllegalArgumentException: Unknown text type. (" + dataSourceLink + ")";

        //when
        Exception actualException = null;
        try {
            textTypeInquirer.inquireTextType(dataSourceLink);
        } catch (IllegalArgumentException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedResult, actualException.toString());
    }

    @Test
    public void testInquireTextType_emptyUrl() {
        //given
        final String dataSourceLink = "";
        final String expectedExceptoin = "java.lang.IllegalArgumentException: " +
                "It is impossible to determine the type of the document because the link is empty.";

        //when
        Exception actualException = null;
        try {
            textTypeInquirer.inquireTextType(dataSourceLink);
        } catch (IllegalArgumentException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedExceptoin, actualException.toString());
    }

    @Test
    public void testInquireTextType_NullUrl() {
        //given
        final String dataSourceLink = null;
        final String expectedExceptoin = "java.lang.NullPointerException: " +
                "It is impossible to determine the type of the document because the link is null.";

        //when
        Exception actualException = null;
        try {
            textTypeInquirer.inquireTextType(dataSourceLink);
        } catch (NullPointerException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedExceptoin, actualException.toString());
    }

}