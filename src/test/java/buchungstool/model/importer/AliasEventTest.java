package buchungstool.model.importer;

import org.junit.Test;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class AliasEventTest {
    @Test
    public void test() {
        AliasEvent aliasEvent = new AliasEvent(now(), now(), "@Alias:Alexander Bischof", "Ali, Alex, Alex B.");
        assertThat(aliasEvent.getName()).isEqualTo("Alexander Bischof");
        assertThat(aliasEvent.getAliases()).containsExactly("Ali", "Alex", "Alex B.");
    }
}