package buchungstool.business.importer;

import buchungstool.model.importer.Event;
import org.junit.Test;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

import static buchungstool.business.importer.Preconditions.hasValidEndTime;
import static buchungstool.business.importer.Preconditions.hasValidStartTime;
import static java.time.LocalTime.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Calendar.getInstance;
import static org.assertj.core.api.Assertions.assertThat;

public class PreconditionsTest {

    @Test
    public void testHasValidStartTime() throws Exception {
        LocalDateTime cal = createDate(9, 0);
        assertThat(hasValidStartTime().test(new Event(cal, cal, "kshdk"))).isTrue();

        cal = createDate(8, 30);
        assertThat(hasValidStartTime().test(new Event(cal, cal, "kshdk"))).isTrue();
    }

    @Test
    public void testHasNotValidStartTime() throws Exception {
        LocalDateTime cal = createDate(8, 0);
        assertThat(hasValidStartTime().test(new Event(cal, cal, "kshdk"))).isFalse();
    }

    @Test
    public void testHasValidEndTime() throws Exception {
        LocalDateTime cal = createDate(15, 30);
        assertThat(hasValidEndTime().test(new Event(cal, cal, "kshdk"))).isTrue();

        cal = createDate(16, 00);
        assertThat(hasValidEndTime().test(new Event(cal, cal, "kshdk"))).isTrue();
    }

    @Test
    public void testHasNotValidEndTime() throws Exception {
        LocalDateTime cal = createDate(16, 1);
        assertThat(hasValidEndTime().test(new Event(cal, cal, "kshdk"))).isFalse();
    }

    private LocalDateTime createDate(int hour, int minute) {
        LocalDateTime now = LocalDateTime.of(LocalDate.now(), of(hour, minute));
        now.atZone(systemDefault());
        return now;
    }
}