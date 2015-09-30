package buchungstool.business.importer;

import buchungstool.model.importer.*;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static buchungstool.business.importer.SkipEntryProducer.SKIPLIST_DEFAULTS;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ComponentEventConverterMetadatenTest {

    private ComponentEventConverter componentEventConverter;

    @Before
    public void before() {
        componentEventConverter = new ComponentEventConverter(SKIPLIST_DEFAULTS,
                                                              new EventFactory(), new ImportResult());
    }

    @Test
    public void testParseICal() throws Exception {

        File file = new File(IcsFileReader.class.getResource("/metatest.ics").toURI());

        ImportResult importResult = componentEventConverter.convert(file);

        assertThat(importResult.getEvents().stream()
                               //.peek(System.out::println)
                               .noneMatch(e -> e instanceof MetaEvent)).isTrue();

        assertThat(importResult.getAliasEvents().stream()
                              // .peek(System.out::println)
                               .collect(
                                   toList())).hasSize(3);
        assertThat(importResult.getKonfigurationEvents().stream()
            // .peek(System.out::println)
                       .collect(
                           toList())).hasSize(1);

    }
}