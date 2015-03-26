package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlainToStringConverterTest {
    private PlainToStringConverter plainToStringConverter;
    private RequestContainer requestContainer;

    @Before
    public void setUp() throws Exception {
        final String input = "В мире есть много интересных занятий.";
        requestContainer = new RequestContainer(input);
        plainToStringConverter = new PlainToStringConverter();
    }

    @Test
    public void testIsEligible_validTextType() {
        // given
        final TextType documentType = new PlainTextTypeImpl();

        // when
        boolean actualResult = plainToStringConverter.isEligible(documentType);

        // then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidTextType() {
        // given
        final TextType documentType = new PdfTextTypeImpl();

        // when
        boolean actualResult = plainToStringConverter.isEligible(documentType);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_nullTextType() {
        // given
        final TextType documentType = null;

        // when
        boolean actualResult = plainToStringConverter.isEligible(documentType);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testConvertToString_valid() {
        // given
        final String inputText = "В мире есть много интересных занятий.";

        // when
        ConvertedDataContainer actualResult = plainToStringConverter.convertToString(requestContainer);

        // then
        Assert.assertEquals(inputText, actualResult.getPlainText());
    }
}