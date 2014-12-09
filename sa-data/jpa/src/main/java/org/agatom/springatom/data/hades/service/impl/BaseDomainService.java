package org.agatom.springatom.data.hades.service.impl;

import com.google.common.collect.Sets;
import org.agatom.springatom.data.event.PersistenceEventListenerAdapter;
import org.agatom.springatom.data.hades.service.listener.DomainRepositoryPersistenceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-18</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class BaseDomainService {
    private static final Logger                              LOGGER           = LoggerFactory.getLogger(BaseDomainService.class);
    @Autowired
    protected            MessageSource                       messageSource    = null;
    @Autowired
    private              DomainRepositoryPersistenceListener listenerDelegate = null;

    @PostConstruct
    protected void init() {
        this.listenerDelegate.setListeners(this.registerListeners());
    }

    private Collection<PersistenceEventListenerAdapter<?>> registerListeners() {
        final ListenerAppender listenerAppender = new ListenerAppender();
        this.registerListeners(listenerAppender);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s defined %d listeners", ClassUtils.getUserClass(this), listenerAppender.listeners.size()));
            for (final PersistenceEventListenerAdapter<?> listener : listenerAppender.listeners) {
                LOGGER.trace(listener.toString());
            }
        }
        return listenerAppender.listeners;
    }

    protected void registerListeners(final ListenerAppender listenerAppender) {
    }

    protected static class ListenerAppender {
        private Collection<PersistenceEventListenerAdapter<?>> listeners = Sets.newHashSet();

        public ListenerAppender add(final PersistenceEventListenerAdapter<?> listener) {
            this.listeners.add(listener);
            return this;
        }
    }

}
