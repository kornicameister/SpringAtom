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

import org.agatom.springatom.model.listener.VersionedObjectListener;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.util.Date;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
@RevisionEntity(VersionedObjectListener.class)
abstract public class PersistentVersionedObject extends Persistent {

    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "hilo")
    private Long id = null;

    @RevisionTimestamp
    @Type(type = "timestamp")
    private Date revisionTimeStamp;

    @Column(name = "updatedBy")
    private String updatedBy;

    public PersistentVersionedObject() {
        super();
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final String updateBy) {
        this.updatedBy = updateBy;
    }

    @Override
    protected void resolveIdColumnName() {
        for (Annotation annotation : this.getClass().getAnnotations()) {
            if (annotation instanceof AttributeOverride) {
                AttributeOverride attributeOverride = (AttributeOverride) annotation;
                if (attributeOverride.name().equals("pk.id")) {
                    Column column = attributeOverride.column();
                    this.idColumnName = column.name();
                    break;
                }
            } else if (annotation instanceof AttributeOverrides) {
                AttributeOverrides attributeOverrides = (AttributeOverrides) annotation;
                for (AttributeOverride attributeOverride : attributeOverrides.value()) {
                    if (attributeOverride.name().equals("pk.id")) {
                        Column column = attributeOverride.column();
                        this.idColumnName = column.name();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int compareTo(final Persistable o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public Long getId() {
        return id;
    }


}
