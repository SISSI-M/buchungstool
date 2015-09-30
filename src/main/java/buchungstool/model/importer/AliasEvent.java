package buchungstool.model.importer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static buchungstool.business.util.DateConverter.convertTo;
import static java.util.stream.Stream.of;

/**
 * Created by Alexander Bischof on 25.02.15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AliasEvent extends Event implements MetaEvent {
    private String[] aliases;

    public AliasEvent() {
    }

    public AliasEvent(LocalDateTime startDate, LocalDateTime endDate, String name, String description) {
        super(startDate, endDate, name.replace("@Alias:", "").trim());
        this.aliases = of(description.split("\\,")).map(s->s.trim()).toArray(String[]::new);
    }

    public String[] getAliases() {
        return aliases;
    }

    @Override public String toString() {
        return "AliasEvent{" +
               "startDate=" + getStartDate() +
               ", endDate=" + getEndDate() +
               ", name='" + getName() + '\'' +
               ", alias='" + Arrays.toString(aliases) + '\'' +
               '}';
    }
}
