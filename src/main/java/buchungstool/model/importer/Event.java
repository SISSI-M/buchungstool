package buchungstool.model.importer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.function.Function;

import static buchungstool.business.util.DateConverter.convertTo;

/**
 * Created by Alexander Bischof on 25.02.15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Event {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;

    public Event() {
    }

    public Event(LocalDateTime startDate, LocalDateTime endDate, String name) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (endDate != null ? !endDate.equals(event.endDate) : event.endDate != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (startDate != null ? !startDate.equals(event.startDate) : event.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }

    @Override public String toString() {
        return "Event{" +
               "startDate=" + startDate +
               ", endDate=" + endDate +
               ", name='" + name + '\'' +
               '}';
    }
}
