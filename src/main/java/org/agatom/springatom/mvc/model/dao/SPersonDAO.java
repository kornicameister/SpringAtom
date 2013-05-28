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

package org.agatom.springatom.mvc.model.dao;

import org.agatom.springatom.model.util.SPerson;
import org.agatom.springatom.model.util.SPersonalInformation;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SPersonDAO<T extends SPerson, ID extends Serializable>
        extends CrudRepository<T, ID> {
    Iterable findByPersonalInformation(@NotNull final SPersonalInformation information);

    Iterable findByFirstName(@NotNull final String firstName);

    Iterable findByLastName(@NotNull final String lastName);

    T findByEmail(@NotNull final String email);

    Iterable findByStatus(@NotNull final Boolean enabled);

    T setDisabled(@NotNull final SPerson person, @NotNull final Boolean enable);
}
