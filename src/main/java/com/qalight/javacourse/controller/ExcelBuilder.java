package com.qalight.javacourse.controller;

import static com.qalight.javacourse.util.ViewsConstants.*;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelBuilder extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        setExportFileName(response);

        createErrorSheetIfErrorExist(model, workbook);

        createResultSheetIfResultExist(model, workbook, request);
    }

    private void setExportFileName(HttpServletResponse response) {
        response.setHeader(RESPONSE_HEADER_NAME, HEADER_VALUE_XLS);
    }

    private void createErrorSheetIfErrorExist(Map<String, Object> model, HSSFWorkbook workbook){
        List<String> errorList = (List<String>) model.get("errorList");
        if(!errorList.isEmpty()) {
            HSSFSheet errorSheet = getErrorSheet(workbook);
            CellStyle style = getErrorStyle(workbook);

            addErrorWord(errorSheet, style);

            int rowNum = 1;
            for(String eachError : errorList) {
                HSSFRow row = errorSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(DASH + eachError);
                row.getCell(0).setCellStyle(style);
            }
        }
    }

    private void createResultSheetIfResultExist(Map<String, Object> model,
                                                HSSFWorkbook workbook,
                                                HttpServletRequest request) {
        Map<String,Integer> calculatedWords = (Map<String,Integer>) model.get(MODEL_NAME);
        if(!calculatedWords.isEmpty()) {
            HSSFSheet wordsSheet = getWordsSheet(workbook);
            setHeadCells(request, wordsSheet, workbook);
            setResultCells(calculatedWords, wordsSheet, workbook);
        }
    }

    private HSSFSheet getErrorSheet(HSSFWorkbook workbook) {
        final String errorSheetName = "Error(s)";
        HSSFSheet wordsSheet = workbook.createSheet(errorSheetName);
        wordsSheet.autoSizeColumn(0, true);
        return wordsSheet;
    }

    private CellStyle getErrorStyle(HSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font errorFont = workbook.createFont();
        errorFont.setColor(Font.COLOR_RED);
        style.setFont(errorFont);
        return style;
    }

    private void addErrorWord(HSSFSheet errorSheet, CellStyle style) {
        HSSFRow errorWord = errorSheet.createRow(0);
        errorWord.createCell(0).setCellValue(ERROR_WORD);
        errorWord.getCell(0).setCellStyle(style);
    }

    private HSSFSheet getWordsSheet(HSSFWorkbook workbook) {
        HSSFSheet wordsSheet = workbook.createSheet(WORDS_SHEET_NAME);
        wordsSheet.setDefaultColumnWidth(COLUMN_WIDTH);
        return wordsSheet;
    }

    private void setHeadCells(HttpServletRequest request,
                              HSSFSheet excelSheet,
                              HSSFWorkbook workbook) {
        final String userBrowserLocale = request.getHeader(REQUEST_HEADER_NAME);
        CellStyle style = getFontStyle(workbook);
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
        HSSFRow row = excelSheet.createRow(0);
        row.createCell(0).setCellValue(wordsCell);
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue(countCell);
        row.getCell(1).setCellStyle(style);
    }

    private CellStyle getFontStyle(HSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }

    private void setResultCells(Map<String, Integer> calculatedWords,
                                HSSFSheet wordsSheet,
                                HSSFWorkbook workbook){
        CellStyle style = getAlignmentStyle(workbook);
        int rowNum = 1;
        for (Map.Entry<String, Integer> entry : calculatedWords.entrySet()) {
            HSSFRow row = wordsSheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(entry.getValue());
            row.getCell(1).setCellStyle(style);
        }
    }

    private CellStyle getAlignmentStyle(HSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        return style;
    }
}
