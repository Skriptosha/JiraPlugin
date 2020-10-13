package ru.rshbintech.jira.report.lib;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataTools {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Функция преобразует формат поиска пользователя в формат поиска SQL
     *
     * @param text - воходяший текс
     * @return текст после преобразования
     */
    public static String toSQL(String text) {
        return text.replaceAll("\\*", "%");
    }

    /**
     * Проверить объект на NULL
     *
     * @param o - объект
     * @return boolean
     */
    public static boolean isNull(Object o) {
        return o == null;
    }

    /**
     * в строке все запятые заменить точками
     *
     * @param text - строка
     * @return строка без запятых
     */
    public static String commaToPoint(String text) {
        return text.replaceAll(",", ".");
    }

    /**
     * форматировать дату см DataTools.DATE_FORMAT
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);

    }

    /**
     * Сконвертировать дату в нужный формат
     *
     * @param date       - дата
     * @param dateFormat - формат
     * @return строка
     */
    public static String formatDate(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        return format.format(date);
    }

    /**
     * Дробные часы в 00ч 00м
     *
     * @param time
     * @return
     */
    public static String hoursMinutesFromFraction(double time) {
        int hours = (int) time; //целая часть - это часы
        int minutes = (int) ((time - hours) * 60); // дробная часть в минутах
        return hours + "h " + minutes + "m";
    }

    /**
     * get name Excel column
     *
     * @param column - index column
     * @return String
     */
    public static String getColumnFromIndex(int column) {
        int dividend = column;
        String columnName = "";
        short modulo;
        while (dividend > 0) {
            modulo = (short) ((dividend - 1) % 26);
            columnName = (char) (('A' + modulo)) + columnName;
            dividend = (dividend - modulo) / 26;
        }
        return columnName;
    }

    /**
     * get month name in period or period
     *
     * @param beginDate - begin date
     * @param endDate   - end date
     * @return String month name or period
     */
    public static String monthNameOrPeriod(String beginDate, String endDate) {
        Calendar beginCalendar = parseStringToDate(beginDate);
        Calendar endCalendar = parseStringToDate(endDate);

        String strBeginDate = "01." + monthNumber(beginCalendar.get(Calendar.MONTH)) + "." + beginCalendar.get(Calendar.YEAR);
        String strEndDate = endCalendar.getActualMaximum(Calendar.DATE) + "." + monthNumber(endCalendar.get(Calendar.MONTH)) + "." + endCalendar.get(Calendar.YEAR);

        if (beginCalendar.get(Calendar.YEAR) != endCalendar.get(Calendar.YEAR)) {
            System.out.println("YEAR");
            return I18n.getText("service.c") + " " + beginDate + " " + I18n.getText("service.by") + " " + endDate;
        } else if (beginCalendar.get(Calendar.MONTH) != endCalendar.get(Calendar.MONTH)) {
            System.out.println("MONTH");
            return I18n.getText("service.c") + " " + beginDate + " " + I18n.getText("service.by") + " " + endDate;
        } else if (!beginDate.equals(strBeginDate)) {
            System.out.println("beginDate");
            return I18n.getText("service.c") + " " + beginDate + " " + I18n.getText("service.by") + " " + endDate;
        } else if (!endDate.equals(strEndDate)) {
            System.out.println("endDate");
            return I18n.getText("service.c") + " " + beginDate + " " + I18n.getText("service.by") + " " + endDate;
        } else {
            System.out.println("month_name");
            return I18n.getText("service.behind") + " " + MonthName.UNCASE_MONTH_NAME[beginCalendar.get(Calendar.MONTH)] + " " + beginCalendar.get(Calendar.YEAR);
        }
    }

    /**
     * get parse String to Date
     *
     * @param strDate - date string format dd.MM.yyyy
     * @return Calendar
     */
    public static Calendar parseStringToDate(String strDate) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = DATE_FORMAT.parse(strDate);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return calendar;
    }

    /**
     * get String month number
     *
     * @param month - month index [0..11]
     * @return String
     */
    public static String monthNumber(int month) {
        return (month + 1) < 10 ? "0" + (month + 1) : "" + (month + 1);
    }
}
