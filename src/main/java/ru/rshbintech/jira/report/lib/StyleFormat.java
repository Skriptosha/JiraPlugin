package ru.rshbintech.jira.report.lib;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для форматирования стиля ячеек, для выгрузки в EXCEL через POI
 * По идее можно использовать клонирование стилей cloneStyleFrom, но так как
 * мы грузим Font из книги, то получается разрыв связей у Font
 * Created by Latypov-RR on 11.07.2017.
 */
public class StyleFormat {

    public enum StyleEnum {
        DEFAULT_CENTER("По умолчнию (Calibri 11 белый по центру)"),
        DEFAULT_LEFT("По умолчнию (Calibri 11 белый по левому краю)"),
        TITLE("Шапка"),
        TABLE_TITLE("Заголовки в таблице"),
        TABLE_TITLE_ORANGE("Заголовки в таблице оранжевого цвета"),
        TABLE_TITLE_LIGHT_YELLOW("Заголовки в таблице светло желтого цвета"),
        TABLE_TITLE_LIGHT_BLUE("Заголовки в таблице светло синего цвета"),
        TABLE_TITLE_LIGHT_GREEN("Заголовки в таблице светло зеленого цвета"),
        // без цвета
        TABLE_STRING_WHITE("Текстовая ячейка таблицы"),
        TABLE_DOUBLE_WHITE("Числовая ячейка таблицы"),
        TABLE_DATE_WHITE("Ячейка таблицы формата даты"),

        // зеленый цвет
        TABLE_STRING_LIGHT_GREEN("Текстовая ячейка светло зеленого цвета"),
        TABLE_DOUBLE_LIGHT_GREEN("Числовая ячейка светло зеленого цвета"),
        TABLE_DOUBLE_DARK_GREEN("Числовая ячейка темно зеленого цвета"),
        TABLE_STRING_GREEN("Текстовая ячейка зеленого цвета"),
        TABLE_DOUBLE_GREEN("Числовая ячейка зеленого цвета"),
        // синий цвет
        TABLE_STRING_LIGHT_BLUE("Текстовая ячейка светло синего цвета"),
        TABLE_DOUBLE_LIGHT_BLUE("Числовая ячейка светло синего цвета"),
        TABLE_DOUBLE_DARK_BLUE("Числовая ячейка темно синего цвета"),
        TABLE_STRING_BLUE("Текстовая ячейка синего цвета"),
        TABLE_DOUBLE_BLUE("Числовая ячейка синего цвета"),
        // желтый цвет
        TABLE_STRING_LIGHT_YELLOW("Текстовая ячейка светло желтого цвета"),
        TABLE_DOUBLE_LIGHT_YELLOW("Числовая ячейка светло желтого цвета"),
        TABLE_DOUBLE_DARK_YELLOW("Числовая ячейка темно желтого цвета"),
        TABLE_STRING_YELLOW("Текстовая ячейка желтого цвета"),
        TABLE_DOUBLE_YELLOW("Числовая ячейка желтого цвета"),
        // оранжевый цвет
        TABLE_STRING_LIGHT_ORANGE("Текстовая ячейка светло оранжевого цвета"),
        TABLE_DOUBLE_LIGHT_ORANGE("Числовая ячейка светло оранжевого цвета"),
        TABLE_DOUBLE_DARK_ORANGE("Числовая ячейка темно оранжевого цвета"),
        TABLE_STRING_ORANGE("Текстовая ячейка оранжевого цвета"),
        TABLE_DOUBLE_ORANGE("Числовая ячейка оранжевого цвета"),
        // серый цвет
        TABLE_STRING_LIGHT_GRAY("Текстовая ячейка светло серого цвета"),
        TABLE_STRING_LIGHT_GRAY_TITLE("Текстовая ячейка светло серого цвета шапки таблицы"),
        TABLE_DOUBLE_LIGHT_GRAY("Числовая ячейка светло серого цвета"),
        TABLE_STRING_GRAY("Текстовая ячейка серого цвета"),
        TABLE_DOUBLE_GRAY("Числовая ячейка серого цвета"),
        ;

        private String defaultMessage;

        StyleEnum(String msg) {
            defaultMessage = msg;
        }

        public String getDefaultMessage() {
            return defaultMessage;
        }

        /**
         * метод поиска стиля
         *
         * @param value - поиск по имени
         * @return найденный стиль иначе DEFAULT_CENTER
         */
        public static StyleEnum getEnumName(String value) {
            for (StyleEnum v : values())
                if (v.name().equalsIgnoreCase(value)) return v;
            return DEFAULT_CENTER;
        }
    }

    enum BackgroundColor {

        WHITE(new Color(255, 255, 255)),
        GREEN(new Color(198, 214, 180)),
        LIGHT_GREEN(new Color(226, 239, 218)),
        DARK_GREEN(new Color(169, 208, 142)),
        BLUE(new Color(189, 215, 238)),
        LIGHT_BLUE(new Color(221, 235, 247)),
        DARK_BLUE(new Color(155, 194, 230)),
        YELLOW(new Color(255, 230, 153)),
        LIGHT_YELLOW(new Color(255, 242, 204)),
        DARK_YELLOW(new Color(255, 217, 104)),
        ORANGE(new Color(248, 203, 173)),
        LIGHT_ORANGE(new Color(252, 228, 214)),
        DARK_ORANGE(new Color(244, 176, 132)),
        GRAY(new Color(191, 191, 191)),
        LIGHT_GRAY(new Color(217, 217, 217)),
        LIGHT_LIGHT_GRAY(new Color(242, 242, 242)),
        ;


        Color backgroundColor;

        BackgroundColor(Color color) {
            backgroundColor = color;
        }

        public Color getBackgroundColor() {
            return backgroundColor;
        }
    }

    Map<StyleEnum, XSSFCellStyle> styles;

    /**
     * Заполняем стили ячеек по разным цветам для отчетов JIRAFS-842
     * переработка кода TT-16822
     *
     * @param wb
     * @return
     */
    public static Map<StyleEnum, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
        Map<StyleEnum, XSSFCellStyle> styles = new HashMap<>();

        ru.rshbintech.jira.report.lib.StyleFormat style
                = new ru.rshbintech.jira.report.lib.StyleFormat();
        FontFormat font = new FontFormat();
        styles.put(StyleEnum.DEFAULT_CENTER, style.cs_StringHCenterVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "", ""), wb, "WHITE"));
        styles.put(StyleEnum.DEFAULT_LEFT, style.cs_StringHLeftVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "", ""), wb, "WHITE"));
        styles.put(StyleEnum.TITLE, style.cs_StringHCenterVCenterWTColor(font.fontOptions(wb, "Calibri", 14, "b", ""), wb, "WHITE"));
        styles.put(StyleEnum.TABLE_TITLE, style.cs_StringHCenterVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                , BackgroundColor.LIGHT_LIGHT_GRAY.getBackgroundColor()));
        styles.put(StyleEnum.TABLE_TITLE_ORANGE, style.cs_StringHCenterVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                , BackgroundColor.ORANGE.getBackgroundColor()));
        styles.put(StyleEnum.TABLE_TITLE_LIGHT_BLUE, style.cs_StringHCenterVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                , BackgroundColor.LIGHT_BLUE.getBackgroundColor()));
        styles.put(StyleEnum.TABLE_TITLE_LIGHT_GREEN, style.cs_StringHCenterVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                , BackgroundColor.LIGHT_GREEN.getBackgroundColor()));
        styles.put(StyleEnum.TABLE_TITLE_LIGHT_YELLOW, style.cs_StringHCenterVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                , BackgroundColor.LIGHT_YELLOW.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_STRING_WHITE
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.WHITE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_WHITE
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "", ""), wb
                        , BackgroundColor.WHITE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_STRING_LIGHT_GREEN
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.LIGHT_GREEN.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_LIGHT_GREEN
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "", ""), wb
                        , BackgroundColor.LIGHT_GREEN.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_DARK_GREEN
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.DARK_GREEN.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_STRING_GREEN
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.GREEN.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_GREEN
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.GREEN.getBackgroundColor()));


        styles.put(StyleEnum.TABLE_STRING_LIGHT_BLUE
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.LIGHT_BLUE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_LIGHT_BLUE
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "", ""), wb
                        , BackgroundColor.LIGHT_BLUE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_DARK_BLUE
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.DARK_BLUE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_STRING_BLUE
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.BLUE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_BLUE
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.BLUE.getBackgroundColor()));


        styles.put(StyleEnum.TABLE_STRING_LIGHT_YELLOW
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.LIGHT_YELLOW.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_LIGHT_YELLOW
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "", ""), wb
                        , BackgroundColor.LIGHT_YELLOW.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_DARK_YELLOW
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.DARK_YELLOW.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_STRING_YELLOW
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.YELLOW.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_YELLOW
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.YELLOW.getBackgroundColor()));


        styles.put(StyleEnum.TABLE_STRING_LIGHT_ORANGE
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.LIGHT_ORANGE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_LIGHT_ORANGE
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "", ""), wb
                        , BackgroundColor.LIGHT_ORANGE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_DARK_ORANGE
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.DARK_ORANGE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_STRING_ORANGE
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.ORANGE.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_ORANGE
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.ORANGE.getBackgroundColor()));


        styles.put(StyleEnum.TABLE_STRING_LIGHT_GRAY_TITLE
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 11, "b", ""), wb
                        , BackgroundColor.LIGHT_GRAY.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_STRING_LIGHT_GRAY
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 12, "b", ""), wb
                        , BackgroundColor.LIGHT_GRAY.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_LIGHT_GRAY
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 12, "b", ""), wb
                        , BackgroundColor.LIGHT_GRAY.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_STRING_GRAY
                , style.cs_StringBorderAllVCenterWTColor(font.fontOptions(wb, "Calibri", 12, "b", ""), wb
                        , BackgroundColor.GRAY.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_GRAY
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 12, "b", ""), wb
                        , BackgroundColor.GRAY.getBackgroundColor()));

        styles.put(StyleEnum.TABLE_DOUBLE_GRAY
                , style.cs_DoubleBorderAllHRightVCenterWTColor(font.fontOptions(wb, "Calibri", 12, "b", ""), wb
                        , BackgroundColor.GRAY.getBackgroundColor()));

        return styles;
    }

    private Color formatColor(String color) {
        if ("GREEN".equalsIgnoreCase(color)) {
            return Color.GREEN;
        } else if ("DARK_BLUE".equalsIgnoreCase(color)) {
            return new Color(108, 183, 230);
        } else if ("REDP".equalsIgnoreCase(color)) {
            return new Color(230, 0, 7);
        } else if ("RED".equalsIgnoreCase(color)) {
            return new Color(230, 186, 186);
        } else if ("LIGHT_RED".equalsIgnoreCase(color)) {
            return new Color(239, 246, 234);
        } else if ("LIGHT_GREEN".equalsIgnoreCase(color)) {
            return new Color(198, 224, 180);
        } else if ("LIGHT_LIGHT_GREEN".equalsIgnoreCase(color)) {
            return new Color(226, 239, 218);
        } else if ("DARK_GREEN".equalsIgnoreCase(color)) {
            return new Color(0, 170, 15);
        } else if ("LIGHT_BLUE".equalsIgnoreCase(color)) {
            return new Color(155, 255, 255);
        } else if ("SKY_BLUE".equalsIgnoreCase(color)) {
            return new Color(135, 206, 235);
        } else if ("LIGHT_LIGHT_LIGHT_GREEN".equalsIgnoreCase(color)) {
            return new Color(239, 246, 234);
        } else if ("LIGHT_YELLOW".equalsIgnoreCase(color)) {
            return new Color(255, 242, 204);
        } else if ("DARK_YELLOW".equalsIgnoreCase(color)) {
            return new Color(255, 207, 71);
        } else if ("LIGHT_GREY".equalsIgnoreCase(color)) {
            return new Color(231, 231, 231);
        } else if ("GREY".equalsIgnoreCase(color)) {
            return new Color(192, 192, 192);
        } else if ("WHITE".equalsIgnoreCase(color)) {
            return Color.WHITE;
        } else if ("YELLOW".equalsIgnoreCase(color)) {
            return Color.YELLOW;
        } else if ("ORANGE".equalsIgnoreCase(color)) {
            return new Color(252, 213, 180);
        } else if ("PURPLE".equalsIgnoreCase(color)) {
            return new Color(204, 192, 218);
        }
        return Color.black;
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringHCenterVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, String color) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor/*IndexedColors.PALE_BLUE.getIndex()*/);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringHLeftVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, String color) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor/*IndexedColors.PALE_BLUE.getIndex()*/);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * метод для изменения границ в ячейке
     *
     * @param workBook     - книга
     * @param style        - стиль который будет взят за основу
     * @param borderTop    - граница с верху
     * @param borderRight  - граница с права
     * @param borderBottom - граница снизу
     * @param borderLeft   - граница слева
     * @return новый стиль
     */
    public static XSSFCellStyle setBorder(XSSFWorkbook workBook, XSSFCellStyle style
            , int borderTop
            , int borderRight
            , int borderBottom
            , int borderLeft
    ) {
        XSSFCellStyle style1 = workBook.createCellStyle();
        style1.cloneStyleFrom(style);
        style1.setBorderTop(checkBorder(borderTop));
        style1.setBorderRight(checkBorder(borderRight));
        style1.setBorderBottom(checkBorder(borderBottom));
        style1.setBorderLeft(checkBorder(borderLeft));
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style1;
    }

    /**
     * метод проверяет каакой индекс границы ячейки
     * если в пределах границы 0 <= i < 14 то вернет данное число иначе 0
     *
     * @param i - индекс границы ячейки
     * @return short
     */
    private static BorderStyle checkBorder(int i) {
        return BorderStyle.valueOf((short)(i >= 0 && i < 14 ? i : 0));
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHCenterVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringHCenterVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Right , WT True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHRightVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Left , WT True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHLeftVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Left , WT True, V Vertical Alignment Top
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHLeftVTopWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Left , WT True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHCenterVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }


    /**
     * Border ALL, Alignment Center , WT True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleHLeftVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    public XSSFCellStyle cs_StringBorderAllVTopHCenterWTColor(XSSFFont font, XSSFWorkbook workBook, Color color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        XSSFColor myColor = new XSSFColor(color);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * WT- WrapText True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);

        return (style);
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringHCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setAlignment(HorizontalAlignment.CENTER);

        return (style);
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllVCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return (style);
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllCenterVCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);

        return (style);
    }

    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, String color) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor/*IndexedColors.PALE_BLUE.getIndex()*/);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }


    /**
     * H - HorizontalAlignment Center, WT- WrapText True, V Vertical Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHCenterVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, String color) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor/*IndexedColors.PALE_BLUE.getIndex()*/);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border Bottom
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_LineBottom(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);

        return (style);
    }

    /**
     * Border Bottom,Right
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_LineBottomRight(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font); 
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return (style);
    }

    /**
     * H - Horizontal Alignment center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringHCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * H - Horizontal Alignment right
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */

    public XSSFCellStyle cs_StringHRight(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * H - Horizontal Alignment right
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */

    public XSSFCellStyle cs_StringHLeft(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * H - Horizontal Alignment center, V Vertical Alignment top
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringHCenterVTop(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * V Vertical Alignment top
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringVTop(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Alignment Center, WrapText True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER); 
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, Alignment Right , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllRightWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        //style.setAlignment(HorizontalAlignment.RIGHT);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL,  V Vertical Alignment top, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllVTopWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    public XSSFCellStyle cs_StringBorderAllVTopHCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        XSSFColor myColor = new XSSFColor(Color.LIGHT_GRAY);
        style.setFillForegroundColor(myColor/*IndexedColors.PALE_BLUE.getIndex()*/);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllVCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();

        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00;[Red]-#,##0.00"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHCenterColor(XSSFFont font, XSSFWorkbook workBook, String color) {

        XSSFCellStyle style = workBook.createCellStyle();

        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00;[Red]-#,##0.00"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHCenterColorRUB(XSSFFont font, XSSFWorkbook workBook, String color) {

        XSSFCellStyle style = workBook.createCellStyle();

        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00\"" + I18n.getText("ruble") + "\";[Red]-#,##0.00\"" + I18n.getText("ruble") + "\""));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllTMHCenterColor(XSSFFont font, XSSFWorkbook workBook, String color) {

        XSSFCellStyle style = workBook.createCellStyle();

        style.setFont(font);
        style.setBorderTop(BorderStyle.MEDIUM); 
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00;[Red]-#,##0.00"));
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHLeft(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHLeftVCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        return (style);
    }

    /**
     * Border ALL, Alignment Дуае , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DateBorderAllHLeft(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("DD.MM.YYYY"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DateBorderAllHLeftVCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("DD.MM.YYYY"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center Vertical Center, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DateBorderAllHCenterVCenter(XSSFFont font, XSSFWorkbook workBook, String color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("DD.MM.YYYY"));
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHRigth(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00;[Red]-#,##0.00"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHCenterVCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00;[Red]-#,##0.00"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_IntegerBorderAllHCenterVCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_Double0BorderAllHCenterVCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0;[Red]-#,##0"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllHCenterVCenterBM(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFDataFormat format = workBook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00;[Red]-#,##0.00"));
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHLeft(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHLeftVTop(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHLeftVCenter(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllTMHLeftVTop(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHRigth(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHRigthColor(XSSFFont font, XSSFWorkbook workBook, String color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHLeftColor(XSSFFont font, XSSFWorkbook workBook, String color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHLeftVCenterColor(XSSFFont font, XSSFWorkbook workBook, String color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderAllHLeftVCenterColorBM(XSSFFont font, XSSFWorkbook workBook, String color) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Center , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllVCenterWTColor(XSSFFont font, XSSFWorkbook workBook, String color) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        XSSFColor myColor = new XSSFColor(formatColor(color));
        style.setFillForegroundColor(myColor/*IndexedColors.PALE_BLUE.getIndex()*/);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return (style);
    }

    /**
     * Border ALL, Alignment Right , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleBorderAllRightWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat((short) 4);
        return (style);
    }

    /**
     * Border ALL, Alignment Right , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_Double(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setDataFormat((short) 4);
        return (style);
    }

    /**
     * Border ALL, Alignment Right , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleHRight(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setDataFormat((short) 4);
        style.setAlignment(HorizontalAlignment.RIGHT);
        return (style);
    }

    /**
     * Border ALL, Alignment Right , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_DoubleHCenter(XSSFFont font, XSSFWorkbook workBook) {
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setDataFormat((short) 4);
        style.setAlignment(HorizontalAlignment.CENTER);
        return (style);
    }

    /**
     * Border ALL, Alignment Right , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_IntegerBorderAllRightWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat((short) 1);
        return (style);
    }

    /**
     * Border ALL, Alignment Left , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderBottomLeftWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT); 
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, Alignment Right , WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderBottomRightWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border Bot, Alignment Right , V Vertical Alignment top, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderBottomVTopWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border ALL, WT True
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_IntegerBorderAllCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat((short) 1);
        return (style);
    }

    /**
     * Border bottom, WT True,  Alignment Center
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderBottomCenterWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

    /**
     * Border bottom, WT True,  Alignment Center, V Vertical Alignment top
     *
     * @param font     Загружаем Font сформированный в FontFormat, либо собранный в самой книге
     * @param workBook книга к которой будет относиться Style
     * @return Возвращает отформатированный стиль
     * @see FontFormat
     */
    public XSSFCellStyle cs_StringBorderBottomCenterVTopWT(XSSFFont font, XSSFWorkbook workBook) {

        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        style.setDataFormat((short) 0);
        return (style);
    }

}