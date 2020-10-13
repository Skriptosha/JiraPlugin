package ru.rshbintech.jira.report.webwork;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BIQ-4711 Отчет по дефектам (JIR-55) (Временное решение по отчету "План доработк ИС новый")
 */

public class RepDefectSimpleAction {

    public Sheet doReport(ResultSet rs, XSSFWorkbook wb, String sheetName, String procedureName) throws SQLException {
        Sheet sheet;

        sheet = wb.createSheet(sheetName);

        int begin = 0;

        MainClassForReports mainClassForReports = new MainClassForReports();

        String[] metaData = mainClassForReports.getMetaData(rs);

        mainClassForReports.writeCellSimpleReport(sheet, begin + 1, rs, metaData);

        mainClassForReports.titleSimpleReports(sheet, begin, metaData, procedureName
                , new StylesWorkbook().initStyle(sheet.getWorkbook(), "cs_Data_String10HCVCC"));

        if (procedureName.contains("reportByDefectStatusPriority")) mainClassForReports.setColumnWidth(sheet, metaData.length, 20);
        else if (procedureName.contains("getBiqStatusReportData_new")) mainClassForReports.setColumnWidth(sheet, metaData.length, 20);
        else mainClassForReports.setColumnWidth(sheet, metaData.length, 0);
        return sheet;
    }
}