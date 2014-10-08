package com.qalight.javacourse.controller;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelBuilder extends AbstractExcelView {
    private static final String HEAD_CELL_WORDS_EN = "Words";
    private static final String HEAD_CELL_COUNT_EN = "Count";
    private static final String HEAD_CELL_WORDS_RU = "Слово";
    private static final String HEAD_CELL_COUNT_RU = "Количество";
    private static final String HEAD_CELL_WORDS_UKR = "Слово";
    private static final String HEAD_CELL_COUNT_UKR = "Кiлькiсть";
    private static final String MODEL_NAME = "calculatedWords";
    private static final String A_HREF_TAG = "<a href=";
    private static final String FILE_NAME = "calculatedWords.xls";
    private static final String SHEET_NAME = "Calculated Words";

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        setExportFileName(response);

        //todo: add link support?
        //todo: delete hardcode
        HSSFSheet excelSheet = workbook.createSheet(SHEET_NAME);
        excelSheet.setDefaultColumnWidth(20);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        setExcelHeader(excelSheet, request, style);

        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get(MODEL_NAME);
        setExcelRows(excelSheet, calculatedWords, workbook);
    }

    private void setExportFileName(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + FILE_NAME + "\"");
    }

    private void setExcelHeader(HSSFSheet excelSheet, HttpServletRequest request, CellStyle style) {
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
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue(wordsCell);
        excelHeader.getCell(0).setCellStyle(style);
        excelHeader.createCell(1).setCellValue(countCell);
        excelHeader.getCell(1).setCellStyle(style);
    }

    private void setExcelRows(HSSFSheet excelSheet, Map<String, Integer> calculatedWords, HSSFWorkbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        int record = 1;
        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            if (entry.getKey().startsWith(A_HREF_TAG)){
                String unTaggedLink = Jsoup.clean(entry.getKey(), Whitelist.simpleText());
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
}
