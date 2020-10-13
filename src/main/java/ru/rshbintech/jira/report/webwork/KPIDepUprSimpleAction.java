package ru.rshbintech.jira.report.webwork;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * класс для отчета KPI по отделу\управлению
 * расскрашиваем строку в желтый цвет с итоговым KPI по отделу (ищем по наличие текста в первой ячейке: среднее расчетное)
 */

public class KPIDepUprSimpleAction{

    public Sheet doReport(ResultSet rs, XSSFWorkbook wb, String sheetName, String procedureName) throws SQLException {

        MainClassForReports mc = new MainClassForReports();
        StylesWorkbook stylesWorkbook = new StylesWorkbook();
        Sheet sheet;
        Row row;

        if (wb.getNumberOfSheets() == 0) {
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.getSheetAt(0);
        }

        int begin = sheet.getLastRowNum() == 0 ? 0 : sheet.getLastRowNum() + 3;
        String[] metaData = mc.getMetaData(rs);

        mc.writeCellSimpleReport(sheet, begin + 1, rs, metaData);

        mc.titleSimpleReports(sheet, begin, metaData, procedureName
                , new StylesWorkbook().initStyle(sheet.getWorkbook(), "cs_Data_String10HCVCC"));

        mc.setColumnWidth(sheet, metaData.length);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            //проверяем на наличие текста среднее расчетное
            if (row.getCell(0) != null) {
                row.getCell(0).getStringCellValue();
                if (row.getCell(0).getStringCellValue().contains("\u0441\u0440\u0435\u0434\u043d\u0435\u0435 \u0440\u0430\u0441\u0447\u0435\u0442\u043d\u043e\u0435")) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        row.getCell(j).setCellStyle(stylesWorkbook.initStyle(wb, "cs_Data_String10HCVCCY"));
                    }
                }
            }
        }

        return sheet;
    }

}
