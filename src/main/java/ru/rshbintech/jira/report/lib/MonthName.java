package ru.rshbintech.jira.report.lib;

import java.util.HashMap;
import java.util.Map;

public class MonthName {
    
    
    public static String[] MONTH_NAME = {
            I18n.getText("Month.1"),
            I18n.getText("Month.2"),
            I18n.getText("Month.3"),
            I18n.getText("Month.4"),
            I18n.getText("Month.5"),
            I18n.getText("Month.6"),
            I18n.getText("Month.7"),
            I18n.getText("Month.8"),
            I18n.getText("Month.9"),
            I18n.getText("Month.10"),
            I18n.getText("Month.11"),
            I18n.getText("Month.12")
    };

    public static String[] UNCASE_MONTH_NAME = {
            I18n.getText("month.1"),
            I18n.getText("month.2"),
            I18n.getText("month.3"),
            I18n.getText("month.4"),
            I18n.getText("month.5"),
            I18n.getText("month.6"),
            I18n.getText("month.7"),
            I18n.getText("month.8"),
            I18n.getText("month.9"),
            I18n.getText("month.10"),
            I18n.getText("month.11"),
            I18n.getText("month.12")
    };

    public static String get(int i, String type) {
        switch (type) {
            case "UNCASE_MONTH_NAME":
                return UNCASE_MONTH_NAME[i];

            case "MONTH_NAME":
            default:
                return MONTH_NAME[i];
        }
    }

    /**
     * Возвращает Map с месяцами и индексами 0..11
     *
     * @return  - Map
     */
    public static Map<String, Integer> getMonthMap() {
        int i = 0;
        Map<String, Integer> map = new HashMap<>();
        for (String monthName : MONTH_NAME) {
            map.put(monthName, i++);
        }
        return map;
    }
}
