package info.deepidea.wordcounter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class DocumentConverterIntegrationTest {
    @Autowired
    private DocumentConverter converter;

    @Test
    public void testGetDocumentConverter_html() throws Exception {
        //given
        final TextType htmlType = new HtmlTextTypeImpl();
        final DocumentToStringConverter expectedConverter = new HtmlToStringConverter();

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(htmlType);

        //then
        assertEquals(expectedConverter.getClass(), actual.getClass());
    }

    @Test
    public void testGetDocumentConverter_pdf() throws Exception {
        //given
        final TextType pdfType = new PdfTextTypeImpl();
        final DocumentToStringConverter expectedConverter = new PdfToStringConverter();

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(pdfType);

        //then
        assertEquals(expectedConverter.getClass(), actual.getClass());
    }

    @Test
    public void testGetDocumentConverter_doc() throws Exception {
        //given
        final TextType docType = new DocTextTypeImpl();
        final DocumentToStringConverter expectedConverter = new DocToStringConverter();

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(docType);

        //then
        assertEquals(expectedConverter.getClass(), actual.getClass());
    }

    @Test
    public void testGetDocumentConverter_plain() throws Exception {
        //given
        final TextType plainType = new PlainTextTypeImpl();
        final DocumentToStringConverter expectedConverter = new PlainToStringConverter();

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(plainType);

        //then
        assertEquals(expectedConverter.getClass(), actual.getClass());
    }

    @Test
    public void testGetDocumentConverter_xls() throws Exception {
        //given
        final TextType xlsType = new XlsTextTypeImpl();
        final DocumentToStringConverter expectedConverter = new XlsToStringConverter();

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(xlsType);

        //then
        assertEquals(expectedConverter.getClass(), actual.getClass());
    }
    @Test
    public void testGetDocumentConverter_xlsx() throws Exception {
        //given
        final TextType xlsxType = new XlsxTextTypeImpl();
        final DocumentToStringConverter expectedConverter = new XlsxToStringConverter();

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(xlsxType);

        //then
        assertEquals(expectedConverter.getClass(), actual.getClass());
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