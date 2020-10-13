package ru.rshbintech.jira.report.DAO;

/**
 * SQL запросы для получения данных
 * Запрос обязательно должен вернуть 2 столбца! Причем 1 из них должен быть уникальным!
 * Если запрос с параметром, то необходимо вместо параметра поставить %d - он будет подставлен
 */
public enum AdditionalReportEnum {
    CUSTOM_FIELD( "SELECT ID, customvalue FROM customfieldoption WHERE CUSTOMFIELD=%d"),
    FIO("SELECT id,display_name,active FROM dbo.RSHB_userTable() where active = 1 and display_name like '%[А-я]%'"),
    BIQ("SELECT ID, CAST(issuenum as varchar(20)) + ' - ' + SUMMARY FROM jiraissue WHERE jiraissue.PROJECT = 10701"),
    PS("SELECT ID, CAST(issuenum as varchar(20)) + ' - ' + SUMMARY FROM jiraissue WHERE jiraissue.PROJECT = 11001"),
    UPRAVLENIE("SELECT ID, upravlenie FROM RSHB_SpUpravlenie"),
    DEPARMENT("SELECT ID, department FROM RSHB_SpDepartment WHERE upravlenie_id=%d");

    private final String sql;

    public String getSql() {
        return sql;
    }

    AdditionalReportEnum(String sql) {
        this.sql = sql;
    }
}
