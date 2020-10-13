package ru.rshbintech.jira.report.webwork;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import ru.rshbintech.jira.report.lib.I18n;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class Temp {

    private StylesWorkbook sw = new StylesWorkbook();

    /**
     * Формирование шапки для таблицы
     *
     * @param sheet - страница
     */
    public void title(Sheet sheet, Object object, int rowNum, XSSFCellStyle xssfCellStyle) {
        int cellPos = 0;
        Cell cell;
        Row row = sheet.createRow(rowNum);
        String className = object.getClass().getSimpleName();
        Field[] fields = object.getClass().getDeclaredFields();
        for (int fieldPos = 0; fieldPos < fields.length; fieldPos++) {
            cell = row.createCell(cellPos++);
            cell.setCellStyle(xssfCellStyle);
            cell.setCellValue(I18n.getText(replace(className) + "." + fields[fieldPos].getName()));
        }
        sheet.createFreezePane(rowNum, 1);
    }

    private String replace(String name) {
        return name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toLowerCase());
    }

    /**
     * Заполнение ячеек из коллекции обьектов
     *
     * @param sheet                 Лист
     * @param objects               коллекция обьектов
     * @param xssfCellStyleForTitle стиль для оформления шапки таблицы
     */
    public void writeCell(Sheet sheet, List<?> objects, XSSFCellStyle xssfCellStyleForTitle) {

        Row row;
        Field[] fields;
        int cellNumber;
        int rowNumber = 1;
        Object object;

        sw.initStyle(sheet.getWorkbook(), "cs_Data_String10HCVCC");
        fields = objects.get(0).getClass().getDeclaredFields();

        for (int objPos = 0; objPos < objects.size(); objPos++) {
            cellNumber = 0;
            row = sheet.createRow(rowNumber++);
            for (int fieldPos = 0; fieldPos < fields.length; fieldPos++) {
                Cell cell = row.createCell(cellNumber++);
                try {
                    Class<?> fieldsType = fields[fieldPos].getType();
                    object = objects.get(objPos);
                    fields[fieldPos].setAccessible(true);
                    switch (fieldsType.getSimpleName()) {
                        case ("String"):
                            String stringValue = (String) fields[fieldPos].get(object);
                            cell.setCellValue(stringValue);
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_Data_String10BALLHLC"));
                            break;
                        case ("Double"):
                            Double doubleValue = (Double) fields[fieldPos].get(object);
                            cell.setCellValue(doubleValue);
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_DoubleBorderAllHCenter"));
                            break;
                        case ("Date"):
                            Date date = (Date) fields[fieldPos].get(object);
                            cell.setCellValue((date));
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_Data_Date10BAllHCVC"));
                            break;
                        case ("Boolean"):
                            cell.setCellValue((Boolean) fields[fieldPos].get(object));
                            cell.setCellStyle(sw.initStyle(sheet.getWorkbook(), "cs_Data_String10BALLHLC"));
                            break;
                    }
                    fields[fieldPos].setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
//        setColumnWidth(sheet, fields.length, 0);
        title(sheet, objects.get(0), 0, xssfCellStyleForTitle);
    }
}
