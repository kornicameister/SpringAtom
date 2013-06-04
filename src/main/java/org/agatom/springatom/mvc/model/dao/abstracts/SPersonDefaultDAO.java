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

package org.agatom.springatom.mvc.model.dao.abstracts;

import org.agatom.springatom.model.beans.Persistable;
import org.agatom.springatom.model.beans.util.SPerson;
import org.agatom.springatom.model.beans.util.SPersonalInformation;
import org.agatom.springatom.mvc.model.dao.SPersonDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@SuppressWarnings("unchecked")
@Repository(value = "SPersonDAO")
public abstract class SPersonDefaultDAO<T extends SPerson, ID extends Serializable>
        extends DefaultDAO<T, ID>
        implements SPersonDAO<T, ID> {

    public Iterable findByPersonalInformation(@NotNull final SPersonalInformation information) {
        return null;
    }

    public Iterable findByFirstName(@NotNull final String firstName) {
        return null;
    }

    public Iterable findByLastName(@NotNull final String lastName) {
        return null;
    }

    public T findByEmail(@NotNull final String email) {
        return null;
    }

    public Iterable findByStatus(@NotNull final Boolean enabled) {
        return null;
    }

    @Transactional
    public T setDisabled(@NotNull final SPerson person, @NotNull final Boolean enable) {
        person.setDisabled(enable);
        return this.load(this.update((T) person));
    }

    @Override
    protected Class<? extends Persistable> getTargetClazz() {
        return SPerson.class;
    }
}
