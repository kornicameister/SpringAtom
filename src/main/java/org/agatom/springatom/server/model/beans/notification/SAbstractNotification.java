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

package org.agatom.springatom.server.model.beans.notification;

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.notification.SNotification;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.Past;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SAbstractNotification.TABLE_NAME)
@Entity(name = SAbstractNotification.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "idSNotification", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "sbn",
		discriminatorType = DiscriminatorType.STRING
)
abstract public class SAbstractNotification
		extends PersistentObject<Long>
		implements SNotification<Long> {
	public static final  String TABLE_NAME       = "notifications";
	public static final  String ENTITY_NAME      = "SAbstractNotification";
	private static final String DATE_TIME_TYPE   = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
	private static final long   serialVersionUID = -5518002639762454805L;
	@NotBlank
	@Length(max = 1000)
	@Column(name = "message", length = 1000)
	private String message;
	@Type(type = "boolean")
	@Column(name = "wasRead")
	private Boolean read = false;
	@Past
	@Type(type = DATE_TIME_TYPE)
	@Column(name = "sent", nullable = false)
	private DateTime sent;

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public SAbstractNotification setMessage(final String notification) {
		this.message = notification;
		return this;
	}

	@Override
	public DateTime getSent() {
		return null == this.sent ? null : this.sent;
	}

	@Override
	public SAbstractNotification setSent(final DateTime sent) {
		this.sent = sent;
		return this;
	}

	@Override
	public Boolean isRead() {
		return this.read;
	}

	@Override
	public SAbstractNotification readNotification() {
		this.read = true;
		return this;
	}

	@Override
	public String getIdentity() {
		return String.valueOf(this.getId());
	}
}
