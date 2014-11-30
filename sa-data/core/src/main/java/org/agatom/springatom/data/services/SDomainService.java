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

package org.agatom.springatom.data.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * {@code SBasicService} is the interface for the services layers in {@code SpringAtom}.
 * Defines commonly used functionality of <b>CRUD</b> operations.
 * <p/>
 * It takes advantage from <b>fluent-interface approach</b> allowing to add complex criteria to the
 * request such as:
 * <ol>
 * <li>{@link org.springframework.data.domain.Pageable} - paging the request</li>
 * <li>{@link com.mysema.query.types.OrderSpecifier} - ordering</li>
 * <li>{@link com.mysema.query.types.Expression} - grouping</li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */

@Validated
public interface SDomainService<T extends Persistable<Long>>
        extends SService {
    @NotNull
    T findOne(Long id);

    @NotNull
    Iterable<T> findAll();

    @NotNull
    Page<T> findAll(final Pageable pageable);

    @NotNull
    T save(final @NotNull T persistable);

    T deleteOne(final @NotNull Long pk);

    void deleteAll();

    @NotNull
    void delete(final Iterable<T> toDelete);

    long count();
}
