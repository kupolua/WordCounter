package service;

import com.qalight.javacourse.service.HtmlTextTypeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HtmlTextTypeImplTest {

    private HtmlTextTypeImpl typeImpl;

    @Before
    public void setup() {
        typeImpl = new HtmlTextTypeImpl();
    }

    @Test
    public void testIsEligible_woExtension() {
        //given
        final String sourceLink1 = "index";

        //when
        boolean isEligible1 = typeImpl.isEligible(sourceLink1);

        //then
        Assert.assertTrue(isEligible1);
    }

    @Test
    public void testIsEligible_pdfExtension() {
        //given
        final String sourceLink2 = "index.pdf";

        //when
        boolean isEligible2 = typeImpl.isEligible(sourceLink2);

        //then
        Assert.assertFalse(isEligible2);
    }

    @Test
    public void testIsEligible_xmlExtension() {
        //given
        final String sourceLink3 = "index.xml";

        //when
        boolean isEligible3 = typeImpl.isEligible(sourceLink3);

        //then
        Assert.assertFalse(isEligible3);
    }

    @Test
    public void testIsEligible_htmlExtension() {
        //given
        final String sourceLink = "src/main/webapp/index.html";

        //when
        boolean isEligible = typeImpl.isEligible(sourceLink);

        //then
        Assert.assertTrue(isEligible);
    }
}