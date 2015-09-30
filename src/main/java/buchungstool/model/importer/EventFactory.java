package buchungstool.model.importer;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

/**
 * Created by Alexander Bischof on 01.09.15.
 */
@ApplicationScoped
public class EventFactory {

    public Event create(String name, LocalDateTime startDate, LocalDateTime endDate, String description) {
        if (name.startsWith("@")) {
            return createMetaEvent(name, startDate, endDate, description);
        }
        return new Event(startDate, endDate, name
            .replaceAll(" HvD","")
            .trim());
    }

    private Event createMetaEvent(String name, LocalDateTime startDate, LocalDateTime endDate,
                                  String description) {
        String metadatenName = name.substring(1);

        switch (metadatenName.split(":")[0].toLowerCase()) {
        case "alias":
            return new AliasEvent(startDate, endDate, name, description);
        case "konfiguration":
            return new KonfigurationEvent(startDate, endDate, name, description);
        default:
            throw new RuntimeException("MetaEvent for : " + metadatenName + " not known.");
        }
    }
}
