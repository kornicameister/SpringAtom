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

package org.agatom.springatom.server.service.domain;

import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * {@code SBasicService} is the interface for the services layers in {@code SpringAtom}.
 * Defines commonly used functionality of <b>CRUD</b> operations.
 * <p/>
 * It takes advantage from <b>fluent-interface approach</b> allowing to add complex criteria to the
 * request such as:
 * <ol>
 * <li>{@link Pageable} - paging the request</li>
 * <li>{@link OrderSpecifier} - ordering</li>
 * <li>{@link Expression} - grouping</li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */

@Validated
public interface SBasicService<T extends Persistable<ID>, ID extends Serializable> {
    /**
     * Returns single entity by provided ID of the {@code SBasicService}'s <b>domain class</b>
     *
     * @param id
     *         the id
     *
     * @return the entity with the given {@code ID}
     */
    @NotNull
    T findOne(ID id);

    /**
     * Returns all entities of the type for which {@code SBasicService} was defined (<b>domain class</b>)
     *
     * @return all entities of the {@code SBasicService}'s domain class
     */
    @NotNull
    List<T> findAll();

    @NotNull
    Page<T> findAll(final Pageable pageable);

    /**
     * Saves the {@code SBasicService}'s <b>domain class</b> object and returns its persisted version,
     * This method can be used to either save or update the entity thanks to {@link
     * org.springframework.data.jpa.repository.support.SimpleJpaRepository#save(Object)} method
     *
     * @param persistable
     *         to be saved
     *
     * @return persisted entity
     */
    @NotNull
    T save(final @NotNull T persistable);

    /**
     * Returns how many objects of {@code SBasicService}'s <b>domain class</b> exists
     *
     * @return row count
     */
    long count();

    /**
     * Deletes only one entity of {@code SBasicService}'s <b>domain class</b> for given {@code ID}
     *
     * @param pk
     *         the entity's {@code ID}
     */
    T deleteOne(final @NotNull ID pk);

    /**
     * Deletes all entities of {@code SBasicService}'s <b>domain class</b>
     */
    void deleteAll();

    @NotNull
    T withFullLoad(@NotNull T obj);

    @NotNull
    List<T> withFullLoad(@NotNull Iterable<T> objects);

    T detach(@NotNull T report);
}
