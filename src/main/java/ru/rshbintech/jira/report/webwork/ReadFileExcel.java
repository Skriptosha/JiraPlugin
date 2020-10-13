package ru.rshbintech.jira.report.webwork;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.rshbintech.jira.report.DAO.GetSingleton;
import ru.rshbintech.jira.report.lib.I18n;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Класс для чтения файла эксель. Основной метод readFile.
 */

public class ReadFileExcel {

    /**
     * Чтение книги MS Excel.
     *
     * @param inputStream Книга MS Excel в виде Stream
     * @return Мэп с названием листа книги и коллекцией строк данного листа. Берутся только не скрытые листы
     * (not SheetHidden && not SheetVeryHidden)
     * @throws IOException
     */
    public Map<String, List<Row>> readFile(InputStream inputStream) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        Row row;
        Map<String, List<Row>> book = new HashMap<>(wb.getNumberOfSheets());
        Sheet sheet;
        for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
            if (!wb.isSheetHidden(sheetNum) && !wb.isSheetVeryHidden(sheetNum)) {
                sheet = wb.getSheetAt(sheetNum);
                List<Row> rows = new ArrayList<>();
                for (int j = 0; j < sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if (row.getCell(0).getCellType() != CellType.BLANK
                            && row.getCell(1).getCellType() != CellType.BLANK
                            && row.getCell(2).getCellType() != CellType.BLANK) {
                        rows.add(row);
                    } else {
                        System.out.println("break " + j);
                        break;
                    }
                }
                book.put(sheet.getSheetName(), rows);
            }
        }
        return book;
    }

    /**
     * Запись в БД строк (Row). Заполняется массив типа Object. Тип обьекта в массиве будет соответствовать типу ячейки
     * в Row.
     * @param book Мэп с названием листа книги и коллекцией строк данного листа
     * @param sheetName названия листа для парса
     * @param columns Номера колонок в исходном файле эксель, которые необходимо взять
     * @throws SQLException
     */
    public int writeMapToDB(Map<String, List<Row>> book, String sheetName, String[] columns) throws Exception {

        List<Row> rows;
        rows = book.get(sheetName);
        Object[] result = new Object[rows.size() - 1];
        Object[] objects;
        for (int i = 1; i < rows.size(); i++) {
            objects = new Object[columns.length];
            for (int j = 0; j < columns.length; j++) {
                switch (rows.get(i).getCell(Integer.parseInt(columns[j]) - 1).getCellType()) {
                    case NUMERIC:
                    case FORMULA:
                        objects[j] = rows.get(i).getCell(Integer.parseInt(columns[j]) - 1).getNumericCellValue();
                        break;
                    case STRING:
                        objects[j] = rows.get(i).getCell(Integer.parseInt(columns[j]) - 1).getStringCellValue()
                                .replaceAll("[\\n]", "").trim();
                        break;
                    case BLANK:
                        objects[j] = "";
                        break;
                    default:
                        System.out.println(rows.get(i).getCell(Integer.parseInt(columns[j]) - 1).getCellType());
                }
            }
            result[i - 1] = objects;
        }
        String[] names = I18n.getText("tempTableColumnsNames").split(",");
        return GetSingleton.getInstance().getSimpleReportsDAO().writeReportDAOImpl(I18n.getText("tempTable")
                , result, names);
    }
}