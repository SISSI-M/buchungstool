package buchungstool.business;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class UserDatabaseEntityManager {

    @Produces
    @PersistenceContext
    private EntityManager em;
}