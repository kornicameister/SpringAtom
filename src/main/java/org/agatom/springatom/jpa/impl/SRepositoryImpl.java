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

/*
 * SpringAtom is using part of the original Spring-data-envers code but takes no credit for it.
 * This code was built by Spring team and the honor goes to them.
 */


package org.agatom.springatom.jpa.impl;

import com.google.common.base.Preconditions;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.agatom.springatom.jpa.SRepository;
import org.apache.log4j.Logger;
import org.hibernate.envers.*;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.AuditProperty;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryImpl;
import org.springframework.data.history.AnnotationRevisionMetadata;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SRepositoryImpl<T, ID extends Serializable, N extends Number & Comparable<N>>
        extends QueryDslJpaRepository<T, ID>
        implements SRepository<T, ID, N> {
    public static final  String ERROR_MESSAGE_INSUFFICIENT_REV_NUMBERS = "Insufficient revision numbers";
    private static final Logger LOGGER                                 = Logger.getLogger(SRepositoryImpl.class);
    private static final String ERROR_MESSAGE_ID                       = "ID must not be null";
    private static final String ERROR_MESSAGE_PAGEABLE                 = "Pageable must not be null";
    private static final String ERROR_MESSAGE_REVISION                 = "Revision must not be null";
    private static final String ERROR_MESSAGE_REI                      = "RevisionEntityInformation must not be null";
    private final JpaEntityInformation<T, ID> entityInformation;
    private final EntityManager               entityManager;
    private final RevisionEntityInformation   revisionEntityInformation;
    private final RevisionRepository          repository;

    public SRepositoryImpl(final JpaEntityInformation<T, ID> entityInformation,
                           final RevisionEntityInformation revisionEntityInformation,
                           final EntityManager entityManager) {
        super(entityInformation, entityManager);
        Preconditions.checkArgument(revisionEntityInformation != null, ERROR_MESSAGE_REI);
        this.entityInformation = entityInformation;
        this.revisionEntityInformation = revisionEntityInformation;
        this.entityManager = entityManager;
        this.repository = new EnversRevisionRepositoryImpl<T, ID, N>(entityInformation, revisionEntityInformation, entityManager);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String
                    .format("Created %s for arguments=[em=%s,ei=%s,rei=%s]", SRepositoryImpl.class
                            .getSimpleName(), entityManager, entityInformation, revisionEntityInformation));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Revision<N, T> findLastChangeRevision(final ID id) {
        Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s(%s)", "findLastChangeRevision", id));
        }
        return this.repository.findLastChangeRevision(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Revisions<N, T> findRevisions(final ID id) {
        Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s(%s)", "findRevisions", id));
        }
        return this.repository.findRevisions(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Revision<N, T>> findRevisions(final ID id, final Pageable pageable) {
        Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
        Preconditions.checkArgument(pageable != null, ERROR_MESSAGE_PAGEABLE);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s(%s,%s)", "findRevisions", id, pageable));
        }
        return this.repository.findRevisions(id, pageable);
    }

    @Override
    public Revision<N, T> findInRevision(final ID id, final N revision) {
        Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
        Preconditions.checkArgument(revision != null, ERROR_MESSAGE_REVISION);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s(%s,%s)", "findInRevision", id, revision));
        }
        return this.findInRevisions(id, revision).getLatestRevision();
    }

    @Override
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final Revisions<N, T> findInRevisions(final ID id, final N... revisionNumbers) {
        Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
        Preconditions.checkArgument(revisionNumbers.length >= 1, ERROR_MESSAGE_INSUFFICIENT_REV_NUMBERS);

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s(%s,%s)", "findInRevision", id, Arrays.toString(revisionNumbers)));
        }

        final Class<T> type = this.entityInformation.getJavaType();
        final AuditReader reader = AuditReaderFactory.get(this.entityManager);
        final Map<N, T> revisions = new HashMap<>(revisionNumbers.length);
        final Class<?> revisionEntityClass = this.revisionEntityInformation.getRevisionEntityClass();
        final Map<Number, Object> revisionEntities = (Map<Number, Object>) reader.findRevisions(revisionEntityClass,
                new HashSet<Number>(Arrays.asList(revisionNumbers)));

        for (Number number : revisionNumbers) {
            revisions.put((N) number, reader.find(type, id, number));
        }

        return new Revisions<>(this.toRevisions(revisions, revisionEntities));
    }

    @SuppressWarnings("unchecked")
    private List<Revision<N, T>> toRevisions(Map<N, T> source, Map<Number, Object> revisionEntities) {
        final List<Revision<N, T>> result = new ArrayList<>();
        for (Map.Entry<N, T> revision : source.entrySet()) {
            final N revisionNumber = revision.getKey();
            final T entity = revision.getValue();
            final RevisionMetadata<N> metadata = (RevisionMetadata<N>) getRevisionMetadata(revisionEntities
                    .get(revisionNumber));
            result.add(new Revision<>(metadata, entity));
        }
        Collections.sort(result);
        return Collections.unmodifiableList(result);
    }

    @SuppressWarnings("Convert2Diamond")
    private RevisionMetadata<?> getRevisionMetadata(Object object) {
        if (object instanceof DefaultRevisionEntity) {
            return new DefaultRevisionMetadata((DefaultRevisionEntity) object);
        } else {
            return new AnnotationRevisionMetadata<N>(object, RevisionNumber.class, RevisionTimestamp.class);
        }
    }

    @Override
    @SuppressWarnings({"unchecked", "SuspiciousToArrayCall"})
    public Revisions<N, T> findRevisions(final ID id, final DateTime dateTime, final Operators operator) {
        final Class<T> type = this.entityInformation.getJavaType();
        final AuditReader reader = AuditReaderFactory.get(this.entityManager);
        final AuditProperty<Object> actualDate = AuditEntity
                .revisionProperty("timestamp");

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s(%s,%s,%s)", "findRevisions", id, dateTime, operator));
        }

        AuditQuery auditQuery = reader.createQuery()
                                      .forRevisionsOfEntity(type, false, true);

        switch (operator) {
            case BEFORE:
                auditQuery = auditQuery.add(actualDate.le(dateTime.getMillis()));
                break;
            case AFTER:
                auditQuery = auditQuery.add(actualDate.ge(dateTime.getMillis()));
                break;
            case EQ:
                auditQuery = auditQuery.add(actualDate.eq(dateTime.getMillis()));
                break;
        }

        final List<Object[]> resultList = auditQuery.getResultList();
        if (resultList.isEmpty()) {
            return new Revisions(new ArrayList<>());
        }

        final List<Revision<N, T>> revisionList = new ArrayList<>();
        for (Object[] number : resultList) {
            final Object entity = number[0];
            final Object revEntity = number[1];
            revisionList
                    .add((Revision<N, T>) new Revision<>(this.getRevisionMetadata(revEntity), entity));
        }

        return new Revisions<>(revisionList);
    }

    @Override
    public long countRevisions(final ID id) {
        final Class<T> type = this.entityInformation.getJavaType();
        final AuditReader reader = AuditReaderFactory.get(this.entityManager);

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("%s(%s)", "countRevisions", id));
        }

        return (long) reader.createQuery()
                            .forRevisionsOfEntity(type, false, true)
                            .addProjection(AuditEntity.revisionNumber().count())
                            .getSingleResult();
    }

    public JPQLQuery createQuery() {
        return new JPAQuery(this.entityManager);
    }
}
