package ru.rshbintech.jira.report.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ofbiz.core.entity.GenericEntityException;
import ru.rshbintech.jira.report.DAO.GetSingleton;
import ru.rshbintech.jira.report.lib.I18n;
import ru.rshbintech.jira.report.lib.PoiExcel;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

/**
 * Основной класс для формирования отчета SimpleReports. Названия столбцов формируются из файла AutomationJiraPlugin
 * где ключ - procedureName + ".columnName (из БД)", Название файла - procedureName + ".fileName",
 * название листа - procedureName + ".sheetName" Список параметров для SQL запроса формируется из таблицы
 * simpleReportsParams - берутся только значение, где в столбце parameterType указано Date или String.
 * Для переопределения логики формирования файла отчета, неоходимо реализовать метод doReport данного класса,
 * с тем же набором параметров, далее новый класс с данным методом необходимо занести в таблицу simpleReports
 * столбец ActionClass по данному отчету.
 */


public class SimpleReportsActionN extends JiraWebActionSupport {
    private final String PROC_ID = "procedureId";
    private final String S_DATE = "startDate";
    private final String E_DATE = "endDate";
    private final String DATE_PATTERN = "dd.MM.yy";
    private String procedureId;
    private String sheetName;
    private String procedureName;
    private XSSFWorkbook wb;
    private MainClassForReports mainClassForReports;
    private Object object;
    private ArrayList<String> paramsArray;
    private String temp;

    @Override
    public String doDefault() throws Exception {
        return super.doDefault();
    }

    @Override
    public String doExecute() throws Exception {

        procedureId = getHttpRequest().getParameter(PROC_ID);
        Map<String, Object> map = GetSingleton.getInstance().getSimpleReportsDAO()
                .getParamsOfSimpleReportsDAOImpl(procedureId);

        String procName = GetSingleton.getInstance().getSimpleReportsDAO().getProcedureNameDAOImpl(procedureId);

        if (procName != null && !"".equals(procName)) {

            procedureName = procName.split("[.]")[2];
            String bookName = I18n.getText(procedureName + ".fileName");
            sheetName = I18n.getText(procedureName + ".sheetName");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            paramsArray = new ArrayList<>();
            for (Map.Entry<String, Object> param : map.entrySet()) {
                switch ((String) param.getValue()) {
                    case ("Date"):
                        if (!(temp = getHttpRequest().getParameter(param.getKey())).equals(""))
                            paramsArray.add("@" + param.getKey() + "='" + temp + "'");
                        LocalDate lc = LocalDate.parse(temp);
                        if ((param.getKey()).equalsIgnoreCase(S_DATE))
                            sheetName = sheetName.concat(" ").concat(lc.format(formatter));
                        if ((param.getKey()).equalsIgnoreCase(E_DATE))
                            sheetName = sheetName.concat("-").concat(lc.format(formatter));
                        break;
                    case ("String"):
                        if (!(temp = getHttpRequest().getParameter(param.getKey())).equals(""))
                            paramsArray.add("@" + param.getKey() + "='" + temp + "'");
                        break;
                }
            }
            wb = new XSSFWorkbook();
            mainClassForReports = new MainClassForReports();
            GetSingleton.getInstance().getSimpleReportsDAO().getDataSimpleReportsDAOImpl(procedureId
                    , paramsArray.toArray(new String[0]), this);
            if (wb.getNumberOfSheets() == 0) wb.createSheet(sheetName);
            PoiExcel.saveClient(wb, bookName, getHttpResponse());
        }
        //return super.doExecute();
        return "reports-action-success";
    }

    public void invokeMethod(ResultSet rs)
            throws SQLException, GenericEntityException, ClassNotFoundException, NoSuchMethodException
            , IllegalAccessException, InvocationTargetException, InstantiationException {
        String actionClass = GetSingleton.getInstance().getSimpleReportsDAO().getActionClassDAOImpl(procedureId);

        if (this.getClass().getName().equalsIgnoreCase(actionClass) || actionClass == null) {
            doReport(rs, wb, sheetName, procedureName);
        } else {
            if (object == null) object = Class.forName(actionClass).getConstructor().newInstance();
            object.getClass().getDeclaredMethod("doReport"
                    , ResultSet.class, XSSFWorkbook.class, String.class, String.class)
                    .invoke(object, rs, wb, sheetName, procedureName);
        }
    }

    /**
     * Основной метод для формирования файла эксель на основании ResultSet"а
     *
     * @param rs            ResultSet
     * @param wb            Книга XSSFWorkbook
     * @param sheetName     название листа экселя
     * @param procedureName наименования хранимой процедуры (функции) отчета (без пакета jira64.dbo)
     * @return
     * @throws SQLException
     */
    public Sheet doReport(ResultSet rs, XSSFWorkbook wb, String sheetName, String procedureName) throws SQLException {
        Sheet sheet;

        if (wb.getNumberOfSheets() == 0) {
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.getSheetAt(0);
        }

        int begin = sheet.getLastRowNum() == 0 ? 0 : sheet.getLastRowNum() + 3;
        String[] metaData = mainClassForReports.getMetaData(rs);

        mainClassForReports.writeCellSimpleReport(sheet, begin + 1, rs, metaData);

        mainClassForReports.titleSimpleReports(sheet, begin, metaData, procedureName
                , new StylesWorkbook().initStyle(sheet.getWorkbook(), "cs_Data_String10HCVCC"));

        mainClassForReports.setColumnWidth(sheet, metaData.length);

        return sheet;
    }
}