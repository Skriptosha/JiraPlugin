package ru.rshbintech.jira.report.lib;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PoiExcel {

    /**
     * Процедура для сохранения файла Excel на клиенте
     *
     * @param workbook - книга
     * @param fileName - имя файла
     * @param response - http сессия
     * @throws IOException при возникновении ошибки пробросим
     */
    public static void saveClient(Workbook workbook, String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String s = "attachment;filename=\"" + fileName + ".xlsx\"";
        response.setHeader("Content-Disposition", s);
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
    }

    public static void saveFile(Workbook workbook, String fileName) throws IOException {
        OutputStream out = new FileOutputStream("C:\\Program Files\\Atlassian\\Application Data\\JIRA-6.4.8\\export\\" + fileName + ".xlsx");
        workbook.write(out);
        out.close();
    }
}
