package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class DocTextTypeImplIntegrationTest {

    @Autowired
    private DocTextTypeImpl docTextType;

    @Test
    public void testIsEligible_invalidPdfType() {
        //given
        final String textHttpHeader = "https://dl.dropboxusercontent.com/u/12495182/33.pdf";
        //when
        boolean actualResult = docTextType.isEligible(textHttpHeader);
        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_validTypes() {
        //given
        final String[] url = {
                "https://dl.dropboxusercontent.com/u/12495182/%D0%B7%D0%BE%D0%BA.rtf",
                "https://dl.dropboxusercontent.com/u/12495182/33.docx"
        };

        //when
        boolean actualResult = checkIfSourceIsEligible(url);

        //then
        final boolean expectedResult = true;
        Assert.assertEquals(expectedResult, actualResult);
    }

    private boolean checkIfSourceIsEligible(String[] list) {
        boolean result = true;
        for (String link : list) {
            if (!docTextType.isEligible(link)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullLink() {
        //given
        final String url = null;

        //when
        docTextType.isEligible(url);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyLink() {
        //given
        final String url = "";

        //when
        docTextType.isEligible(url);

        //then
        //expected exception
    }
}