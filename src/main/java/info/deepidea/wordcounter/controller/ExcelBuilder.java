package info.deepidea.wordcounter.controller;

import static info.deepidea.wordcounter.util.ViewsConstants.*;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.Cookie;
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
        setCookie(response);
        setExportFileName(response);

        createErrorSheetIfErrorExist(model, workbook);
        createResultSheetIfResultExist(model, workbook, request);
        createStatisticsSheetIfResultExist(model, workbook, request);
    }

    private void setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(FILE_DOWNLOAD_COOKIE_NAME, FILE_DOWNLOAD_COOKIE_VALUE);
        cookie.setPath(FILE_DOWNLOAD_COOKIE_PATH);
        response.addCookie(cookie);
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

    private void createStatisticsSheetIfResultExist(Map<String, Object> model,
                                                    HSSFWorkbook workbook,
                                                    HttpServletRequest request) {
        final String statisticsObjectName = "statistics";
        Map<String,Integer> statistics = (Map<String,Integer>) model.get(statisticsObjectName);
        if(!statistics.isEmpty()) {
            HSSFSheet statisticsSheet = getStatisticsSheet(workbook);
            setStatisticsCells(statistics, statisticsSheet, workbook, request);
        }
    }

    private HSSFSheet getErrorSheet(HSSFWorkbook workbook) {
        final String errorSheetName = "Error(s)";
        HSSFSheet wordsSheet = workbook.createSheet(errorSheetName);
        wordsSheet.autoSizeColumn(0, true);
        return wordsSheet;
    }

    private HSSFSheet getStatisticsSheet(HSSFWorkbook workbook) {
        final String statisticsSheetName = "Statistics";
        HSSFSheet wordsSheet = workbook.createSheet(statisticsSheetName);
        wordsSheet.autoSizeColumn(0, true);
        wordsSheet.setColumnWidth(0, 8000);
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

    private void setStatisticsCells(Map<String, Integer> statistics,
                                    HSSFSheet wordsSheet,
                                    HSSFWorkbook workbook,
                                    HttpServletRequest request){
        CellStyle style = getAlignmentStyle(workbook);
        int rowNum = 1;
        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            HSSFRow row = wordsSheet.createRow(rowNum++);
            row.createCell(0).setCellValue(localizeStatName(entry.getKey(), request));
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(entry.getValue());
            row.getCell(1).setCellStyle(style);
        }
    }

    private String localizeStatName(String statName, HttpServletRequest request) {
        final String statisticCharactersWithoutSpaces = "statisticCharactersWithoutSpaces";
        final String statisticUniqueWords = "statisticUniqueWords";
        final String statisticTotalCharacters = "statisticTotalCharacters";
        final String statisticTotalWords = "statisticTotalWords";

        final String userBrowserLocale = request.getHeader(REQUEST_HEADER_NAME);
        String correctStatName = statName;

        if (userBrowserLocale.startsWith(LOCALE_RU)) {
            switch (statName) {
                case statisticCharactersWithoutSpaces:
                    correctStatName = STAT_CHAR_WITHOUT_SPACES_RU;
                    break;
                case statisticUniqueWords:
                    correctStatName = STAT_UNIQUE_RU;
                    break;
                case statisticTotalCharacters:
                    correctStatName = STAT_TOTAL_CHARS_RU;
                    break;
                case statisticTotalWords:
                    correctStatName = STAT_TOTAL_WORDS_RU;
                    break;
            }
        } else if (userBrowserLocale.startsWith(LOCALE_UKR)) {
            switch (statName) {
                case statisticCharactersWithoutSpaces:
                    correctStatName = STAT_CHAR_WITHOUT_SPACES_UK;
                    break;
                case statisticUniqueWords:
                    correctStatName = STAT_UNIQUE_UK;
                    break;
                case statisticTotalCharacters:
                    correctStatName = STAT_TOTAL_CHARS_UK;
                    break;
                case statisticTotalWords:
                    correctStatName = STAT_TOTAL_WORDS_UK;
                    break;
            }
        } else {
            switch (statName) {
                                                                                                                                                                                                            case statisticCharactersWithoutSpaces:
                    correctStatName = STAT_CHAR_WITHOUT_SPACES_EN;
                    break;
                case statisticUniqueWords:
                    correctStatName = STAT_UNIQUE_EN;
                    break;
                case statisticTotalCharacters:
                    correctStatName = STAT_TOTAL_CHARS_EN;
                    break;
                case statisticTotalWords:
                    correctStatName = STAT_TOTAL_WORDS_EN;
                    break;
            }
        }
        return correctStatName;
    }

    private CellStyle getAlignmentStyle(HSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        return style;
    }
}
