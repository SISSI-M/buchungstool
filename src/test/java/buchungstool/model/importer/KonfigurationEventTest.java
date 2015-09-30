package buchungstool.model.importer;

import org.junit.Test;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class KonfigurationEventTest {
    @Test
    public void test() {
        KonfigurationEvent konfigurationEvent = new KonfigurationEvent(now(), now(), "@Konfiguration",
                                                                       "Max:16\nMin: 4");
        assertThat(konfigurationEvent.getMax()).isEqualTo(16);
        assertThat(konfigurationEvent.getMin()).isEqualTo(4);
    }
}