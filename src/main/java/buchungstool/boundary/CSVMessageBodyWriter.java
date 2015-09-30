package buchungstool.boundary;

import buchungstool.model.reports.ReportEntry;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@Provider
@Produces("text/csv")
public class CSVMessageBodyWriter implements MessageBodyWriter<List<ReportEntry>> {
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
        StringBuilder sb = new StringBuilder();
        reportEntries.forEach(
            e -> sb.append(String.format("%s;%s;%s\n", e.getUsername(), e.getSumNetto(), e.getSumBrutto())));
        entityStream.write(sb.toString().getBytes());
    }
}