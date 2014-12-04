package org.agatom.springatom.data.hades.service;

import org.agatom.springatom.data.event.PersistenceEventListenerAdapter;
import org.agatom.springatom.data.hades.JpaModelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Persistable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@ActiveProfiles(value = {"test"})
@ContextHierarchy(value = {
        @ContextConfiguration(classes = BaseJpaIntegrationTestConfig.class),
        @ContextConfiguration(classes = JpaModelConfiguration.class)
})
abstract public class BaseJpaIntegrationTest {

    @Autowired
    protected ApplicationContext applicationContext = null;

    protected static PersistenceEventListenerAdapter<Persistable<Long>> acceptAllListener() {
        return new PersistenceEventListenerAdapter<Persistable<Long>>() {
            @Override
            public boolean canAccept(final Object object) {
                return true;
            }
        };
    }

    protected static PersistenceEventListenerAdapter<Persistable<Long>> acceptNoneListener() {
        return new PersistenceEventListenerAdapter<Persistable<Long>>() {
            @Override
            public boolean canAccept(final Object object) {
                return false;
            }
        };
    }

}
