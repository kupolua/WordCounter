package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class XlsxTextTypeImplTest {
    @Spy
    private XlsxTextTypeImpl spyXlsxTextType;

    @Test
    public void testIsEligible_validType() {
        // given
        final String validTypeUrl = "http://defas.com.ua/java/textForTest.xlsx";

        doReturn(true).when(spyXlsxTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyXlsxTextType.isEligible(validTypeUrl);

        // then
        verify(spyXlsxTextType, times(1)).isEligible(anyString());
        verify(spyXlsxTextType, times(1)).isWebProtocol(anyString());
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidType() {
        // given
        final String invalidTypeUrl = "http://defas.com.ua/java/textForTest.doc";

        doReturn(true).when(spyXlsxTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyXlsxTextType.isEligible(invalidTypeUrl);

        // then
        verify(spyXlsxTextType, times(1)).isEligible(anyString());
        verify(spyXlsxTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_invalidUrl() {
        // given
        final String invalidUrl = "defas.com.ua/java/textForTest.xlsx";

        doReturn(false).when(spyXlsxTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyXlsxTextType.isEligible(invalidUrl);

        // then
        verify(spyXlsxTextType, times(1)).isEligible(anyString());
        verify(spyXlsxTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String url = " ";

        // when
        spyXlsxTextType.isEligible(url);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        // given
        final String url = null;

        // when
        spyXlsxTextType.isEligible(url);

        // then
        // exception thrown
    }
}