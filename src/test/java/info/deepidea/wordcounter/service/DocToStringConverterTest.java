package info.deepidea.wordcounter.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.IOException;
import java.net.URL;

@RunWith(MockitoJUnitRunner.class)
public class DocToStringConverterTest {
    @Mock private Tika tika;
    @Spy private DocToStringConverter spyConverter;
    private DocToStringConverter docToStringConverter;

    @Before
    public void setUp() throws Exception {
        docToStringConverter = new DocToStringConverter();
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType docTextType = new DocTextTypeImpl();

        //when
        boolean actualResult = docToStringConverter.isEligible(docTextType);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {
        //given
        final TextType docTextType = new PdfTextTypeImpl();

        //when
        boolean actualResult = docToStringConverter.isEligible(docTextType);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void convertToString() throws Exception{
        //given
        final String input = "http://doc-examples.com/example.doc";
        final String expected = "some text";

        doReturn(tika).when(spyConverter).getTika();
        when(tika.parseToString(any(URL.class))).thenReturn(expected);

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, times(1)).getTika();
        verify(tika, times(1)).parseToString(any(URL.class));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void convertToString_throwingIOException() throws Exception{
        //given
        final String input = "http://doc-examples.com/example.doc";

        doReturn(tika).when(spyConverter).getTika();
        when(tika.parseToString(any(URL.class))).thenThrow(new IOException("test"));

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, times(1)).getTika();
        verify(tika, times(1)).parseToString(any(URL.class));
        // exception expected
    }

    @Test(expected = RuntimeException.class)
    public void convertToString_throwingTikaException() throws Exception{
        //given
        final String input = "http://doc-examples.com/example.doc";

        doReturn(tika).when(spyConverter).getTika();
        when(tika.parseToString(any(URL.class))).thenThrow(new TikaException("test"));

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, times(1)).getTika();
        verify(tika, times(1)).parseToString(any(URL.class));
        // exception expected
    }
}