package buchungstool.model.reports;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Created by Alexander Bischof on 25.09.15.
 */
public class ReportEntry {
    private String username;
    private double sumNetto;
    private double sumBrutto;

    public ReportEntry(String username, double sumNetto, double sumBrutto) {
        requireNonNull(username);
        this.username = username;
        this.sumNetto = sumNetto;
        this.sumBrutto = sumBrutto;
    }

    public String getUsername() {
        return username;
    }

    public double getSumNetto() {
        return sumNetto;
    }

    public double getSumBrutto() {
        return sumBrutto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportEntry that = (ReportEntry) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override public String toString() {
        return "ReportEntry{" +
               "username='" + username + '\'' +
               ", sumNetto=" + sumNetto +
               ", sumBrutto=" + sumBrutto +
               '}';
    }

    public ReportEntry mergeEntry(ReportEntry e2) {
        this.sumNetto += e2.getSumNetto();
        this.sumBrutto += e2.getSumBrutto();
        return this;
    }
}
