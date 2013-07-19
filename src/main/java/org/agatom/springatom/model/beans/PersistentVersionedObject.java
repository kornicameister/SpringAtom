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

import org.agatom.springatom.model.listeners.VersionedObjectListener;
import org.hibernate.annotations.Type;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionTimestamp;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
@RevisionEntity(VersionedObjectListener.class)
abstract public class PersistentVersionedObject<U, PK extends Serializable>
        extends AbstractAuditable<U, PK> {
    @RevisionTimestamp
    @Type(type = "timestamp")
    private Date revisionTimeStamp;
    @Version
    @GeneratedValue
    private Long version = 0l;

    public PersistentVersionedObject() {
        super();
    }

    public Date getRevisionTimeStamp() {
        return revisionTimeStamp;
    }

    public Long getVersion() {
        return version;
    }

    @PrePersist
    public void preCreateHandler() {
        this.setCreatedDate(DateTime.now());
        this.preUpdateHandler();
    }

    @PreUpdate
    public void preUpdateHandler() {
        this.setLastModifiedDate(DateTime.now());
    }
}
