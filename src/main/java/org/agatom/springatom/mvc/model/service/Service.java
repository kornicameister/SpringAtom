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

package org.agatom.springatom.mvc.model.service;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * {@code Service} is the interface for the services layers in {@code SpringAtom}.
 * Defines commonly used functionality of <b>CRUD</b> operations.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */

public interface Service<T extends Persistable, ID extends Serializable, R extends JpaRepository> {
    /**
     * Method implemented by services with {@link org.springframework.beans.factory.annotation.Autowired} annotation to
     * set the {@link org.springframework.data.jpa.repository.JpaRepository}
     * of the given type subtype.
     *
     * @param repo
     *         {@code JpaRepository} or its subclass
     */
    void autoWireRepository(final R repo);

    /**
     * Returns single entity by provided ID of the {@code Service}'s <b>domain class</b>
     *
     * @param id
     *         the id
     *
     * @return the entity with the given {@code ID}
     */
    T findOne(@NotNull ID id);

    /**
     * Returns all entities of the type for which {@code Service} was defined (<b>domain class</b>)
     *
     * @return all entities of the {@code Service}'s domain class
     */
    List<T> findAll();

    /**
     * Saves the {@code Service}'s <b>domain class</b> object and returns its persisted version,
     * This method can be used to either save or update the entity thanks to {@link
     * org.springframework.data.jpa.repository.support.SimpleJpaRepository#save(Object)} method
     *
     * @param persistable
     *         to be saved
     *
     * @return persisted entity
     */
    T save(@NotNull final T persistable);

    /**
     * Returns how many objects of {@code Service}'s <b>domain class</b> exists
     *
     * @return row count
     */
    Long count();

    /**
     * Deletes only one entity of {@code Service}'s <b>domain class</b> for given {@code ID}
     *
     * @param pk
     *         the entity's {@code ID}
     */
    void deleteOne(@NotNull final ID pk);

    /**
     * Deletes all entities of {@code Service}'s <b>domain class</b>
     */
    void deleteAll();
}
