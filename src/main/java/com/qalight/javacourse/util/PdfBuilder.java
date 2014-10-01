package com.qalight.javacourse.util;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PdfBuilder extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {2.0f, 1.0f});
        table.setSpacingBefore(10);

        BaseFont unicodeArialBold = BaseFont.createFont("fonts/arialbi.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font headFont = new Font(unicodeArialBold);
        BaseFont unicodeArial = BaseFont.createFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font bodyFont = new Font(unicodeArial);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Words", headFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Count", headFont));
        table.addCell(cell);

        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get("calculatedWords");
        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            cell.setPhrase(new Phrase(entry.getKey(), bodyFont));
            table.addCell(cell);
            cell.setPhrase(new Phrase(entry.getValue().toString(), bodyFont));
            table.addCell(cell);
        }

        document.add(table);
    }
}
