package ru.rshbintech.jira.report.DAO;

import org.ofbiz.core.entity.GenericEntityException;
import ru.rshbintech.jira.report.lib.ConnectionDB;
import ru.rshbintech.jira.report.webwork.SimpleReportsActionN;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class SimpleReportsDAOImpl {
    private String[] columns = {"procedureId", "procedureName", "reportName", "ActionClass", "JSFunction", "GroupsForAccess"};
    private String nameParam = "parameter";
    private String typeParam = "parameterType";
    private Map<String, Object> reportsReportName;
    private Map<String, Object> reportsActionClasses;
    private Map<String, Object> reportsProcedureName;
    private Map<String, Object> reportsJSFunctions;
    private Map<String, Object> reportsGroupsForAccess;
    private Connection connection;

    public Statement getStmt() throws SQLException, GenericEntityException {
        return (connection == null || connection.isClosed())
                ? (connection = ConnectionDB.getSqlProcessor().getConnection())
                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                : connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public void rsClose(ResultSet resultSet) throws SQLException {
        Statement stmt = resultSet.getStatement();
        resultSet.close();
        if (stmt != null) stmt.close();
    }

    /**
     * Основной метод по работе с базой для отчетов simpleReports (таблица simpleReports). Заполняет Мэпы по столбцам
     * таблицы, где ключом является procedureId отчета
     *
     * @return reportsReportName
     */
    public Map<String, Object> getNumberOfSimpleReportsDAOImpl() throws SQLException, GenericEntityException {
        ResultSet rs = getStmt().executeQuery("SELECT * FROM simpleReports");
        if (rs == null) throw new SQLException("ResultSet is empty");
        int size = getSize(rs);
        reportsReportName = new HashMap<>(size);
        reportsActionClasses = new HashMap<>(size);
        reportsProcedureName = new HashMap<>(size);
        reportsJSFunctions = new HashMap<>(size);
        reportsGroupsForAccess = new HashMap<>(size);
        while (rs.next()) {
            reportsProcedureName.put(String.valueOf(rs.getInt(columns[0])), rs.getString(columns[1]));
            reportsReportName.put(String.valueOf(rs.getInt(columns[0])), rs.getString(columns[2]));
            reportsActionClasses.put(String.valueOf(rs.getInt(columns[0])), rs.getString(columns[3]));
            reportsJSFunctions.put(String.valueOf(rs.getInt(columns[0])), rs.getString(columns[4]));
            reportsGroupsForAccess.put(String.valueOf(rs.getInt(columns[0])), rs.getString(columns[5]) == null ? "" : rs.getString(columns[5]));
        }
        rsClose(rs);
        return reportsReportName;
    }

    /**
     * Метод для получения данных из таблицы simpleReportsParams (параметры отчета). Данные получаются по procedureId
     * (совпадает с procedureId из таблицы simpleReports)
     *
     * @param procedureId Уникальный ключ таблицы
     * @return Мэп с параметрами для таблицы
     */
    public Map<String, Object> getParamsOfSimpleReportsDAOImpl(String procedureId) throws SQLException, GenericEntityException {
        ResultSet rs = getStmt().executeQuery("SELECT * FROM simpleReportsParams WHERE "
                + columns[0] + " = " + procedureId);
        Map<String, Object> params = new LinkedHashMap<>(getSize(rs));
        while (rs.next()) {
            params.put(rs.getString(nameParam), rs.getString(typeParam));
        }
        rsClose(rs);
        return params;
    }

    /**
     * Выполнение хранимой процедуры (функции) для отчета. Выполнение происходит через класс SimpleReportsAction
     * для каждого полученного ResultSet"а.
     *
     * @param procedureId   Уникальный ключ таблицы
     * @param args          Список параметров, необходимых для выполнения хранимой процедуры (функции) отчета
     * @param reportsAction экземпляр SimpleReportsAction
     */
    public void getDataSimpleReportsDAOImpl(String procedureId, String[] args, SimpleReportsActionN reportsAction)
            throws SQLException, GenericEntityException, ClassNotFoundException, NoSuchMethodException
            , InvocationTargetException, InstantiationException, IllegalAccessException {
        if (reportsProcedureName == null) getNumberOfSimpleReportsDAOImpl();
        String sql;

        if (args.length == 0 || args[0] == null || "".equals(args[0])) {
            sql = "exec " + reportsProcedureName.get(procedureId);
        } else {
            StringBuilder query = new StringBuilder(" ");
            for (String arg : args) {
                query.append(arg).append(", ");
            }
            sql = "exec " + reportsProcedureName.get(procedureId)
                    + query.substring(0, query.length() - 2);
        }

        Statement statement = getStmt();

        boolean hasResults = statement.execute(sql);
        ResultSet rs;
        do {
            if (hasResults) {
                rs = statement.getResultSet();
                reportsAction.invokeMethod(rs);
                rs.close();
                hasResults = statement.getMoreResults();
            } else {
                if (statement.getUpdateCount() == -1) break;
            }
        } while (hasResults);

        statement.close();
    }

    /**
     * Получение класса для формирования файла эксель, таблица simpleReports столбец ActionClass
     *
     * @param procedureId Уникальный ключ таблицы
     * @return наименование Java класса с пакетом
     */
    public String getActionClassDAOImpl(String procedureId) throws SQLException, GenericEntityException {
        if (reportsActionClasses == null) getNumberOfSimpleReportsDAOImpl();
        return (String) reportsActionClasses.get(procedureId);
    }

    /**
     * Получение наименования хранимой процедуры (функции) отчета, таблица simpleReports столбец ProcedureName
     *
     * @param procedureId Уникальный ключ таблицы
     * @return наименования хранимой процедуры (функции) отчета (с пакетом jira64.dbo)
     */
    public String getProcedureNameDAOImpl(String procedureId) throws SQLException, GenericEntityException {
        if (reportsProcedureName == null) getNumberOfSimpleReportsDAOImpl();
        return (String) reportsProcedureName.get(procedureId);
    }

    /**
     * Получение наименования JavaScript функции отчета, таблица simpleReports столбец JSFunction
     *
     * @param procedureId Уникальный ключ таблицы
     * @return Наименование JavaScript функции отчета
     */
    public String getJSFunctionDAOImpl(String procedureId) throws SQLException, GenericEntityException {
        if (reportsJSFunctions == null) getNumberOfSimpleReportsDAOImpl();
        return (String) reportsJSFunctions.get(procedureId);
    }

    /**
     * Получение списка групп необходимого доступа для каждого отчета, таблица simpleReports столбец GroupsForAccess
     *
     * @return Мэп со списком групп, ключ -  procedureId, значение - (String)
     */
    public Map<String, Object> getGroupsForAccessDAOImpl() throws SQLException, GenericEntityException {
        if (reportsGroupsForAccess == null) getNumberOfSimpleReportsDAOImpl();
        return reportsGroupsForAccess;
    }

    /**
     * Формирование inserta'а для записи данных в БД
     *
     * @param table       Название таблицы для записи в БД
     * @param result      Результирующий набор необходимых строк, которые надо записать в таблицу
     * @param nameColumns Название столбцов (колонок) в БД
     */
    public int writeReportDAOImpl(String table, Object[] result, String[] nameColumns) throws SQLException, GenericEntityException {

        StringBuilder stringBuilder = new StringBuilder("(");

        for (String name : nameColumns) {
            stringBuilder.append(name).append(",");
        }
        String nameColumnsResult = stringBuilder.substring(0, stringBuilder.length() - 1).concat(")");

        String[] lines = new String[result.length];

        Object[] objects;
        for (int resultPos = 0; resultPos < result.length; resultPos++) {
            stringBuilder = new StringBuilder("(");
            objects = (Object[]) result[resultPos];
            for (int objPos = 0; objPos < objects.length; objPos++) {
                stringBuilder.append(objects[objPos].getClass().equals(String.class)
                        ? "'" + objects[objPos] + "'" : objects[objPos]).append(",");
            }
            lines[resultPos] = stringBuilder.substring(0, stringBuilder.length() - 1).concat(")");
        }

        stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(line).append(",");
        }

        String query = stringBuilder.substring(0, stringBuilder.length() - 1);

        int insertResult = ConnectionDB.getSqlProcessor().executeUpdate("INSERT INTO " + table + " "
                + nameColumnsResult + " VALUES " + query);
        getStmt().execute("exec jira64.dbo.SetGeneralDesignSlippage");
        ConnectionDB.getSqlProcessor().close();

        return insertResult;
    }

    int getSize(ResultSet rs) throws SQLException {
        int size;
        rs.last();
        size = rs.getRow();
        rs.beforeFirst();
        return size;
    }
}