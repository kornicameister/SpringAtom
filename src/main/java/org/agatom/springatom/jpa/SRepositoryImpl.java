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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SRepositoryImpl<T, ID extends Serializable, N extends Number & Comparable<N>>
        extends QueryDslJpaRepository<T, ID>
        implements SRepository<T, ID, N> {

    private RevisionEntityInformation revisionEntityInformation;

    public SRepositoryImpl(final JpaEntityInformation<T, ID> entityInformation,
                           final RevisionEntityInformation revisionEntityInformation,
                           final EntityManager entityManager) {
        this(entityInformation, entityManager);
        this.revisionEntityInformation = revisionEntityInformation;
    }

    public SRepositoryImpl(final JpaEntityInformation<T, ID> entityInformation,
                           final EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public Revision<N, T> findLastChangeRevision(final ID id) {
        return null;
    }

    @Override
    public Revisions<N, T> findRevisions(final ID id) {
        return null;
    }

    @Override
    public Page<Revision<N, T>> findRevisions(final ID id, final Pageable pageable) {
        return null;
    }
}
