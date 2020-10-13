package ru.rshbintech.jira.report.lib;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;

/**
 * Класс для форматирования текста ячеек, для выгрузки в EXCEL через POI
 * Created by Latypov-RR on 11.07.2017.
 */
public class FontFormat {
    /**
     * Шрифт Times New Roman, размер 10, Толщина текста Жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes16Bold(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        font.setFontName("Times New Roman");

        return (font);
    }

    /**
     * Шрифт Times New Roman, размер 10, Толщина текста Жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes10Bold(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        font.setFontName("Times New Roman");

        return (font);
    }

    /**
     * Шрифт по умолчанию, размер 8, Толщина текста NORMAL, Цвет Normal
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont Font8BoldweightNormalColorDefault(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setColor(XSSFFont.COLOR_NORMAL);
        font.setBold(false);

        return (font);
    }

    /**
     * Шрифт по умолчанию, размер 6, Толщина текста NORMAL, Цвет Normal
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont Font6BoldweightNormalColorDefault(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 6);
        font.setColor(XSSFFont.COLOR_NORMAL);
        font.setBold(false);

        return (font);
    }

    /**
     * Шрифт Arial, размер 7
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial7(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 7);
        font.setFontName("Arial");


        return (font);
    }

    /**
     * Шрифт Arial, размер 8 жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial8Bold(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setBold(true);
        font.setFontName("Arial");


        return (font);
    }

    /**
     * Шрифт Arial, размер 8
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial8(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("Arial");


        return (font);
    }

    /**
     * Шрифт по умолчанию, размер 7
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes7(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 7);
        font.setFontName("Times New Roman");


        return (font);
    }

    /**
     * Шрифт Times New Roman, размер 10, Толщина текста Жирный, Цвет Blue
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes10BoldColorBlue(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        font.setFontName("Times New Roman");
        font.setColor(IndexedColors.BLUE.getIndex());

        return (font);
    }

    /**
     * Шрифт Times New Roman, размер 12, Толщина текста Жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes12Bold(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        font.setFontName("Times New Roman");

        return (font);
    }

    /**
     * Шрифт Times New Roman, размер 12
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes12(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Times New Roman");

        return (font);
    }

    /**
     * Шрифт Arial, размер 10, Толщина текста Жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial10Bold(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        font.setFontName("Arial");

        return (font);
    }

    /**
     * Шрифт Calibri, размер 11, Толщина текста Жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontCalibri11Bold(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        font.setFontName("Calibri");

        return (font);
    }

    /**
     * Шрифт Arial, размер 10, Толщина текста Жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial12Bold(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        font.setFontName("Arial");

        return (font);
    }

    /**
     * Шрифт Arial, размер 11, Толщина текста Жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial11(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        font.setFontName("Arial");

        return (font);
    }

    /**
     * Шрифт Arial, размер 10
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial10(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");

        return (font);
    }

    /**
     * Шрифт Arial, размер 6
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial6(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 6);
        font.setFontName("Arial");

        return (font);
    }

    /**
     * Шрифт Arial, размер 9 , Толщина текста Жирный
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial9(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName("Arial");

        return (font);
    }

    /**
     * Шрифт Arial, размер 10, Толщина текста Жирный, Подчеркнутый
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontArial10BoldUnderline(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        font.setUnderline(XSSFFont.U_SINGLE);
        font.setFontName("Arial");

        return (font);
    }

    /**
     * Шрифт Times New Roman, размер 10
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes10(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Times New Roman");

        return (font);
    }

    /**
     * Шрифт Times New Roman, размер 10
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes10StrikeOut(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setStrikeout(true);
        font.setFontName("Times New Roman");

        return (font);
    }

    /**
     * Шрифт Times New Roman, размер 11
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes11(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Times New Roman");

        return (font);
    }

    /**
     * Шрифт Times New Roman, размер 11
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont FontTimes11Bold(XSSFWorkbook workBook) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        font.setFontName("Times New Roman");

        return (font);
    }

    /**
     * Шрифт настраиваемый
     *
     * @param workBook книга к которой будет относиться Font
     * @return Возвращает отформатированный Font
     */
    public XSSFFont fontOptions(XSSFWorkbook workBook, String fontName, int size, String type, String color) {

        XSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) size);
        switch (type) {
            case "b":
                font.setBold(true);
                break;
            case "u":
                font.setUnderline(XSSFFont.U_SINGLE);
                break;
            case "i":
                font.setItalic(true);
                break;
            case "bi":
            case "ib":
                font.setBold(true);
                font.setItalic(true);
                break;
            case "bu":
            case "ub":
                font.setBold(true);
                font.setUnderline(XSSFFont.U_SINGLE);
                break;
            case "ui":
            case "iu":
                font.setUnderline(XSSFFont.U_SINGLE);
                font.setItalic(true);
                break;
            case "all":
            case "bui":
            case "biu":
            case "iub":
            case "ibu":
            case "uib":
            case "ubi":
                font.setBold(true);
                font.setUnderline(XSSFFont.U_SINGLE);
                font.setItalic(true);
                break;
            default:
                break;
        }

        // попытаемя найти в IndexedColors
        try {
            font.setColor(IndexedColors.valueOf(color).getIndex());
        } catch (IllegalArgumentException ex) { // если там нет то предположительно пришел код цвета
            try {
                Color color1 = Color.decode(color);
                XSSFColor xssfColor = new XSSFColor(color1);
                font.setColor(xssfColor);
            } catch (NumberFormatException ignored) {

            }
        }

        font.setFontName(fontName);

        return (font);
    }
}