package buchungstool.business.reports;

import buchungstool.model.importer.Event;
import buchungstool.model.importer.ImportResult;
import buchungstool.model.reports.Report;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

/**
 * Created by Alexander Bischof on 25.09.15.
 */
public class Benutzerreport {

    private final Map<String, String> aliasMap;
    private List<Event> eventList;
    private PausenStrategy pausenStrategy = new PausenStrategy();

    @Inject
    public Benutzerreport(ImportResult importResult) {
        requireNonNull(importResult);

        //Aufbau von alias liste
        aliasMap = new HashMap<>();
        importResult.getAliasEvents().forEach(aliasEvent -> {
            of(aliasEvent.getAliases()).forEach(entry -> aliasMap.put(entry.toLowerCase(), aliasEvent.getName()));
        });

        //AliasMapping
        this.eventList = importResult.getEvents().stream()//
            .map(e -> new Event(e.getStartDate(), e.getEndDate(),
                                aliasMap.getOrDefault(e.getName().toLowerCase().trim(), e.getName()))).collect(toList());
    }

    //TODO Regelung offen
    public Benutzerreport sommer() {
        pausenStrategy.sommer();
        return this;
    }

    public static Predicate<Event> filterByName(String name) {
        return e -> e.getName().equalsIgnoreCase(name);
    }

    public static Predicate<Event> filterFrom(LocalDateTime fromDate) {
        return e -> e.getStartDate().isAfter(fromDate);
    }

    public static Predicate<Event> filterTo(LocalDateTime toDate) {
        return e -> e.getStartDate().isBefore(toDate);
    }

    public Benutzerreport filter(Predicate<Event> predicate) {
        this.eventList = eventList.stream().filter(predicate).collect(toList());
        return this;
    }

    public Report build() {

        final Report report = new Report();
        eventList.stream().forEach(event -> {
            long diffBruttoInMinutes = ChronoUnit.MINUTES.between(event.getStartDate(), event.getEndDate());
            long diffNettoInMinutes = diffBruttoInMinutes - pausenStrategy.pausenAbzug(diffBruttoInMinutes);

            report.add(event.getName(), diffNettoInMinutes, diffBruttoInMinutes);
        });
        return report;
    }
}
