package com.qalight.javacourse.util;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;

public class PdfBuilder extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get("calculatedWords");

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {2.0f, 1.0f});
        table.setSpacingBefore(10);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(255, 255, 255);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Words", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Count", font));
        table.addCell(cell);

        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            table.addCell(entry.getKey());
            table.addCell(entry.getValue().toString());
        }

        document.add(table);
    }
}
