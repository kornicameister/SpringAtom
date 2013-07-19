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

package org.agatom.springatom.model.beans.notification;

import com.google.common.base.Objects;
import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.meta.SNotificationType;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SNotification")
@Table(name = "SNotification")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSNotification",
                updatable = false,
                nullable = false)
)
public class SNotification extends PersistentObject<Long> {

    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(name = "type",
            referencedColumnName = "idSMetaData", updatable = true)
    private SNotificationType type;
    @Column(name = "message", length = 1000)
    private String            message;
    @Type(type = "timestamp")
    @Column(name = "sent")
    private Date              sent;

    public SNotification() {
        super();
    }

    public SNotificationType getType() {
        return type;
    }

    public void setType(final SNotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String notification) {
        this.message = notification;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(final Date sent) {
        this.sent = sent;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("message", message)
                .add("sent", sent)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SNotification)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SNotification that = (SNotification) o;

        return !(message != null ? !message.equals(that.message) : that.message != null) &&
                !(sent != null ? !sent.equals(that.sent) : that.sent != null) &&
                !(type != null ? !type.equals(that.type) : that.type != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (sent != null ? sent.hashCode() : 0);
        return result;
    }
}
