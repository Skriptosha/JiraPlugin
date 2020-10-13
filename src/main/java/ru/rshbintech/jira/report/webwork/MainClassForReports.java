package ru.rshbintech.jira.report.webwork;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import ru.rshbintech.jira.report.lib.I18n;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

/**
 * Основной класс для автоматического заполнения ячеек в файле эксель.
 */

public class MainClassForReports {

    private StylesWorkbook sw = new StylesWorkbook();
    private int count = 0;

    /**
     * Получение списка столбцов таблицы с их назаниями
     *
     * @param rs ResultSet
     * @return массив названий столбцов из БД типа String
     * @throws SQLException
     */
    public String[] getMetaData(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        String[] metaDataString = new String[metaData.getColumnCount()];

        for (int metadataPos = 0; metadataPos < metaData.getColumnCount(); metadataPos++) {
            metaDataString[metadataPos] = metaData.getColumnName(metadataPos + 1);
        }
        return metaDataString;
    }

    /**
     * Формирование шапки для таблицы. Используется для отчетов simpleReports.
     *
     * @param sheet          Лист файла эксель для заполнения
     * @param rowNum         Номер строки, которую необходимо заполнить
     * @param metaDataString Названия столбцов таблицы из БД для заполнения
     * @param procedureName  наименования хранимой процедуры (функции) отчета (без пакета jira64.dbo)
     * @param xssfCellStyle  стиль, который необходимо применить к ячейкам
     */
    public void titleSimpleReports(Sheet sheet, int rowNum, String[] metaDataString, String procedureName, XSSFCellStyle xssfCellStyle) {
        int cellPos = 0;
        Cell cell;
        Row row = sheet.createRow(rowNum);

        for (int i = 0; i < metaDataString.length; i++) {
            cell = row.createCell(cellPos++);
            cell.setCellStyle(xssfCellStyle);
            cell.setCellValue(I18n.getText(procedureName + "." + metaDataString[i]));
        }
        if (count == 0) sheet.createFreezePane(rowNum, 1);
        count++;
    }

    /**
     * Заполнение самих ячеек для таблицы. Используется для отчетов simpleReports.
     *
     * @param sheet          Лист файла эксель для заполнения
     * @param rowNum         Номер строки, с которой необходимо начать заполнение
     * @param rs             ResultSet
     * @param metaDataString Названия столбцов таблицы из БД для заполнения
     * @return Заполненный лист эксель
     * @throws SQLException
     */
    public Sheet writeCellSimpleReport(Sheet sheet, int rowNum, ResultSet rs, String[] metaDataString) throws SQLException {
        Row row;
        int cellNumber;
        Object object;

        while (rs.next()) {
            cellNumber = 0;
            row = sheet.createRow(rowNum++);
            for (int dataPos = 0; dataPos < metaDataString.length; dataPos++) {
                Cell cell = row.createCell(cellNumber++);
                object = rs.getObject(dataPos);

                if (object != null) {
                    switch (object.getClass().getSimpleName()) {
                        case ("String"):
                        case ("ClobImpl"):
                            cell.setCellValue((String) object);
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_Data_String10BALLHLC"));
                            break;
                        case ("BigDecimal"):
                            cell.setCellValue(((BigDecimal) object).doubleValue());
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_DoubleBorderAllHCenter"));
                            break;
                        case ("Integer"):
                            cell.setCellValue(((Integer) object).doubleValue());
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_IntegerBorderAllHCenter"));
                            break;
                        case ("Date"):
                            cell.setCellValue((Date) object);
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_Data_Date10BAllHCVC"));
                            break;
                        default:
                            cell.setCellValue(object.getClass().getSimpleName() + ";" + metaDataString[dataPos]);
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_Data_String10BALLHLC"));
                            break;
                    }
                } else cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_Data_String10BALLHLC"));
            }
        }
        return sheet;
    }

    // Параметры для методов setColumnWidth
    private int mod = 256;
    private int max = 58;

    /**
     * Ручная установка размера всех столбцов
     *
     * @param sheet             Лист файла эксель для заполнения
     * @param numberOfColumn    количество столбцов в таблице
     * @param columnWidthManual необходимая ширина столбца
     */
    public void setColumnWidth(Sheet sheet, int numberOfColumn, int columnWidthManual) {
        int temp = columnWidthManual > max ? max * mod : columnWidthManual * mod;
        for (int i = 0; i < numberOfColumn; i++) {
            sheet.setColumnWidth(i, temp);
        }
    }

    /**
     * Автоматическая установка размера всех столбцов
     *
     * @param sheet          Лист файла эксель для заполнения
     * @param numberOfColumn количество столбцов в таблице
     */
    public void setColumnWidth(Sheet sheet, int numberOfColumn) {
        for (int i = 0; i < numberOfColumn; i++) {
            sheet.autoSizeColumn(i);
            if (sheet.getColumnWidth(i) > max * mod) {
                sheet.setColumnWidth(i, max * mod);
            }
        }
    }
}