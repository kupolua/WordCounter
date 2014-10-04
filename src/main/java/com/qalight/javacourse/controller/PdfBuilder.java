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
    private static final String HEAD_CELL_WORDS = "Words";
    private static final String HEAD_CELL_COUNT = "Count";
    private static final String MODEL_NAME = "calculatedWords";

    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

        cell.setPhrase(new Phrase(HEAD_CELL_WORDS, headFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase(HEAD_CELL_COUNT, headFont));
        table.addCell(cell);

        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get(MODEL_NAME);
        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            if (entry.getKey().startsWith("<a href=")){
                Chunk link = deleteTagAndgetChunk(entry);
                cell.setPhrase(new Phrase(link));
                table.addCell(cell);
            } else {
                cell.setPhrase(new Phrase(entry.getKey(), bodyFont));
                table.addCell(cell);
            }
            cell.setPhrase(new Phrase(entry.getValue().toString(), bodyFont));
            table.addCell(cell);
        }

        document.add(table);
    }

    private Chunk deleteTagAndgetChunk(Map.Entry<String, Integer> entry) {
        String unTaggedLink = Jsoup.clean(entry.getKey(), Whitelist.simpleText());
        Chunk link = new Chunk(unTaggedLink);
        link.setAnchor(unTaggedLink);
        return link;
    }
}
