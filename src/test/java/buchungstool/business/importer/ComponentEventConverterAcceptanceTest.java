package buchungstool.business.importer;

import buchungstool.model.importer.EventFactory;
import buchungstool.model.importer.ImportResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static buchungstool.business.importer.SkipEntryProducer.SKIPLIST_DEFAULTS;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ComponentEventConverterAcceptanceTest {

    @Parameter public String filename;
    @Parameter(1) public int expectedEvents;
    private ComponentEventConverter componentEventConverter;

    @Parameters
    public static Object[][] data() {
        return new Object[][] {
            { "/20150225.ics", 224 },
            { "/20150720.ics", 393 },
        };
    }

    @Before
    public void before() {
        componentEventConverter = new ComponentEventConverter(SKIPLIST_DEFAULTS,
                                                              new EventFactory(), new ImportResult());
    }

    @Test
    public void testParseICal() throws Exception {

        File file = new File(IcsFileReader.class.getResource(filename).toURI());

        ImportResult importResult = componentEventConverter.convert(file);
        importResult.getEvents().forEach(System.out::println);
        assertThat(importResult.getEvents().size()).isEqualTo(expectedEvents);
    }
}