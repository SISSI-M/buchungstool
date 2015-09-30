package buchungstool.business.reports;

import org.junit.Before;
import org.junit.Test;

import static buchungstool.business.reports.PausenStrategy.PAUSEN_ABZUG;
import static org.assertj.core.api.Assertions.assertThat;

public class PausenStrategyTest {

    PausenStrategy pausenStrategy;

    @Before
    public void before() {
        pausenStrategy = new PausenStrategy();
    }

    @Test
    public void testPausenAbzugSommer() throws Exception {
        pausenStrategy.sommer();
        assertAbzuege(241);
    }

    @Test
    public void testPausenAbzugWinter() throws Exception {
        assertAbzuege(301);
    }

    private void assertAbzuege(int abzugsGrenze) {
        assertThat(pausenStrategy.pausenAbzug(0)).isEqualTo(0);
        assertThat(pausenStrategy.pausenAbzug(abzugsGrenze)).isEqualTo(PAUSEN_ABZUG);
    }
}