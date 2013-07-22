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

import org.agatom.springatom.model.beans.meta.SMetaDataHolder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
public class SNotification
        extends SMetaDataHolder {

    @Column(name = "message", length = 1000)
    private String message;
    @Type(type = "timestamp")
    @Column(name = "sent")
    private Date   sent;

    public SNotification() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String notification) {
        this.message = notification;
    }

    public DateTime getSent() {
        return null == this.sent ? null : new DateTime(this.sent);
    }

    public void setSent(final DateTime sent) {
        this.sent = sent.toDate();
    }


}
