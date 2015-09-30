package buchungstool.model.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

/**
 * Created by Alexander Bischof on 25.09.15.
 */
public class Report {
    private List<ReportEntry> reportEntryList = new ArrayList<>();

    public void add(String name, double sumNetto, double sumBrutto) {
        reportEntryList.add(new ReportEntry(name, sumNetto, sumBrutto));
    }

    public List<ReportEntry> getAccumulatedReportEntryList() {
                Map<String, List<ReportEntry>> groupByName = reportEntryList.stream().collect(
                    groupingBy(ReportEntry::getUsername, toList()));
        
                return groupByName.entrySet().stream().map(
                    e -> new ReportEntry(e.getKey(),
                                         accumulate(e.getValue(), ReportEntry::getSumNetto),
                                         accumulate(e.getValue(), ReportEntry::getSumBrutto)
                    )).collect(toList());
    }

    private static double accumulate(List<ReportEntry> reportEntryList, ToDoubleFunction<ReportEntry> sum) {
        return reportEntryList.stream().mapToDouble(sum).sum()/60;
    }
}
