package ru.rshbintech.jira.report.lib;

import org.ofbiz.core.entity.GenericEntityException;
import org.ofbiz.core.entity.jdbc.SQLProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDB {
    private static ConnectionDB ourInstance = new ConnectionDB();
    private static SQLProcessor sqlProcessor;

    public static synchronized ConnectionDB getInstance() {
        return ourInstance;
    }

    private ConnectionDB() {
        sqlProcessor = initSQLProcessor();
    }

    private static synchronized SQLProcessor initSQLProcessor() {
        return new org.ofbiz.core.entity.jdbc.SQLProcessor("defaultDS");
    }

    public static synchronized SQLProcessor getSqlProcessor() {
        if (sqlProcessor != null && !testConnect()) {
            sqlProcessor = initSQLProcessor();
        }
        return sqlProcessor;
    }

    private static boolean testConnect() {
        try {
            ResultSet rs = sqlProcessor.executeQuery("select 1 as conn ");
            rs.next();
            return true;
        } catch (GenericEntityException | SQLException e) {
            return false;
        }
    }
}
