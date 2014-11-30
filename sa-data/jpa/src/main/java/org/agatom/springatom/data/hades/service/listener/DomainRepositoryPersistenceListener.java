package org.agatom.springatom.data.hades.service.listener;

import org.agatom.springatom.data.event.PersistenceEvent;
import org.agatom.springatom.data.event.PersistenceEventListenerAdapter;
import org.springframework.context.ApplicationListener;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface DomainRepositoryPersistenceListener
        extends ApplicationListener<PersistenceEvent> {
    void setListeners(Collection<PersistenceEventListenerAdapter<?>> applicationListeners);
}
