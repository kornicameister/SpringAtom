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

package org.agatom.springatom.mvc.model.service.impl;

import org.agatom.springatom.jpa.SBasicRepository;
import org.agatom.springatom.jpa.SRepository;
import org.agatom.springatom.jpa.exceptions.EntityInRevisionDoesNotExists;
import org.agatom.springatom.mvc.model.service.SService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.data.domain.Persistable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * {@code SBasicServiceImpl} is the base class for all services
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@SuppressWarnings("unchecked")
@Transactional(readOnly = true,
        isolation = Isolation.SERIALIZABLE,
        propagation = Propagation.SUPPORTS,
        rollbackFor = EntityInRevisionDoesNotExists.class)
abstract class SServiceImpl<T extends Persistable, ID extends Serializable, N extends Number & Comparable<N>, R extends JpaRepository>
        extends SBasicServiceImpl<T, ID, R>
        implements SService<T, ID, N, R> {
    private static final Logger LOGGER = Logger.getLogger(SServiceImpl.class);
    private SRepository<T, ID, N> revRepo;

    @Override
    public void autoWireRepository(final R repo) {
        super.autoWireRepository(repo);
        this.revRepo = (SRepository) repo;
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Repository set to %s", this.revRepo));
        }
    }

    @Override
    public Revision<N, T> findFirstRevision(final ID id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findFirstRevision", id));
        }
        return this.revRepo.findRevisions(id).iterator().next();
    }

    @Override
    public Revisions<N, T> findAllRevisions(final ID id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findAllRevisions", id));
        }
        return this.revRepo.findRevisions(id);
    }

    @Override
    public Revision<N, T> findLatestRevision(final ID id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findLatestRevision", id));
        }
        return this.revRepo.findLastChangeRevision(id);
    }

    @Override
    public long countRevisions(final ID id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "countRevisions", id));
        }
        return this.revRepo.countRevisions(id);
    }

    @Override
    public Revisions<N, T> findModifiedBefore(final ID id, final DateTime dateTime) throws
            EntityInRevisionDoesNotExists {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findModifiedBefore", id));
        }
        return this.revRepo.findRevisions(id, dateTime, SBasicRepository.Operators.BEFORE);
    }

    @Override
    public Revisions<N, T> findModifiedAfter(final ID id, final DateTime dateTime) throws
            EntityInRevisionDoesNotExists {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findModifiedAfter", id));
        }
        return this.revRepo.findRevisions(id, dateTime, SBasicRepository.Operators.AFTER);
    }

    @Override
    public Revisions<N, T> findModifiedAt(final ID id, final DateTime dateTime)
            throws EntityInRevisionDoesNotExists {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findModifiedAt", id));
        }
        return this.revRepo.findRevisions(id, dateTime, SBasicRepository.Operators.EQ);
    }
}
