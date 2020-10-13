package ru.rshbintech.jira.report.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.ofbiz.core.entity.GenericEntityException;
import ru.rshbintech.jira.report.DAO.GetSingleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("reportresource")
@Produces({MediaType.APPLICATION_JSON})
public class AdditionalReportResource {
    private final ObjectMapper mapper = new ObjectMapper();

    @GET
    @Path("/getSelectResult")
    public Response selectResult(@QueryParam("type") String query, @QueryParam("param") String param)
            throws Exception {
        return Response.ok(mapper.valueToTree(GetSingleton.getInstance().getAdditionalReportDAO()
                .getSelectResult(query, param))).build();
    }

    @GET
    @Path("/getAUISelect2result")
    public Response AUISelect2result(@QueryParam("query") String query) {
        return Response.ok(mapper.valueToTree(GetSingleton.getInstance().getAdditionalReportDAO()
                .getAUISelect2result(query))).build();
    }

    @GET
    @Path("/refresh")
    public Response refresh(@QueryParam("query") String type) throws SQLException, GenericEntityException {
        return Response.ok(GetSingleton.getInstance().getAdditionalReportDAO().refresh(type)).build();
    }

}
