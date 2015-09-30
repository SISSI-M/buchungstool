package buchungstool.business.reports;

import buchungstool.business.importer.ComponentEventConverter;
import buchungstool.business.importer.IcsFileReadException;
import buchungstool.business.importer.IcsFileReader;
import buchungstool.model.importer.EventFactory;
import buchungstool.model.importer.ImportResult;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Collections;

import static buchungstool.business.importer.SkipEntryProducer.SKIPLIST_DEFAULTS;

/**
 * Created by Alexander Bischof on 25.09.15.
 */
public class BenutzerreportIT {

    @Test
    public void test() throws URISyntaxException, IcsFileReadException {
      //  String filename = "/20150720.ics";
        String filename = "/metatest.ics";
        File file = new File(IcsFileReader.class.getResource(filename).toURI());
        ImportResult importResult = new ComponentEventConverter(SKIPLIST_DEFAULTS,
                                                            new EventFactory(), new ImportResult()).convert(file);
        Benutzerreport benutzerreport = new Benutzerreport(importResult);
        benutzerreport.build().getAccumulatedReportEntryList().forEach(
            System.out::println);
    }
}
