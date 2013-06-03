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

import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Annotation;

/**
 * MappedSuperclass for entities, aggregates all shared properties
 * that can be found in spring.database
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@MappedSuperclass
abstract public class PersistentObject extends Persistent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "hilo")
    private Long id = null;

    public PersistentObject() {
        super();
    }

    @Override
    protected void resolveIdColumnName() {
        for (Annotation annotation : this.getClass().getAnnotations()) {
            if (annotation instanceof AttributeOverride) {
                AttributeOverride attributeOverride = (AttributeOverride) annotation;
                if (attributeOverride.name().equals("id")) {
                    Column column = attributeOverride.column();
                    this.idColumnName = column.name();
                    break;
                }
            } else if (annotation instanceof AttributeOverrides) {
                AttributeOverrides attributeOverrides = (AttributeOverrides) annotation;
                for (AttributeOverride attributeOverride : attributeOverrides.value()) {
                    if (attributeOverride.name().equals("id")) {
                        Column column = attributeOverride.column();
                        this.idColumnName = column.name();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersistentObject)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        PersistentObject that = (PersistentObject) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

    @Override
    public int compareTo(final Persistable object) {
        return this.getId().compareTo(object.getId());
    }

    @Override
    public final Long getId() {
        return id;
    }
}
