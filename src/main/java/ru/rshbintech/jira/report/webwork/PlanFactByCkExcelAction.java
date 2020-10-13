package ru.rshbintech.jira.report.webwork;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.rshbintech.jira.report.lib.DataTools;
import ru.rshbintech.jira.report.lib.FontFormat;
import ru.rshbintech.jira.report.lib.I18n;
import ru.rshbintech.jira.report.lib.StyleFormat;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JIR-52 "План-факт по ПС"
 */
public class PlanFactByCkExcelAction {

    private XSSFWorkbook wb;
    private StyleFormat style = new StyleFormat();
    private FontFormat font = new FontFormat();
    private XSSFCellStyle сs_Data_String10HCVCC, cs_Data_String10BALLHRC, cs_Data_String10BALLHLC, cs_DoubleBorderAllHCenter;
    private int i = 0;


    public Sheet doReport(ResultSet rs, XSSFWorkbook wb, String sheetName, String procedureName) throws SQLException {

        StylesWorkbook styles = new StylesWorkbook();

        Sheet sheet;
        int i = 0, j, ckCount = 0; // Строка / Столбец / Кол-во ЦК
        Row row = null;
        Cell cell;
        String psNumber = null;

        if (wb.getNumberOfSheets() == 0) {
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.getSheetAt(0);
        }

        // массив заголовков из метадата
        String[] fieldNameList = new MainClassForReports().getMetaData(rs);
        j = 1;
        while (rs.next()) {
            ckCount++;
            i = 3;

            row = sheet.getRow(i) == null ? sheet.createRow(i++) : sheet.getRow(i++);
            cell = row.createCell(j);
            cell.setCellStyle(styles.initStyle(wb, "cs_Data_String10BALLHLC"));
            cell.setCellValue(rs.getString(fieldNameList[0]));

            row = sheet.getRow(i) == null ? sheet.createRow(i++) : sheet.getRow(i++);
            cell = row.createCell(j);
            cell.setCellStyle(styles.initStyle(wb, "cs_DoubleBorderAllHRight"));
            cell.setCellValue(rs.getDouble(fieldNameList[1]));

            row = sheet.getRow(i) == null ? sheet.createRow(i++) : sheet.getRow(i++);
            cell = row.createCell(j);
            cell.setCellStyle(styles.initStyle(wb, "cs_DoubleBorderAllHRight"));
            cell.setCellValue(rs.getDouble(fieldNameList[2]));

            if (psNumber == null)
                psNumber = rs.getString(fieldNameList[3]);

            j++;
        }

        int begin = sheet.getLastRowNum() == 0 ? 0 : sheet.getLastRowNum() + 3;

        // Шапка таблицы
        row = sheet.createRow(1);
        for (int h1 = 0; h1 < ckCount + 2; h1++) {
            cell = row.createCell(h1);
            cell.setCellStyle(styles.initStyle(wb, "cs_Data_String10BALLHLCLightGreen"));
            cell.setCellValue(psNumber != null ? psNumber : "");
        }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, ckCount + 1));

        row = sheet.createRow(2);
        for (int h2 = 0; h2 < ckCount + 2; h2++) {
            cell = row.createCell(h2);
            cell.setCellStyle(styles.initStyle(wb, "cs_Data_String10BALLHLC"));
            cell.setCellValue(I18n.getText("getPlanFactByCk.ckNames"));
        }
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, ckCount + 1));

        if (ckCount > 0) {
            // Остаток по ЦК (разница ПЛАН и ФАКТ - нижняя строка таблицы)
            for (int d = 1; d < ckCount + 1; d++) {
                int balanceRow = 6;
                if (sheet.getRow(balanceRow) != null) {
                    row = sheet.getRow(balanceRow);
                } else {
                    row = sheet.createRow(balanceRow);
                }
                cell = row.createCell(d); // Строка "Остаток"
                cell.setCellStyle(styles.initStyle(wb, "cs_DoubleBorderAllHRight"));
                cell.setCellFormula(DataTools.getColumnFromIndex(d + 1) + "5-" + DataTools.getColumnFromIndex(d + 1) + "6");
            }

            // Первый столбец и Суммы строк ПЛАН, ФАКТ и Остаток
            for (int k = 3; k < 7; k++) {
                if (sheet.getRow(k) != null) {
                    row = sheet.getRow(k);
                } else {
                    row = sheet.createRow(k);
                }
                // Первый столбец (левый бок таблицы)
                cell = row.createCell(0);
                cell.setCellStyle(styles.initStyle(wb, "cs_Data_String10BALLHLC"));
                cell.setCellValue(k == 3
                        ? I18n.getText("getPlanFactByCk.chd")
                        : k == 4
                        ? I18n.getText("getPlanFactByCk.plan")
                        : k == 5 ? I18n.getText("getPlanFactByCk.fact") : I18n.getText("getPlanFactByCk.balance"));

                // Суммы строк ПЛАН, ФАКТ и Остаток (правый бок таблицы)
                cell = row.createCell(ckCount + 1);
                if (k == 3) {
                    cell.setCellStyle(styles.initStyle(wb, "cs_Data_String10BALLHLC"));
                    cell.setCellValue("");
                } else {
                    cell.setCellStyle(styles.initStyle(wb, "cs_DoubleBorderAllHRightBold"));
                    cell.setCellFormula("SUM(" + DataTools.getColumnFromIndex(2) + (k + 1) + ":" + DataTools.getColumnFromIndex(ckCount + 1) + (k + 1) + ")");
                }
            }
        }

        return sheet;
    }
}