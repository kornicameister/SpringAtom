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


package org.agatom.springatom.jpa;

import com.google.common.base.Preconditions;
import org.agatom.springatom.jpa.exceptions.EntityInRevisionDoesNotExists;
import org.apache.log4j.Logger;
import org.hibernate.envers.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
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
    private static final Logger LOGGER                 = Logger.getLogger(SRepositoryImpl.class);
    private static final String ERROR_MESSAGE_ID       = "ID must not be null";
    private static final String ERROR_MESSAGE_PAGEABLE = "Pageable must not be null";
    private static final String ERROR_MESSAGE_REVISION = "Revision must not be null";
    private static final String ERROR_MESSAGE_REI      = "RevisionEntityInformation must not be null";
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
        return this.repository.findLastChangeRevision(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Revisions<N, T> findRevisions(final ID id) {
        Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
        return this.repository.findRevisions(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Revision<N, T>> findRevisions(final ID id, final Pageable pageable) {
        Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
        Preconditions.checkArgument(pageable != null, ERROR_MESSAGE_PAGEABLE);
        return this.repository.findRevisions(id, pageable);
    }

    @Override
    public Revision<N, T> findInRevision(final ID id, final N revision) throws EntityInRevisionDoesNotExists {
        Preconditions.checkArgument(id != null, ERROR_MESSAGE_ID);
        Preconditions.checkArgument(revision != null, ERROR_MESSAGE_REVISION);
        return this.findInRevisions(id, revision).getLatestRevision();
    }

    @Override
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final Revisions<N, T> findInRevisions(final ID id, final N... revisionNumbers) throws
            EntityInRevisionDoesNotExists {
        final Class<T> type = this.entityInformation.getJavaType();
        final AuditReader reader = AuditReaderFactory.get(this.entityManager);
        final Map<N, T> revisions = new HashMap<>(revisionNumbers.length);
        final Class<?> revisionEntityClass = this.revisionEntityInformation.getRevisionEntityClass();
        final Map<Number, Object> revisionEntities = (Map<Number, Object>) reader.findRevisions(revisionEntityClass,
                new HashSet<Number>(Arrays.asList(revisionNumbers)));

        for (Number number : revisionNumbers) {
            revisions.put((N) number, reader.find(type, id, number));
        }

        if (revisions.isEmpty()) {
            throw new EntityInRevisionDoesNotExists((Class<? extends Persistable>) type, revisionNumbers);
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
}
