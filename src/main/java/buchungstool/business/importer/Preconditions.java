package buchungstool.business.importer;

import buchungstool.model.importer.Event;

import java.util.function.Predicate;

import static java.time.LocalTime.of;

/**
 * Created by Alexander Bischof on 20.08.15.
 */
public class Preconditions {
    public static Predicate<Event> hasValidStartTime() {
        return event -> event.getStartDate().toLocalTime().isAfter(of(8, 29));
    }

    public static Predicate<Event> hasValidEndTime() {
        return event -> event.getEndDate().toLocalTime().isBefore(of(16, 1));
    }
}
