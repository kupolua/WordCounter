package com.qalight.javacourse.controller;

import static com.qalight.javacourse.util.ViewsConstants.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PdfBuilder extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        setExportFileName(response);

        PdfPTable table = new PdfPTable(COLUMNS);
        table.setWidthPercentage(WIDTH_PERCENTAGE);
        table.setWidths(new float[] {WIDTH_TABLE_ONE, WIDTH_TABLE_TWO});
        table.setSpacingBefore(SPACING_BEFORE_TABLE);

        BaseFont unicodeArialBold = BaseFont.createFont(FONT_ARIAL_BOLD_ITALIC, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font headFont = new Font(unicodeArialBold);
        BaseFont unicodeArial = BaseFont.createFont(FONT_ARIAL_NORMAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font bodyFont = new Font(unicodeArial);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(PADDING);

        setHeadCells(table, headFont, cell, request);

        setResultCells(model, table, bodyFont, cell);

        document.add(table);
    }

    private void setExportFileName(HttpServletResponse response) {
        response.setHeader(RESPONSE_HEADER_NAME, HEADER_VALUE_PDF);
    }

    private void setHeadCells(PdfPTable table, Font headFont, PdfPCell cell, HttpServletRequest request) {
        final String USER_BROWSER_LOCALE = request.getHeader(REQUEST_HEADER_NAME);
        String wordsCell = HEAD_CELL_WORDS_EN;
        String countCell = HEAD_CELL_COUNT_EN;
        if (USER_BROWSER_LOCALE.startsWith(LOCALE_RU)){
            wordsCell = HEAD_CELL_WORDS_RU;
            countCell = HEAD_CELL_COUNT_RU;
        }
        if (USER_BROWSER_LOCALE.startsWith(LOCALE_UKR)){
            wordsCell = HEAD_CELL_WORDS_UKR;
            countCell = HEAD_CELL_COUNT_UKR;
        }
        cell.setPhrase(new Phrase(wordsCell, headFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase(countCell, headFont));
        table.addCell(cell);
    }

    private void setResultCells(Map model, PdfPTable table, Font bodyFont, PdfPCell cell) {
        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get(MODEL_NAME);
        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            if (entry.getKey().startsWith(A_HREF_TAG)){
                Chunk link = getChunk(entry);
                cell.setPhrase(new Phrase(link));
                table.addCell(cell);
            } else {
                cell.setPhrase(new Phrase(entry.getKey(), bodyFont));
                table.addCell(cell);
            }
            cell.setPhrase(new Phrase(String.valueOf(entry.getValue()), bodyFont));
            table.addCell(cell);
        }
    }

    private Chunk getChunk(Map.Entry<String, Integer> entry) {
        String unTaggedLink = deleteTag(entry);
        Chunk link = new Chunk(unTaggedLink);
        link.setAnchor(unTaggedLink);
        return link;
    }

    private String deleteTag(Map.Entry<String, Integer> entry) {
        return Jsoup.clean(entry.getKey(), Whitelist.simpleText());
    }
}
