package com.qalight.javacourse.controller;

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

import static com.qalight.javacourse.util.ViewsConstants.*;

//todo: refactor exception handling
public class PdfBuilder extends AbstractPdfView {
    private static final Logger LOG = LoggerFactory.getLogger(PdfBuilder.class);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        setExportFileName(response);

        PdfPTable table = new PdfPTable(COLUMNS);
        table.setWidthPercentage(WIDTH_PERCENTAGE);
        table.setWidths(new float[] {WIDTH_TABLE_ONE, WIDTH_TABLE_TWO});
        table.setSpacingBefore(SPACING_BEFORE_TABLE);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(PADDING);

        setHeadCells(table, cell, request);

        setResultCells(model, table, cell);

        addErrorsIntoDocumentIfExists(document, model);

        document.add(table);
    }

    private void setExportFileName(HttpServletResponse response) {
        response.setHeader(RESPONSE_HEADER_NAME, HEADER_VALUE_PDF);
    }

    private void setHeadCells(PdfPTable table, PdfPCell cell, HttpServletRequest request) {
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

    private void setResultCells(Map model, PdfPTable table, PdfPCell cell) {
        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get(MODEL_NAME);
        Font font = getArialNormalFont();
        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            cell.setPhrase(new Phrase(entry.getKey(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(entry.getValue()), font));
            table.addCell(cell);
        }
    }

    private void addErrorsIntoDocumentIfExists(Document document, Map model) throws DocumentException {
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

    private Font getArialBoldItalicFont() {
        BaseFont unicodeArialBold = null;
        try {
            unicodeArialBold = BaseFont.createFont(FONT_ARIAL_BOLD_ITALIC, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            LOG.error("An error has occurred while creating a font.", e);
        } catch (IOException e) {
            final String msg = String.format("An error has occurred while reading a font from %s", e);
            LOG.error(msg);
        }
        return new Font(unicodeArialBold);
    }

    private Font getArialNormalFont() {
        BaseFont unicodeArial = null;
        try {
            unicodeArial = BaseFont.createFont(FONT_ARIAL_NORMAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            LOG.error("An error has occurred while creating a font.", e);
        } catch (IOException e) {
            final String msg = String.format("An error has occurred while reading a font from %s", e);
            LOG.error(msg);
        }
        return new Font(unicodeArial);
    }

    private void setRedColorForFont(Font font) {
        font.setColor(255, 0, 0);
    }

    private void setFontSize(Font font) {
        font.setSize(8.0f);
    }
}
