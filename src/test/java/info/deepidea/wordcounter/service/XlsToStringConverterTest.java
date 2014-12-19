package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class XlsToStringConverterTest {
    @Mock InputStream stream;
    @Spy private XlsToStringConverter spyConverter;
    private XlsToStringConverter xlsToStringConverter;

    @Before
    public void setUp() throws Exception {
        xlsToStringConverter = new XlsToStringConverter();
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType xlsTextType = new XlsTextTypeImpl();

        //when
        boolean actualResult = xlsToStringConverter.isEligible(xlsTextType);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {
        //given
        final TextType xlsTextType = new DocTextTypeImpl();

        //when
        boolean actualResult = xlsToStringConverter.isEligible(xlsTextType);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testConvertToString() throws Exception {
        //given
        final String input = "http://xls-examples.com/example.xls";
        final String expected = "example text";

        doReturn(stream).when(spyConverter).openStream(input);
        doReturn(expected).when(spyConverter).extractXlsWithoutSheetName(stream);

        //when
        final String actualResult = spyConverter.convertToString(input);

        //then
        verify(spyConverter).openStream(input);
        verify(spyConverter).extractXlsWithoutSheetName(stream);

        assertEquals(expected, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_throwingException() throws Exception {
        //given
        final String input = "http://xls-examples.com/example.xls";
        final String expected = "example text";

        doReturn(stream).when(spyConverter).openStream(input);
        doThrow(new IOException("test")).when(spyConverter).extractXlsWithoutSheetName(stream);

        //when
        final String actualResult = spyConverter.convertToString(input);

        //then
        verify(spyConverter).openStream(input);
        verify(spyConverter).extractXlsWithoutSheetName(stream);
    }
}