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

import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class ComponentDataResponse
		implements Serializable {
	private static final long                        serialVersionUID = 5738008166804737807L;
	private              String                      builtBy          = null;
	private              Set<ComponentResponseValue> data             = null;
	private              boolean                     success          = false;
	private              Throwable                   error            = null;
	private              long                        time             = 0;

	public static ComponentDataResponse success(final String builtBy, final Set<ComponentResponseValue> data, final long time) {
		return new ComponentDataResponse()
				.setBuiltBy(builtBy)
				.setData(data)
				.setTime(time)
				.setSuccess(true);
	}

	public static ComponentDataResponse error(final String builtBy, final Throwable error, final long time) {
		return new ComponentDataResponse()
				.setBuiltBy(builtBy)
				.setError(error)
				.setTime(time)
				.setSuccess(false);
	}

	public String getBuiltBy() {
		return this.builtBy;
	}

	public ComponentDataResponse setBuiltBy(final String builtBy) {
		this.builtBy = builtBy;
		return this;
	}

	public Set<ComponentResponseValue> getData() {
		if (this.data == null) {
			this.data = Sets.newLinkedHashSet();
		}
		return this.data;
	}

	public ComponentDataResponse setData(final Set<ComponentResponseValue> data) {
		this.data = data;
		return this;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public ComponentDataResponse setSuccess(final boolean success) {
		this.success = success;
		return this;
	}

	public Throwable getError() {
		return this.error;
	}

	public ComponentDataResponse setError(final Throwable error) {
		this.error = error;
		return this;
	}

	public long getTime() {
		return this.time;
	}

	public ComponentDataResponse setTime(final long time) {
		this.time = time;
		return this;
	}

}
