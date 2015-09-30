package buchungstool.business.importer;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.property.Summary;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

import static buchungstool.business.util.DateConverter.convertTo;
import static java.util.Optional.ofNullable;
import static net.fortuna.ical4j.model.Property.*;

/**
 * Created by Alexander Bischof on 01.09.15.
 */
class IcsPropertyManager {
    public static Function<Component, String> getName() {
        return component -> getName(component);
    }

    public static String getName(Component component) {
        return ((Summary) component.getProperties(SUMMARY).get(0)).getValue();
    }

    public static String getDescription(Component component) {
        Property property = component.getProperty(DESCRIPTION);
        String ret = null;
        if (property != null) {
            ret = property.getValue().trim();
        }
        return ret;
    }

    public static Function<Component, LocalDateTime> getStart() {
        return component -> convertTo(getStartAsIcsDate(component));
    }

    public static Date getStartAsIcsDate(Component component) {
        return ((DateProperty) component.getProperty(DTSTART)).getDate();
    }

    public static Function<Component, LocalDateTime> getEnd() {
        return component -> convertTo(getEndAsIcsDate(component));
    }

    public static Date getEndAsIcsDate(Component component) {
        return ((DateProperty) component.getProperty(DTEND)).getDate();
    }
}
