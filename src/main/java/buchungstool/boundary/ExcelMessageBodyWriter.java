package buchungstool.boundary;

import buchungstool.model.reports.ReportEntry;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@Provider
@Produces("text/excel")
public class ExcelMessageBodyWriter implements MessageBodyWriter<List<ReportEntry>> {
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return List.class.isAssignableFrom(type);
    }

    @Override public long getSize(List<ReportEntry> reportEntries, Class<?> type, Type genericType,
                                  Annotation[] annotations, MediaType mediaType) {
        return reportEntries.size();
    }

    @Override public void writeTo(List<ReportEntry> reportEntries, Class<?> type, Type genericType,
                                  Annotation[] annotations, MediaType mediaType,
                                  MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
        throws IOException, WebApplicationException {
        HSSFWorkbook hwb = new HSSFWorkbook();
        Response.ResponseBuilder response = null;
        try {
            HSSFSheet sheet = hwb.createSheet("new sheet");

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("Name");
            rowhead.createCell(1).setCellValue("Netto");
            rowhead.createCell(2).setCellValue("Brutto");

            short rowNum = 1;
            for (ReportEntry entry : reportEntries) {
                HSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getUsername());
                row.createCell(1).setCellValue(entry.getSumNetto());
                row.createCell(2).setCellValue(entry.getSumBrutto());
            }

            hwb.write(entityStream);
        } catch (Exception e) {

        }
    }
}