package ru.rshbintech.jira.report.DAO;

import ru.rshbintech.jira.report.webwork.ReadFileExcel;

public class GetSingleton {
    private static SimpleReportsDAOImpl simpleReportsDAO = null;
    private static AdditionalReportDAOImpl additionalReportDAO = null;
    private static ReadFileExcel readFileExcel = null;
    private static GetSingleton instance = null;

    public static synchronized GetSingleton getInstance() {
        if (instance == null) {
            instance = new GetSingleton();
        }
        return instance;
    }

    public SimpleReportsDAOImpl getSimpleReportsDAO() {
        if (simpleReportsDAO == null) {
            simpleReportsDAO = new SimpleReportsDAOImpl();
        }
        return simpleReportsDAO;
    }

    public AdditionalReportDAOImpl getAdditionalReportDAO() {
        if (additionalReportDAO == null) {
            additionalReportDAO = new AdditionalReportDAOImpl();
        }
        return additionalReportDAO;
    }

    public ReadFileExcel getReadFileExcel() {
        if (readFileExcel == null) {
            readFileExcel = new ReadFileExcel();
        }
        return readFileExcel;
    }
}