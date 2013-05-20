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
 * along with SpringAtom.  If not, see <http://www.gnu.org/licenses/gpl.html>.                    *
 **************************************************************************************************/

package org.agatom.springatom.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedOn")
    private Date updatedOn;

    @Version
    private Long version = 0l;

    protected Persistent() {
        this.resolveIdColumnName();
        this.updatedOn = new Date();
    }

    protected abstract void resolveIdColumnName();

    public Date getUpdatedOn() {
        return updatedOn;
    }

    Long getVersion() {
        return version;
    }

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

    @Override
    public int hashCode() {
        int result = updatedOn != null ? updatedOn.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Persistent)) return false;

        Persistent that = (Persistent) o;

        return !(updatedOn != null ? !updatedOn.equals(that.updatedOn) : that.updatedOn != null) &&
                !(version != null ? !version.equals(that.version) : that.version != null);
    }

    @Override
    public String toString() {
        return "Persistent{" +
                "idColumnName='" + idColumnName + '\'' +
                ", updatedOn=" + updatedOn +
                ", version=" + version +
                '}';
    }
}
