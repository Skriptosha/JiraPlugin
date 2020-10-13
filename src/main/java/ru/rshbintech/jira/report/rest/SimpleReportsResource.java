package ru.rshbintech.jira.report.rest;

import com.atlassian.plugins.rest.common.multipart.FilePart;
import com.atlassian.plugins.rest.common.multipart.MultipartFormParam;
import org.codehaus.jackson.map.ObjectMapper;
import ru.rshbintech.jira.report.DAO.GetSingleton;
import ru.rshbintech.jira.report.lib.I18n;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("jirareports")
@Produces({MediaType.APPLICATION_JSON})
public class SimpleReportsResource {
    private final ObjectMapper mapper = new ObjectMapper();

    @GET
    @Path("/report")
    public Response getReports() throws Exception {
        return Response.ok(mapper.valueToTree(GetSingleton.getInstance().getSimpleReportsDAO()
                .getNumberOfSimpleReportsDAOImpl())).build();
    }

    @GET
    @Path("/param")
    public Response getParams(@QueryParam("param") String id) throws Exception {
        return Response.ok(mapper.valueToTree(GetSingleton.getInstance().getSimpleReportsDAO()
                .getParamsOfSimpleReportsDAOImpl(id))).build();
    }

    @GET
    @Path("/access")
    public Response getAccess() throws Exception {
        return Response.ok(mapper.valueToTree(GetSingleton.getInstance().getSimpleReportsDAO()
                .getGroupsForAccessDAOImpl())).build();
    }

    @GET
    @Path("/js")
    public Response getJS(@QueryParam("param") String id) throws Exception {
        return Response.ok(mapper.valueToTree(GetSingleton.getInstance().getSimpleReportsDAO()
                .getJSFunctionDAOImpl(id))).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadReport(@MultipartFormParam("file") FilePart filePart) throws Exception {
        if (filePart.getName().endsWith(".xlsm") || filePart.getName().endsWith(".xlsx")) {
            String[] numbers = I18n.getText("tempTableColumns").split(",");
            return Response.ok(GetSingleton.getInstance().getReadFileExcel()
                    .writeMapToDB(GetSingleton.getInstance().getReadFileExcel().readFile(filePart.getInputStream())
                            , I18n.getText("tempTableSheetName")
                            , numbers)).build();
        } else return Response.notModified("Wrong file format!").build();
    }
}