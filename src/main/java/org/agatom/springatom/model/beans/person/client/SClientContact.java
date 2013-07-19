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

package org.agatom.springatom.model.beans.person.client;

import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.meta.SMetaData;
import org.agatom.springatom.model.beans.meta.SMetaDataCapable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SClientContact")
@Table(name = "SClientContact")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSClientContact",
                updatable = false,
                nullable = false)
)
public class SClientContact extends PersistentObject<Long> implements SMetaDataCapable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "client",
            referencedColumnName = "idSClient",
            updatable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SClient   client;
    @ManyToOne(optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData")
    private SMetaData type;
    @Column(name = "contact", length = 60)
    private String    contact;

    public SClient getClient() {
        return client;
    }

    public void setClient(final SClient client) {
        this.client = client;
    }

    @Override
    public SMetaData getMetaInformation() {
        return this.type;
    }

    @Override
    public void setMetaInformation(final SMetaData metaData) {
        this.type = metaData;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SClientContact)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SClientContact that = (SClientContact) o;

        return client.equals(that.client) &&
                contact.equals(that.contact) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + contact.hashCode();
        return result;
    }
}
