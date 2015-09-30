package buchungstool.model.importer;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Bischof on 25.02.15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SessionScoped
@Named
public class ImportResult implements Serializable {

    @XmlElement
    private List<Event> events;

    @XmlElement
    private List<AliasEvent> aliasEvents;

    @XmlElement
    private List<KonfigurationEvent> konfigurationEvents;
    private String fileName;

    public ImportResult() {
        this.events = new ArrayList<>();
        this.aliasEvents = new ArrayList<>();
        this.konfigurationEvents = new ArrayList<>();
    }

    public ImportResult(List<Event> events, List<AliasEvent> aliasEvents,
                        List<KonfigurationEvent> konfigurationEvents) {
        this.events = events;
        this.aliasEvents = aliasEvents;
        this.konfigurationEvents = konfigurationEvents;
    }

    public List<AliasEvent> getAliasEvents() {
        return aliasEvents;
    }

    public void setAliasEvents(List<AliasEvent> aliasEvents) {
        this.aliasEvents = aliasEvents;
    }

    public List<KonfigurationEvent> getKonfigurationEvents() {
        return konfigurationEvents;
    }

    public void setKonfigurationEvents(List<KonfigurationEvent> konfigurationEvents) {
        this.konfigurationEvents = konfigurationEvents;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
