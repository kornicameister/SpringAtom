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

package org.agatom.springatom.web.component.core.data;

import java.io.Serializable;

/**
 * {@code ComponentDataResponse} is the bean sent from {@link org.agatom.springatom.web.component.core.builders.ComponentDataBuilder}
 * instance. Whereas particular implementation of {@link org.agatom.springatom.web.component.core.builders.ComponentDataBuilder}
 * are not bound to specific return value, {@code ComponentDataResponse} is always returned as the final value from them.
 * Check {@link org.agatom.springatom.web.component.core.builders.AbstractComponentDataBuilder#buildData(ComponentDataRequest)}
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class ComponentDataResponse
		implements Serializable {
	private static final long      serialVersionUID = 5738008166804737807L;
	private              String    builtBy          = null;
	private              Object    data             = null;
	private              boolean   success          = false;
	private              Throwable error            = null;
	private              long      time             = 0;

	/**
	 * <p>success.</p>
	 *
	 * @param builtBy a {@link java.lang.String} object.
	 * @param data    a {@link java.lang.Object} object.
	 * @param time    a long.
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.data.ComponentDataResponse} object.
	 */
	public static ComponentDataResponse success(final String builtBy, final Object data, final long time) {
		return new ComponentDataResponse()
				.setBuiltBy(builtBy)
				.setData(data)
				.setTime(time)
				.setSuccess(true);
	}

	/**
	 * <p>error.</p>
	 *
	 * @param builtBy a {@link java.lang.String} object.
	 * @param error   a {@link java.lang.Throwable} object.
	 * @param time    a long.
	 *
	 * @return a {@link org.agatom.springatom.web.component.core.data.ComponentDataResponse} object.
	 */
	public static ComponentDataResponse error(final String builtBy, final Throwable error, final long time) {
		return new ComponentDataResponse()
				.setBuiltBy(builtBy)
				.setError(error)
				.setTime(time)
				.setSuccess(false);
	}

	/**
	 * <p>Getter for the field <code>builtBy</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getBuiltBy() {
		return this.builtBy;
	}

	private ComponentDataResponse setBuiltBy(final String builtBy) {
		this.builtBy = builtBy;
		return this;
	}

	/**
	 * <p>Getter for the field <code>data</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getData() {
		return this.data;
	}

	private ComponentDataResponse setData(final Object data) {
		this.data = data;
		return this;
	}

	/**
	 * <p>isSuccess.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isSuccess() {
		return this.success;
	}

	private ComponentDataResponse setSuccess(final boolean success) {
		this.success = success;
		return this;
	}

	/**
	 * <p>Getter for the field <code>error</code>.</p>
	 *
	 * @return a {@link java.lang.Throwable} object.
	 */
	public Throwable getError() {
		return this.error;
	}

	private ComponentDataResponse setError(final Throwable error) {
		this.error = error;
		return this;
	}

	/**
	 * <p>Getter for the field <code>time</code>.</p>
	 *
	 * @return a long.
	 */
	public long getTime() {
		return this.time;
	}

	private ComponentDataResponse setTime(final long time) {
		this.time = time;
		return this;
	}

}
