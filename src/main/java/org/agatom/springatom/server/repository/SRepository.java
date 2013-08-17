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

package org.agatom.springatom.server.repository;

import org.agatom.springatom.server.repository.exceptions.EntityInRevisionDoesNotExists;
import org.joda.time.DateTime;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.history.RevisionRepository;

import java.io.Serializable;

/**
 * {@code SRepository} is an extension of {@link SBasicRepository} that adds revision capable methods from
 * {@link RevisionRepository}.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@NoRepositoryBean
public interface SRepository<T, ID extends Serializable, N extends Number & Comparable<N>>
        extends SBasicRepository<T, ID>,
                RevisionRepository<T, ID, N> {
    /**
     * {@code findInRevision} returns {@link Revision} of the target underlying target entity in the given revision
     * described in {@code revision} param
     *
     * @param id
     *         id of the entity {@link org.springframework.data.domain.Persistable#getId()}
     * @param revision
     *         the revision number
     *
     * @return {@link Revision} for passed arguments
     *
     * @throws EntityInRevisionDoesNotExists
     * @see SRepository#findInRevisions(java.io.Serializable, Number[])
     */
    Revision<N, T> findInRevision(final ID id, final N revision);

    /**
     * {@code findInRevisions} does exactly the same job but for multiple possible {@code revisions}.
     *
     * @param id
     *         id of the entity {@link org.springframework.data.domain.Persistable#getId()}
     * @param revisions
     *         varargs with revision numbers
     *
     * @return {@link Revisions}
     */
    @SuppressWarnings("unchecked") Revisions<N, T> findInRevisions(final ID id, final N... revisions);

    Revisions<N, T> findRevisions(final ID id, final DateTime dateTime, final Operators before);

    /**
     * Returns how many revisions exists for given {@link org.springframework.data.domain.Persistable#getId()} instance
     *
     * @param id
     *         the id
     *
     * @return revisions amount
     */
    long countRevisions(ID id);
}
