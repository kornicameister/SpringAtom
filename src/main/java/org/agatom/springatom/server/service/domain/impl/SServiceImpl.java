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

package org.agatom.springatom.server.service.domain.impl;

import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.repository.SRepository;
import org.agatom.springatom.server.repository.exceptions.EntityInRevisionDoesNotExists;
import org.agatom.springatom.server.service.domain.SService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * {@code SBasicServiceImpl} is the locale class for all services
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@SuppressWarnings({"SpringJavaAutowiringInspection"})
@Transactional(readOnly = true,
        isolation = Isolation.SERIALIZABLE,
        propagation = Propagation.SUPPORTS,
        rollbackFor = EntityInRevisionDoesNotExists.class)
abstract class SServiceImpl<T extends Persistable<ID>, ID extends Serializable, N extends Number & Comparable<N>>
        extends SBasicServiceImpl<T, ID>
        implements SService<T, ID, N> {
    private static final Logger LOGGER           = Logger.getLogger(SServiceImpl.class);
    private static final String CACHE_NAME       = "org_springatom_cache_revisions";
    private static final String CACHE_NAME_F     = "org_springatom_cache_revisions_first";
    private static final String CACHE_NAME_L     = "org_springatom_cache_revisions_last";
    private static final String CACHE_NAME_COUNT = "org_springatom_cache_revisions_count";

    @Autowired
    protected SRepository<T, ID, N> revisionRepository = null;


    @Override
    @Cacheable(value = CACHE_NAME_F)
    public Revision<N, T> findFirstRevision(final ID id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findFirstRevision", id));
        }
        return this.revisionRepository.findRevisions(id).iterator().next();
    }

    @Override
    @Cacheable(value = CACHE_NAME)
    public Revisions<N, T> findAllRevisions(final ID id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findAllRevisions", id));
        }
        return this.revisionRepository.findRevisions(id);
    }

    @Override
    @Cacheable(value = CACHE_NAME_L)
    public Revision<N, T> findLatestRevision(final ID id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findLatestRevision", id));
        }
        return this.revisionRepository.findLastChangeRevision(id);
    }

    @Override
    @Cacheable(value = CACHE_NAME_COUNT)
    public long countRevisions(final ID id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "countRevisions", id));
        }
        return this.revisionRepository.countRevisions(id);
    }

    @Override
    public Revisions<N, T> findModifiedBefore(final ID id, final DateTime dateTime) throws
            EntityInRevisionDoesNotExists {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findModifiedBefore", id));
        }
        return this.revisionRepository.findRevisions(id, dateTime, SBasicRepository.Operators.BEFORE);
    }

    @Override
    public Revisions<N, T> findModifiedAfter(final ID id, final DateTime dateTime) throws
            EntityInRevisionDoesNotExists {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findModifiedAfter", id));
        }
        return this.revisionRepository.findRevisions(id, dateTime, SBasicRepository.Operators.AFTER);
    }

    @Override
    public Revisions<N, T> findModifiedAt(final ID id, final DateTime dateTime)
            throws EntityInRevisionDoesNotExists {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s(%s)", "findModifiedAt", id));
        }
        return this.revisionRepository.findRevisions(id, dateTime, SBasicRepository.Operators.EQ);
    }
}
