package org.agatom.springatom.data.hades.service.listener;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.agatom.springatom.data.event.PersistenceEvent;
import org.agatom.springatom.data.event.PersistenceEventListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-16</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Role(BeanDefinition.ROLE_SUPPORT)
@Scope(BeanDefinition.SCOPE_SINGLETON)
class DomainRepositoryPersistenceListenerImpl
        implements DomainRepositoryPersistenceListener {
    private static final Logger                                         LOGGER    = LoggerFactory.getLogger(DomainRepositoryPersistenceListenerImpl.class);
    private              Collection<PersistenceEventListenerAdapter<?>> listeners = null;

    @Override
    public void onApplicationEvent(final PersistenceEvent event) {
        if (CollectionUtils.isEmpty(this.listeners)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("No listeners registered for this instance, exiting..");
            }
            return;
        }
        final Stopwatch stopwatch = Stopwatch.createStarted();
        for (PersistenceEventListenerAdapter<?> listener : this.listeners) {
            if (listener.canAccept(event.getSource())) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("%s accepts %s", ClassUtils.getUserClass(listener), event));
                }
                try {
                    listener.onApplicationEvent(event);
                } catch (Exception error) {
                    LOGGER.error(String.format("%s from %s threw an error", ClassUtils.getShortName(event.getClass()), listener));
                    throw error;
                }
            }
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Reacting on %s took %d ms", event, stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)));
        }
    }

    @Override
    public void setListeners(final Collection<PersistenceEventListenerAdapter<?>> applicationListeners) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList(applicationListeners);
        } else {
            this.listeners.addAll(applicationListeners);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Registered %d new listeners", applicationListeners.size()));
        }
    }
}
