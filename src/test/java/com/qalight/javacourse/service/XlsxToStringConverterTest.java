package com.qalight.javacourse.service;

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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class XlsxToStringConverterTest {
    @Mock InputStream stream;
    @Spy private XlsxToStringConverter spyConverter;
    private XlsxToStringConverter xlsxToStringConverter;

    @Before
    public void setUp() throws Exception {
        xlsxToStringConverter = new XlsxToStringConverter();
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType xlsxTextType = new XlsxTextTypeImpl();

        //when
        boolean actualResult = xlsxToStringConverter.isEligible(xlsxTextType);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {
        //given
        final TextType xlsxTextType = new DocTextTypeImpl();

        //when
        boolean actualResult = xlsxToStringConverter.isEligible(xlsxTextType);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testConvertToString() throws Exception {
        //given
        final String input = "http://xlsx-examples.com/example.xlsx";
        final String expected = "example text";

        doReturn(stream).when(spyConverter).openStream(input);
        doReturn(expected).when(spyConverter).extractXlsxWithoutSheetName(stream);

        //when
        final String actualResult = spyConverter.convertToString(input);

        //then
        verify(spyConverter).openStream(input);
        verify(spyConverter).extractXlsxWithoutSheetName(stream);

        assertEquals(expected, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_throwingException() throws Exception {
        //given
        final String input = "http://xls-examples.com/example.xls";
        final String expected = "example text";

        doReturn(stream).when(spyConverter).openStream(input);
        doThrow(new IOException("test")).when(spyConverter).extractXlsxWithoutSheetName(stream);

        //when
        final String actualResult = spyConverter.convertToString(input);

        //then
        verify(spyConverter).openStream(input);
        verify(spyConverter).extractXlsxWithoutSheetName(stream);
    }
}