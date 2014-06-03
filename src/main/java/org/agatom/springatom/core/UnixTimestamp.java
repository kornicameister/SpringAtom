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

package org.agatom.springatom.core;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * <p>UnixTimestamp class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class UnixTimestamp
		implements Serializable {
	private static final long serialVersionUID = -2419335050409513828L;
	private DateTime time;

	/**
	 * <p>Constructor for UnixTimestamp.</p>
	 *
	 * @param ts time in miliseconds.
	 */
	public UnixTimestamp(final long ts) {
		this.setTime(ts);
	}

	/**
	 * <p>Getter for the field <code>time</code>.</p>
	 *
	 * @return a {@link org.joda.time.DateTime} object.
	 */
	public DateTime getTime() {
		return time;
	}

	/**
	 * <p>Setter for the field <code>time</code>.</p>
	 *
	 * @param ts a long.
	 */
	public void setTime(final long ts) {
		this.time = new DateTime(TimeUnit.SECONDS.toMillis(ts));
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(time);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		UnixTimestamp that = (UnixTimestamp) o;

		return Objects.equal(this.time, that.time);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(time)
				.toString();
	}
}
