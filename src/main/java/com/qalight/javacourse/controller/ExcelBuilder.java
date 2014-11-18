package com.qalight.javacourse.controller;

import static com.qalight.javacourse.util.ViewsConstants.*;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelBuilder extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        setExportFileName(response);

        HSSFSheet excelSheet = workbook.createSheet(SHEET_NAME);
        excelSheet.setDefaultColumnWidth(COLUMN_WIDTH);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);

        setExcelHeader(excelSheet, request, style);

        setExcelRows(model, excelSheet, workbook);
    }

    private void setExportFileName(HttpServletResponse response) {
        response.setHeader(RESPONSE_HEADER_NAME, HEADER_VALUE_XLS);
    }

    private void setExcelHeader(HSSFSheet excelSheet, HttpServletRequest request, CellStyle style) {
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
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue(wordsCell);
        excelHeader.getCell(0).setCellStyle(style);
        excelHeader.createCell(1).setCellValue(countCell);
        excelHeader.getCell(1).setCellStyle(style);
    }

    private void setExcelRows(Map model, HSSFSheet excelSheet, HSSFWorkbook workbook){
        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get(MODEL_NAME);
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        int record = 1;
        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            if (entry.getKey().startsWith(A_HREF_TAG)){
                String unTaggedLink = deleteTag(entry);
                excelRow.createCell(0).setCellValue(unTaggedLink);
                excelRow.getCell(0).setCellStyle(style);
            } else {
                excelRow.createCell(0).setCellValue(entry.getKey());
                excelRow.getCell(0).setCellStyle(style);
            }
            excelRow.createCell(1).setCellValue(entry.getValue());
            excelRow.getCell(1).setCellStyle(style);
        }
    }

    private String deleteTag(Map.Entry<String, Integer> entry) {
        return Jsoup.clean(entry.getKey(), Whitelist.simpleText());
    }
}
