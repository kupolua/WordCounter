package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class PdfToStringConverterIntegrationTest {

    @Autowired
    private PdfToStringConverter pdfToStringConverter;

    @Test
    public void testConvertToString() {
        //given
        final String url = "https://dl.dropboxusercontent.com/u/12495182/tests/words.pdf";
        final String expected = "Words Count\nsimply 1\nput 1";

        //when
        ConvertedDataContainer actualResult = pdfToStringConverter.convertToString(new RequestContainer(url));

        //then
        Assert.assertEquals(expected, actualResult.getPlainText());
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_badUrl() {
        //given
        final String url = "http://www.xmlfiles.com/examples/cd_ca22123talog.xml";

        //when
        ConvertedDataContainer actualResult = pdfToStringConverter.convertToString(new RequestContainer(url));

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToString_null() {
        //given
        final String url = null;

        //when
        ConvertedDataContainer actualResult = pdfToStringConverter.convertToString(new RequestContainer(url));

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToString_empty() {
        //given
        final String url = "";

        //when
        ConvertedDataContainer actualResult = pdfToStringConverter.convertToString(new RequestContainer(url));

        //then
        //expected exception
    }
}