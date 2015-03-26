package info.deepidea.wordcounter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class DocToStringConverterIntegrationTest {
    @Autowired
    private DocToStringConverter docToStringConverter;

    @Test
    public void testConvertToString() {
        //given
        final String url = "http://95.158.60.148:8008/kpl/test.rtf";
        final String expectedResult = "one two two three three three a a a a a a a\n";

        //when
        ConvertedDataContainer actualResult = docToStringConverter.convertToString(new RequestContainer(url));

        //then
        assertEquals(expectedResult, actualResult.getPlainText());
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_badUrl() {
        //given
        final String url = "http://www.xmlfiles.com/examples/cd_ca22123talog";

        //when
        ConvertedDataContainer actualResult = docToStringConverter.convertToString(new RequestContainer(url));

        //then
        //expected exception
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_woProtocol() {
        //given
        final String url = "www.xmlfiles.com/examples/cd_ca22123talog";

        //when
        ConvertedDataContainer actualResult = docToStringConverter.convertToString(new RequestContainer(url));

        //then
        //expected exception
    }
}