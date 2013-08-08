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

package org.agatom.springatom.mvc.model.service.base;

import org.agatom.springatom.jpa.exceptions.EntityInRevisionDoesNotExists;
import org.joda.time.DateTime;
import org.springframework.data.domain.Persistable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {@code SBasicService} is the interface for the services layers in {@code SpringAtom}.
 * Defines commonly used functionality of <b>CRUD</b> operations.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */

public interface SService<
        T extends Persistable,
        ID extends Serializable,
        N extends Number & Comparable<N>,
        R extends JpaRepository>
        extends SBasicService<T, ID, R> {

    Revision<N, T> findFirstRevision(
            @NotNull
            final ID id);

    Revisions<N, T> findAllRevisions(
            @NotNull
            final ID id);

    Revision<N, T> findLatestRevision(
            @NotNull
            final ID id);

    long countRevisions(
            @NotNull
            final ID id);

    Revisions<N, T> findModifiedBefore(
            @NotNull
            final ID id,
            @NotNull
            final DateTime dateTime) throws EntityInRevisionDoesNotExists;

    Revisions<N, T> findModifiedAfter(
            @NotNull
            final ID id,
            @NotNull
            final DateTime dateTime) throws EntityInRevisionDoesNotExists;

    Revisions<N, T> findModifiedAt(
            @NotNull
            final ID id,
            @NotNull
            final DateTime dateTime) throws EntityInRevisionDoesNotExists;

}
