package buchungstool.business.importer;

import buchungstool.model.importer.*;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.property.ExDate;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.util.Dates;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static buchungstool.business.importer.IcsPropertyManager.*;
import static buchungstool.business.util.DateConverter.convertTo;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static net.fortuna.ical4j.model.Property.*;
import static net.fortuna.ical4j.model.parameter.Value.DATE;

/**
 * Created by Alexander Bischof on 31.07.15.
 */
public class ComponentEventConverter {

    List<String> skipList;

    EventFactory eventFactory;

    @Inject ImportResult importResult;

    @Inject
    public ComponentEventConverter(@Named("skiplist") List<String> skipList,
                                   EventFactory eventFactory, ImportResult importResult) {
        this.skipList = skipList;
        this.eventFactory = eventFactory;
        this.importResult = importResult;
    }

    public ImportResult convert(File file) throws IcsFileReadException {
        Stream icsCalendarComponentsStream = new IcsFileReader().read(file).
            getComponents().parallelStream();

        //Prefilter auf ICS-Components
        icsCalendarComponentsStream = icsCalendarComponentsStream.filter(
            //Importfilter
            hasProperty(DTSTART).and(hasProperty(SUMMARY))
        );

        // Rules hier noch nicht behandelbar, weil diese sonst in Events umgewandelt werden müssen
        Stream<Component> ruleStream = icsCalendarComponentsStream;
        List<Event> eventList = ruleStream.flatMap(
            c -> hasProperty(RRULE).test(c) ?
                convertRuleComponentToEvent().apply(c).stream() :
                asList(convertComponentToEvent().apply(c)).stream()).collect(toList());

        //Auftrennung in die Listen
        List<AliasEvent> aliasList = eventList.stream().filter(e -> e instanceof AliasEvent).map(
            e -> AliasEvent.class.cast(e)).collect(toList());
        List<KonfigurationEvent> konfigurationList = eventList.stream().filter(e -> e instanceof KonfigurationEvent)
                                                              .map(
                                                                  e -> KonfigurationEvent.class.cast(e))

                                                              .collect(toList());

        Predicate<Event> isNotMetaEvent = e -> !(e instanceof MetaEvent);
        eventList = eventList.stream()//
                             .filter(isNotMetaEvent.and(isNotInSkipList(skipList)))//
                             .collect(toList());

        importResult.setEvents(eventList);
        importResult.setAliasEvents(aliasList);
        importResult.setKonfigurationEvents(konfigurationList);
        return importResult;
    }

    private Predicate<Event> isNotInSkipList(List<String> skipList) {
        return event -> skipList.stream()
                                .noneMatch(
                                    skip -> event.getName().trim()
                                                 .toLowerCase().matches(
                                            skip.toLowerCase()));
    }

    private static Predicate<Component> hasProperty(String property) {
        return component -> component != null && !component.getProperties(property).isEmpty();
    }

    private Function<Component, Event> convertComponentToEvent() {
        return component -> eventFactory.create(getName(component), getStart().apply(component), getEnd().apply(
            component), getDescription(component));
    }

    private Function<Component, List<Event>> convertRuleComponentToEvent() {
        return component -> {

            RRule rrule = (RRule) component.getProperties(RRULE).get(0);
            Recur recur = rrule.getRecur();
            String frequency = recur.getFrequency();
            if (!asList("WEEKLY", "YEARLY", "DAILY").contains(frequency)) {
                throw new RuntimeException(
                    "Serientyp wird nicht unterstützt:" + frequency + " " + component.toString());
            }

            Date rStart = getStartAsIcsDate(component);
            Date rEnd = getEndAsIcsDate(component);
            DateList dates = recur.getDates(rStart, rStart, rEnd, DATE, recur.getCount());

            removeExclusions(component, dates);

            String name = getName(component);
            String description = getDescription(component);

            List<Event> events = (List<Event>) dates.stream().map(date -> {
                //FIXME endzeit richtig??
                LocalDateTime localDateTime = convertTo((java.util.Date) date);
                Event event = eventFactory
                    .create(name, localDateTime, localDateTime, description);
                return event;
            }).collect(toList());

            return events;
        };
    }

    private void removeExclusions(Component component, DateList dates) {
        ofNullable(component.getProperty(EXDATE)).map(e -> (ExDate) e).ifPresent(exdate -> {
            DateList exdates = exdate.getDates();
            for (Object exdateObject : exdates) {
                java.util.Date exdate2 = Dates.getInstance((java.util.Date) exdateObject, DATE);
                dates.remove(exdate2);
            }
        });
    }
}
