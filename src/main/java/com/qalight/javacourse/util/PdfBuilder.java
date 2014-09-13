package com.qalight.javacourse.util;

import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by box on 13.09.2014.
 */
public class PdfBuilder extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get("calculatedWords");

        Table table = new Table(2);
        table.addCell("Word");
        table.addCell("Count");

        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            table.addCell(entry.getKey());
            table.addCell(entry.getValue().toString());
        }

        document.add(table);
    }
}
