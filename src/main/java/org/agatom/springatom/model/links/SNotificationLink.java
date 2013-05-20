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

package org.agatom.springatom.model.links;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.agatom.springatom.model.client.SClient;
import org.agatom.springatom.model.notification.SNotification;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SNotificationLink")
@Table(name = "SNotificationLink")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSNotificationLink",
                updatable = false,
                nullable = false)
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.STRING
)
public class SNotificationLink extends PersistentObject {

    @OneToOne(optional = false)
    @JoinColumn(name = "notification", referencedColumnName = "idSNotification", updatable = false)
    private SNotification notification;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client", referencedColumnName = "idSClient", updatable = false)
    private SClient client;

    @Type(type = "boolean")
    @Column(name = "checked")
    private Boolean checked;

    public Boolean isChecked() {
        return this.checked;
    }

    public SNotification getNotification() {
        return notification;
    }

    public void setNotification(final SNotification notification) {
        this.notification = notification;
    }

    public SClient getClient() {
        return client;
    }

    public void setClient(final SClient client) {
        this.client = client;
    }

    public void setChecked(final Boolean read) {
        this.checked = read;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + notification.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + checked.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SNotificationLink)) return false;
        if (!super.equals(o)) return false;

        SNotificationLink that = (SNotificationLink) o;

        return checked.equals(that.checked) &&
                client.equals(that.client) &&
                notification.equals(that.notification);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("notification", notification)
                .add("client", client)
                .add("checked", checked)
                .toString();
    }
}
