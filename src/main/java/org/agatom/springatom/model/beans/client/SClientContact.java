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

package org.agatom.springatom.model.beans.client;

import com.google.common.base.Objects;
import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.meta.SContactType;
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
@javax.persistence.NamedQueries(value = {
        @NamedQuery(
                lockMode = LockModeType.READ,
                name = "fromClientContactByClientId",
                query = "from SClientContact scc where scc.client.id = :clientId order by scc.type"
        )
})
public class SClientContact extends PersistentObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "client",
            referencedColumnName = "idSClient",
            updatable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SClient client;

    @ManyToOne(optional = false,
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.REMOVE,
                    CascadeType.PERSIST
            })
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData")
    private SContactType type;

    @Column(name = "contact", length = 60)
    private String contact;

    public SClient getClient() {
        return client;
    }

    public void setClient(final SClient client) {
        this.client = client;
    }

    public SContactType getType() {
        return type;
    }

    public void setType(final SContactType type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + contact.hashCode();
        return result;
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
    public String toString() {
        return Objects.toStringHelper(this)
                .add("client", client)
                .add("type", type)
                .add("contact", contact)
                .toString();
    }
}
