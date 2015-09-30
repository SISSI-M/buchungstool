package buchungstool.boundary;

import buchungstool.business.reports.Benutzerreport;
import buchungstool.model.importer.ImportResult;
import buchungstool.model.reports.Report;
import buchungstool.model.reports.ReportEntry;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Alexander Bischof on 24.07.15.
 */
@Path("benutzerreport")
public class BenutzerReportEndpoint {

    @Inject
    @SessionScoped
    ImportResult importResult;

    @Inject Benutzerreport benutzerreport;

    @GET
    @Produces("text/csv")
    public Response produceCsv() {
        Report report = benutzerreport.sommer().build();
        List<ReportEntry> accumulatedReportEntryList = report.getAccumulatedReportEntryList();
        return Response.ok(accumulatedReportEntryList).build();
    }

    @GET
    @Path("excel")
    @Produces("text/excel")
    public Response produceExcel() {
        Report report = benutzerreport.sommer().build();
        List<ReportEntry> accumulatedReportEntryList = report.getAccumulatedReportEntryList();
        Response response = Response.ok(accumulatedReportEntryList)
                                    .header("Content-disposition", "attachment; filename=file.xls").build();
        return response;

    }
}
