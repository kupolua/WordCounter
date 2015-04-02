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
    private RequestContainer requestContainer;

    @Before
    public void setUp() throws Exception {
        final String input = "http://xls-examples.com/example.xls";
        requestContainer = new RequestContainer(input);
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
        final String expected = "example text";

        doReturn(stream).when(spyConverter).openStream(requestContainer.getClientRequest());
        doReturn(expected).when(spyConverter).extractXlsWithoutSheetName(stream);

        //when
        final ConvertedDataContainer actualResult = spyConverter.convertToString(requestContainer);

        //then
        verify(spyConverter).openStream(requestContainer.getClientRequest());
        verify(spyConverter).extractXlsWithoutSheetName(stream);

        assertEquals(expected, actualResult.getPlainText());
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_throwingException() throws Exception {
        //given
        doReturn(stream).when(spyConverter).openStream(requestContainer.getClientRequest());
        doThrow(new IOException("test")).when(spyConverter).extractXlsWithoutSheetName(stream);

        //when
        final ConvertedDataContainer actualResult = spyConverter.convertToString(requestContainer);

        //then
        verify(spyConverter).openStream(requestContainer.getClientRequest());
        verify(spyConverter).extractXlsWithoutSheetName(stream);
    }
}