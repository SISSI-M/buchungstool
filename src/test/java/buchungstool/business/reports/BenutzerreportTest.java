package buchungstool.business.reports;

import buchungstool.model.importer.AliasEvent;
import buchungstool.model.importer.Event;
import buchungstool.model.importer.ImportResult;
import buchungstool.model.reports.ReportEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static buchungstool.business.reports.Benutzerreport.filterByName;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class BenutzerreportTest {

    private Benutzerreport benutzerreport;
    private ImportResult importResult;

    @Before
    public void before() {
        importResult = new ImportResult();
    }

    @Test
    public void testBuild() throws Exception {
        List<Event> events = asList(//
                                    new Event(now(), now().plusHours(1).plusMinutes(30), "alex"),
                                    new Event(now(), now().plusHours(1), "alex2"),
                                    new Event(now(), now().plusHours(1), "alex3")
        );
        importResult.setEvents(events);

        benutzerreport = new Benutzerreport(importResult);
        ReportEntry actualAlex = benutzerreport.filter(filterByName("alex")).build().getAccumulatedReportEntryList()
                                               .get(
                                                   0);
        assertThat(actualAlex.getSumBrutto()).isEqualTo(1.5);
    }

    @Test
    public void testAliasMapping() {
        List<Event> events = asList(//
                                    new Event(now(), now().plusHours(1).plusMinutes(30), "alex"),
                                    new Event(now(), now().plusHours(1), "albbbb")
        );
        importResult.setEvents(events);
        importResult.setAliasEvents(asList(new AliasEvent(now(), now(), "@Alias:alex", "albbbb")));

        benutzerreport = new Benutzerreport(importResult);
        List<ReportEntry> accumulatedReportEntryList = benutzerreport.filter(filterByName("alex"))
                                                                                     .build()
                                                                                     .getAccumulatedReportEntryList();
        accumulatedReportEntryList.forEach(System.out::println);
        assertThat(accumulatedReportEntryList).hasSize(1);
        assertThat(accumulatedReportEntryList.get(0).getSumBrutto()).isEqualTo(2.5);
    }
}