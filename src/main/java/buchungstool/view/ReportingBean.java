package buchungstool.view;

import buchungstool.business.importer.ComponentEventConverter;
import buchungstool.business.importer.IcsFileReadException;
import buchungstool.business.reports.Benutzerreport;
import buchungstool.model.importer.ImportResult;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ManagedBean
@SessionScoped
public class ReportingBean {

    @Inject Benutzerreport benutzerreport;

    public java.util.List<buchungstool.model.reports.ReportEntry> create(){
        return benutzerreport.build().getAccumulatedReportEntryList();
    }
}  