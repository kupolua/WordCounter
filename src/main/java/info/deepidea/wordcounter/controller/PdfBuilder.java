package info.deepidea.wordcounter.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static info.deepidea.wordcounter.util.ViewsConstants.*;

public class PdfBuilder extends AbstractPdfView {
    private static final Logger LOG = LoggerFactory.getLogger(PdfBuilder.class);

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest request,
                                    HttpServletResponse response)  {
        try {
            setExportFileName(response);

            addErrorsIntoDocumentIfExists(document, model);

            addResultIntoDocumentIfExist(model, document, request);
        } catch (DocumentException e) {
            final String msg = "An error has occurred while creating a document.";
            LOG.error(msg, e);
            createErrorPdfResponse(document, msg);
        } catch (IOException e) {
            final String msg = "An error has occurred while reading a font.";
            LOG.error(msg, e);
            createErrorPdfResponse(document, msg);
        }
    }

    private void addResultIntoDocumentIfExist(Map<String, Object> model,
                                              Document document,
                                              HttpServletRequest request) throws DocumentException, IOException {
        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get(MODEL_NAME);
        if (!calculatedWords.isEmpty()) {
            PdfPTable table = getPdfPTable();
            PdfPCell cell = getPdfPCell();
            setHeadCells(table, cell, request);
            setResultCells(table, cell, calculatedWords);
            document.add(table);
        }
    }

    private void setExportFileName(HttpServletResponse response) {
        response.setHeader(RESPONSE_HEADER_NAME, HEADER_VALUE_PDF);
    }

    private PdfPTable getPdfPTable() throws DocumentException {
        PdfPTable table = new PdfPTable(COLUMNS);
        table.setWidthPercentage(WIDTH_PERCENTAGE);
        table.setWidths(new float[] {WIDTH_TABLE_ONE, WIDTH_TABLE_TWO});
        table.setSpacingBefore(SPACING_BEFORE_TABLE);
        return table;
    }

    private PdfPCell getPdfPCell() {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(PADDING);
        return cell;
    }

    private void setHeadCells(PdfPTable table,
                              PdfPCell cell,
                              HttpServletRequest request) throws IOException, DocumentException {
        final String userBrowserLocale = request.getHeader(REQUEST_HEADER_NAME);
        Font font = getArialBoldItalicFont();
        String wordsCell = HEAD_CELL_WORDS_EN;
        String countCell = HEAD_CELL_COUNT_EN;
        if (userBrowserLocale.startsWith(LOCALE_RU)){
            wordsCell = HEAD_CELL_WORDS_RU;
            countCell = HEAD_CELL_COUNT_RU;
        }
        if (userBrowserLocale.startsWith(LOCALE_UKR)){
            wordsCell = HEAD_CELL_WORDS_UKR;
            countCell = HEAD_CELL_COUNT_UKR;
        }
        cell.setPhrase(new Phrase(wordsCell, font));
        table.addCell(cell);
        cell.setPhrase(new Phrase(countCell, font));
        table.addCell(cell);
    }

    private Font getArialBoldItalicFont() throws IOException, DocumentException {
        BaseFont unicodeArialBold = BaseFont.createFont(FONT_ARIAL_BOLD_ITALIC, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(unicodeArialBold);
    }

    private void setResultCells(PdfPTable table, PdfPCell cell, Map<String, Integer> calculatedWords) throws IOException, DocumentException {
        Font font = getArialNormalFont();
        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            cell.setPhrase(new Phrase(entry.getKey(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(entry.getValue()), font));
            table.addCell(cell);
        }
    }

    private Font getArialNormalFont() throws IOException, DocumentException {
        BaseFont unicodeArial = BaseFont.createFont(FONT_ARIAL_NORMAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(unicodeArial);
    }

    private void addErrorsIntoDocumentIfExists(Document document, Map model) throws DocumentException, IOException {
        List<String> errorList = (List<String>) model.get("errorList");
        if (!errorList.isEmpty()){
            Font font = getArialNormalFont();
            setRedColorForFont(font);
            setFontSize(font);
            document.add(new Paragraph(ERROR_WORD, font));
            for(String eachError : errorList){
                document.add(new Paragraph(DASH + eachError, font));
            }
        }
    }

    private void setRedColorForFont(Font font) {
        font.setColor(255, 0, 0);
    }

    private void setFontSize(Font font) {
        font.setSize(8.0f);
    }

    private void createErrorPdfResponse(Document document, String msg) {
        try {
            Font font = new Font();
            setRedColorForFont(font);
            document.add(new Paragraph(ERROR_WORD, font));
            document.add(new Paragraph(msg, font));
        } catch (DocumentException e) {
            LOG.error("An error has occurred while creating a document.", e);
        }
    }
}
