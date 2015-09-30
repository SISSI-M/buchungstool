package buchungstool.business.importer;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.apache.tamaya.ConfigurationProvider.getConfiguration;

/**
 * Created by Alexander Bischof on 30.09.15.
 */
public class SkipEntryProducer {

    public final static List<String> SKIPLIST_DEFAULTS = of(
        getConfiguration().get("skiplist.defaults").split(",")).map(s -> s.replaceAll("\\*", ".*")).collect(toList());

    @Produces @Named("skiplist")
    public List<String> create(EntityManager em) {
        List<String> resultList = em.createQuery("select value from SkipEntry", String.class).getResultList();
        resultList.addAll(SKIPLIST_DEFAULTS);
        return resultList;
    }

}
