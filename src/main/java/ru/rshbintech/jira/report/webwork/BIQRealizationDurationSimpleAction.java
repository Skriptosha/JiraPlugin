package ru.rshbintech.jira.report.webwork;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.rshbintech.jira.report.lib.I18n;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BIQRealizationDurationSimpleAction{

    public Sheet doReport(ResultSet rs, XSSFWorkbook wb, String sheetName, String procedureName) throws SQLException {

        MainClassForReports mc = new MainClassForReports();

        Sheet sheet = wb.createSheet(sheetName);

        Cell cell;

        Row row;

        String[] metaDataString = mc.getMetaData(rs);

        XSSFCellStyle xssfCellStyle = new StylesWorkbook().initStyle(wb, "cs_Data_String10BALLHLC");

        sheet = mc.writeCellSimpleReport(sheet, 1, rs, metaDataString);
        int i = sheet.getLastRowNum();
        int c = 0;
        // рисуем последний столбец
        for (int j = 1; j < i + 1; j++) {
            cell = sheet.getRow(j).createCell(metaDataString.length);
            if (!sheet.getRow(j).getCell(3).getStringCellValue().isEmpty()
                    && !sheet.getRow(j).getCell(4).getStringCellValue().isEmpty()) {
                c = j + 1;
                cell.setCellFormula("(E" + c + "-D" + c + ")");
                cell.setCellStyle(xssfCellStyle);
            } else {
                cell.setCellValue("0");
                cell.setCellStyle(xssfCellStyle);
            }
        }

        //промежуточные итоги "длительность (дни)"
        i++;
        row = sheet.createRow(i);
        cell = row.createCell(metaDataString.length);
        cell.setCellStyle(xssfCellStyle);
        cell.setCellFormula("SUBTOTAL(101," + "N2:N" + i + ")");


        mc.titleSimpleReports(sheet, 0, metaDataString, procedureName, new StylesWorkbook().initStyle(sheet.getWorkbook()
                , "cs_Data_String10HCVCC"));

        //шапка для последнего столбца
        cell = sheet.getRow(0).createCell(metaDataString.length);
        cell.setCellStyle(new StylesWorkbook().initStyle(wb,"cs_Data_String10HCVCC"));
        cell.setCellValue(I18n.getText("getBIQRealizationDurationInfo.duration"));

        mc.setColumnWidth(sheet, metaDataString.length);

        return sheet;
    }
}