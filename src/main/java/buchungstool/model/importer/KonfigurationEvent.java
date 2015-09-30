package buchungstool.model.importer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

import static java.util.stream.Stream.of;

/**
 * Created by Alexander Bischof on 25.02.15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class KonfigurationEvent extends Event implements MetaEvent {
    private int min;
    private int max;

    public KonfigurationEvent() {
    }

    public KonfigurationEvent(LocalDateTime startDate, LocalDateTime endDate, String name, String description) {
        super(startDate, endDate, name);

        String[] split = description.split("\n");

        //TODO default aus DB lesen
        //Logging!!!
        this.max = find("Max:", split).orElse(8);
        this.min = find("Min:", split).orElse(8);
    }

    private static java.util.Optional<Integer> find(String filter, String[] split) {
        return of(split).filter(s -> s.startsWith(filter))
                        .map(s -> s.replace(filter, "").trim()).map(Integer::valueOf).findFirst();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override public String toString() {
        return "KonfigurationEvent{" +
               "startDate=" + getStartDate() +
               ", endDate=" + getEndDate() +
               ", name='" + getName() + '\'' +
               "min=" + min +
               ", max=" + max +
               '}';
    }
}
