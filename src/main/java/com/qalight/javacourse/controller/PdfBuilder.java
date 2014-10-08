package com.qalight.javacourse.controller;

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
    private static final String FONT_ARIAL_BOLD_ITALIC = "fonts/arialbi.ttf";
    private static final String FONT_ARIAL_NORMAL = "fonts/arial.ttf";
    private static final String HEAD_CELL_WORDS_EN = "Words";
    private static final String HEAD_CELL_COUNT_EN = "Count";
    private static final String HEAD_CELL_WORDS_RU = "Слово";
    private static final String HEAD_CELL_COUNT_RU = "Количество";
    private static final String HEAD_CELL_WORDS_UKR = "Слово";
    private static final String HEAD_CELL_COUNT_UKR = "Кiлькiсть";
    private static final String MODEL_NAME = "calculatedWords";
    private static final String A_HREF_TAG = "<a href=";
    private static final String FILE_NAME = "calculatedWords.pdf";

    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        setExportFileName(response);

        //todo: delete hardcode
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {2.0f, 1.0f});
        table.setSpacingBefore(10);

        BaseFont unicodeArialBold = BaseFont.createFont(FONT_ARIAL_BOLD_ITALIC, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font headFont = new Font(unicodeArialBold);
        BaseFont unicodeArial = BaseFont.createFont(FONT_ARIAL_NORMAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font bodyFont = new Font(unicodeArial);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        setHeadCells(table, headFont, cell, request);

        setResultCells(model, table, bodyFont, cell);

        document.add(table);
    }

    private void setExportFileName(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + FILE_NAME + "\"");
    }

    private void setHeadCells(PdfPTable table, Font headFont, PdfPCell cell, HttpServletRequest request) {
        final String USER_BROWSER_LOCALE = request.getHeader("Accept-Language");
        String wordsCell = HEAD_CELL_WORDS_EN;
        String countCell = HEAD_CELL_COUNT_EN;
        if (USER_BROWSER_LOCALE.startsWith("ru")){
            wordsCell = HEAD_CELL_WORDS_RU;
            countCell = HEAD_CELL_COUNT_RU;
        } else if (USER_BROWSER_LOCALE.startsWith("uk")){
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
                Chunk link = deleteTagAndGetChunk(entry);
                cell.setPhrase(new Phrase(link));
                table.addCell(cell);
            } else {
                cell.setPhrase(new Phrase(entry.getKey(), bodyFont));
                table.addCell(cell);
            }
            cell.setPhrase(new Phrase(entry.getValue().toString(), bodyFont));
            table.addCell(cell);
        }
    }

    private Chunk deleteTagAndGetChunk(Map.Entry<String, Integer> entry) {
        String unTaggedLink = Jsoup.clean(entry.getKey(), Whitelist.simpleText());
        Chunk link = new Chunk(unTaggedLink);
        link.setAnchor(unTaggedLink);
        return link;
    }
}
