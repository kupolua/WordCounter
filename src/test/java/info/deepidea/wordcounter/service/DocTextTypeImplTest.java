package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DocTextTypeImplTest {
    @Spy
    private DocTextTypeImpl spyDocTextType;

    @Test
    public void testIsEligible_validTexTypesUrls() throws Exception {
        // given
        final Set<String> expectedSet = new TreeSet(Arrays.asList(new String[]{
                "http://defas.com.ua/java/textForTest.doc",
                "http://defas.com.ua/java/textForTest.docx",
                "http://defas.com.ua/java/textForTest.odp",
                "http://defas.com.ua/java/textForTest.ods",
                "http://defas.com.ua/java/textForTest.odt",
                "http://defas.com.ua/java/textForTest.ppt",
                "http://defas.com.ua/java/textForTest.pptx",
                "http://defas.com.ua/java/textForTest.rtf",
                "http://defas.com.ua/java/textForTest.txt"
        }));

        doReturn(true).when(spyDocTextType).isWebProtocol(anyString());

        // when
        Set<String> actualSet = new TreeSet<>();
        for (String validTextUrl : expectedSet) {
            if (spyDocTextType.isEligible(validTextUrl)) {
                actualSet.add(validTextUrl);
            }
        }

        // then
        verify(spyDocTextType, times(9)).isEligible(anyString());
        verify(spyDocTextType, times(9)).isWebProtocol(anyString());
        Assert.assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testIsEligible_invalidTextTypeOfUrl() {
        // given
        final String dataSourceLink = "http://defas.com.ua/java/textForTest.iso";

        doReturn(true).when(spyDocTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyDocTextType.isEligible(dataSourceLink);

        // then
        verify(spyDocTextType, times(1)).isEligible(anyString());
        verify(spyDocTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_invalidTypeOfUrl() {
        // given
        final String dataSourceLink = "defas.com.ua/java/textForTest.pdf";

        doReturn(false).when(spyDocTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyDocTextType.isEligible(dataSourceLink);

        // then
        verify(spyDocTextType, times(1)).isEligible(anyString());
        verify(spyDocTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String dataSourceLink = " ";

        // when
        spyDocTextType.isEligible(dataSourceLink);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        //given
        final String dataSourceLink = null;

        //when
        spyDocTextType.isEligible(dataSourceLink);

        //then
        // exception thrown
    }
}