/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.data.hades.repo.factory;

import com.google.common.collect.FluentIterable;
import com.mysema.query.DefaultQueryMetadata;
import com.mysema.query.jpa.impl.JPAQuery;
import org.agatom.springatom.data.event.after.*;
import org.agatom.springatom.data.event.before.*;
import org.agatom.springatom.data.hades.model.link.NObjectToObjectLink;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManager;


class NRepositoryImpl<T>
        extends QueryDslJpaRepository<T, Long>
        implements NRepository<T>, ApplicationEventPublisherAware {
    private static final Logger                        LOGGER            = LoggerFactory.getLogger(NRepositoryImpl.class);
    protected            JpaEntityInformation<T, Long> entityInformation = null;
    protected            EntityManager                 entityManager     = null;
    protected            ApplicationEventPublisher     eventPublisher    = null;

    /**
     * <p>Constructor for SBasicRepositoryImpl.</p>
     *
     * @param entityInformation a {@link org.springframework.data.jpa.repository.support.JpaEntityInformation} object.
     * @param entityManager     a {@link javax.persistence.EntityManager} object.
     */
    public NRepositoryImpl(final JpaEntityInformation<T, Long> entityInformation,
                           final EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String
                    .format("Created %s for arguments=[em=%s,ei=%s]", NRepositoryImpl.class
                            .getSimpleName(), entityManager, entityInformation));
        }
    }

    @Override
    public void delete(final Long id) {
        if (id == null) {
            LOGGER.warn("delete(...) called with null argument");
            return;
        }
        final T entity = this.findOne(id);
        this.publishBeforeDelete(entity);
        super.delete(id);
        this.publishAfterDelete(entity);
    }

    @Override
    public void delete(final T entity) {
        if (entity == null) {
            LOGGER.warn("delete(...) called with null argument");
            return;
        }
        this.publishBeforeDelete(entity);
        super.delete(entity);
        this.publishAfterDelete(entity);
    }

    @Override
    public void delete(final Iterable<? extends T> entities) {
        if (entities == null) {
            LOGGER.warn("delete(...) called with null argument");
            return;
        }
        if (FluentIterable.from(entities).isEmpty()) {
            LOGGER.warn("delete(...) called with empty argument");
            return;
        }
        this.publishBeforeDelete(entities);
        super.delete(entities);
        this.publishAfterDelete(entities);
    }

    @Override
    public void deleteInBatch(final Iterable<T> entities) {
        if (entities == null) {
            LOGGER.warn("deleteInBatch(...) called with null argument");
            return;
        }
        if (FluentIterable.from(entities).isEmpty()) {
            LOGGER.warn("deleteInBatch(...) called with empty argument");
            return;
        }
        this.publishBeforeDelete(entities);
        super.deleteInBatch(entities);
        this.publishAfterDelete(entities);
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity == null) {
            LOGGER.warn("save(...) called with null argument");
            return null;
        }
        final Persistable<?> persistable = (Persistable<?>) entity;
        final boolean isNew = persistable.isNew();
        this.publishBeforeCreateSaveEvent(entity, isNew);
        entity = super.save(entity);
        this.publishAfterCreateSaveEvent(entity, isNew);
        return entity;
    }

    private void publishBeforeCreateSaveEvent(final Object entity, final boolean isNew) {
        final ApplicationEvent eventObject;
        if (ClassUtils.isAssignableValue(NObjectToObjectLink.class, entity)) {
            eventObject = new BeforeLinkSaveEvent((NObjectToObjectLink<?, ?>) entity);
        } else {
            if (isNew) {
                eventObject = new BeforeCreateEvent(entity);
            } else {
                eventObject = new BeforeSaveEvent(entity);
            }
        }
        doPublish(eventObject);
    }

    private void publishAfterCreateSaveEvent(final Object entity, final boolean isNew) {
        final ApplicationEvent eventObject;
        if (ClassUtils.isAssignableValue(NObjectToObjectLink.class, entity)) {
            eventObject = new AfterLinkSaveEvent((NObjectToObjectLink<?, ?>) entity);
        } else {
            if (isNew) {
                eventObject = new AfterCreateEvent(entity);
            } else {
                eventObject = new AfterSaveEvent(entity);
            }
        }
        doPublish(eventObject);

    }

    private void doPublish(final ApplicationEvent eventObject) {
        if (this.eventPublisher != null) {
            if (eventObject != null) {
                this.eventPublisher.publishEvent(eventObject);
            }
        } else if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(String.format("Failed to publish %s because eventPublisher is null", eventObject));
        }
    }

    private void publishBeforeDelete(final Object entity) {
        final ApplicationEvent eventObject;
        if (ClassUtils.isAssignableValue(NObjectToObjectLink.class, entity)) {
            eventObject = new BeforeLinkDeleteEvent((NObjectToObjectLink<?, ?>) entity);
        } else {
            eventObject = new BeforeDeleteEvent(entity);
        }
        doPublish(eventObject);
    }

    private void publishAfterDelete(final Object entity) {
        final ApplicationEvent eventObject;
        if (ClassUtils.isAssignableValue(NObjectToObjectLink.class, entity)) {
            eventObject = new AfterLinkDeleteEvent((NObjectToObjectLink<?, ?>) entity);
        } else {
            eventObject = new AfterDeleteEvent(entity);
        }
        doPublish(eventObject);

    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public JPAQuery query() {
        return new JPAQuery(this.entityManager, new DefaultQueryMetadata());
    }
}
