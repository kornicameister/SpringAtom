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

package org.agatom.springatom.jpa;

import org.agatom.springatom.jpa.exceptions.EntityInRevisionDoesNotExists;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.history.RevisionRepository;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@NoRepositoryBean
public interface SRepository<T, ID extends Serializable, N extends Number & Comparable<N>>
        extends SBasicRepository<T, ID>, RevisionRepository<T, ID, N> {
    Revision<N, T> findInRevision(final ID id, final N revision) throws EntityInRevisionDoesNotExists;

    @SuppressWarnings("unchecked")
    Revisions<N, T> findInRevisions(final ID id, final N... revisions) throws EntityInRevisionDoesNotExists;
}
