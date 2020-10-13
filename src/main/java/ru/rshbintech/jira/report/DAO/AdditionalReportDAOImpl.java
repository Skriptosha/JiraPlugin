package ru.rshbintech.jira.report.DAO;

import org.ofbiz.core.entity.GenericEntityException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.*;


public class AdditionalReportDAOImpl extends SimpleReportsDAOImpl {
    private Map<String, Object> mainTable;
    private Map<String, Object> tempTable;
    private String tempQuery;

    public Map<String, Object> getSelectResult(String type, String param) throws Exception {

        return createMap(replace(AdditionalReportEnum.valueOf(type.toUpperCase()).getSql(), param));
    }

    private Map<String, Object> createMap(String sql) throws SQLException, GenericEntityException {
        ResultSet rs = getStmt().executeQuery(sql);
        Map<String, Object> mainTable = new LinkedHashMap<>(getSize(rs));
        while (rs.next()) {
            mainTable.put(rs.getString(1), rs.getString(2));
        }
        rsClose(rs);
        return mainTable;
    }

    public String replace(String phrase, String replacement) {
        return phrase.replace("%d", replacement);
    }

    private void refreshTable(String sql) throws SQLException, GenericEntityException {
        ResultSet rs = getStmt().executeQuery(sql);
        mainTable = new HashMap<>(getSize(rs));
        while (rs.next()) {
            mainTable.put(rs.getString(1), rs.getString(2));
        }
        mainTable = mainTable.entrySet().parallelStream()
                .collect(toConcurrentMap(Map.Entry::getKey, o -> o.getValue().toString().toLowerCase()));
        rsClose(rs);
    }

    /**
     * Заполнение данных Мапы данными нужного SQL запроса. Запрос должен возвращать 2 столбца - 1 столбец должен быть
     * уникальным (иначе значения будут перетираться). С фронта необходимо вызывать функцию initAutocomplete с нужными
     * параметрами, urlRefreshParam в шаблоне simpleReports.js = String type
     *
     * @param type тип параметра
     * @return true
     */
    public boolean refresh(String type) throws SQLException, GenericEntityException {
        refreshTable(AdditionalReportEnum.valueOf(type.toUpperCase()).getSql());
        return true;
    }

    public Map<String, Object> getAUISelect2result(String query) {
        Map<String, Object> resultTable;
        String finalQuery = query.replaceAll("[\"']", "").toLowerCase();
        if (tempQuery != null && query.contains(tempQuery)) {

            resultTable = tempTable.entrySet().parallelStream()
                    .filter(o -> o.getValue().toString().contains(finalQuery))
                    .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
            /*
            for (Map.Entry<String, Object> map : tempTable.entrySet()) {
                if (map.getValue().toString().toLowerCase().contains(query)) {
                    resultTable.put(map.getKey(), map.getValue());
                }
            }
             */
        } else {

            resultTable = mainTable.entrySet().parallelStream()
                    .filter(o -> o.getValue().toString().contains(finalQuery))
                    .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));

/*
            for (Map.Entry<String, Object> map : mainTable.entrySet()) {
                if (map.getValue().toString().toLowerCase().contains(query)) {
                    resultTable.put(map.getKey(), map.getValue());
                }
            }
 */
        }
        tempQuery = query;
        tempTable = resultTable;
        return resultTable;
    }
}
