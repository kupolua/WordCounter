package info.deepidea.wordcounter.service;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocumentConverterTest {
    @Mock private HtmlTextTypeImpl htmlType;
    @Mock private PdfTextTypeImpl pdfType;
    @Mock private DocTextTypeImpl docType;
    @Mock private PlainTextTypeImpl plainType;
    @Mock private XlsTextTypeImpl xlsType;
    @Mock private XlsxTextTypeImpl xlsxType;
    @Mock private HtmlToStringConverter htmlConverter;
    @Mock private PdfToStringConverter pdfConverter;
    @Mock private DocToStringConverter docConverter;
    @Mock private PlainToStringConverter plainConverter;
    @Mock private XlsToStringConverter xlsConverter;
    @Mock private XlsxToStringConverter xlsxConverter;
    private DocumentConverter converter;

    @Before
    public void setup() {
        converter = new DocumentConverter(htmlConverter, pdfConverter, docConverter,
                                          plainConverter, xlsConverter, xlsxConverter);
    }

    @Test
    public void testGetDocumentConverter_pdf() {
        //given
        final TextType type = pdfType;
        when(pdfConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(pdfConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof PdfToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_doc() {
        //given
        final TextType type = docType;
        when(docConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(docConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof DocToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_html() {
        //given
        final TextType type = htmlType;
        when(htmlConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(htmlConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof HtmlToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_plainText() {
        //given
        final TextType type = plainType;
        when(plainConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(plainConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof PlainToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_xls() {
        //given
        final TextType type = xlsType;
        when(xlsConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(xlsConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof XlsToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_xlsx() {
        //given
        final TextType type = xlsxType;
        when(xlsxConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(xlsxConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof XlsxToStringConverter);
    }

    @Test(expected = RuntimeException.class)
    public void testNonExistingType() {
        // given
        final TextType nonExistingType = dataSourceLink -> true;

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(nonExistingType);

        //then
        //expected exception
    }
}