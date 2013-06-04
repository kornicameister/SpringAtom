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

package org.agatom.springatom.model.beans;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
@DynamicInsert
@DynamicUpdate
abstract class Persistent implements Persistable {
    @Transient
    protected String idColumnName;

    public Persistent() {
        this.resolveIdColumnName();
    }

    protected abstract void resolveIdColumnName();

    @Override
    public String getEntityName() {
        Table annotation = this.getClass().getAnnotation(Table.class);
        if (annotation == null) {
            annotation = this.getClass().getSuperclass().getAnnotation(Table.class);
        }
        return annotation.name();
    }

    @Override
    public String getIdColumnName() {
        return this.idColumnName;
    }
}
