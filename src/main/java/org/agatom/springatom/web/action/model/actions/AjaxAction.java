/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.web.action.model.actions;

import com.google.common.base.Objects;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>AjaxAction class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class AjaxAction
		extends LinkAction {
	private static final long          serialVersionUID = -1599199447411132454L;
	private              boolean       async            = Boolean.TRUE;
	private              boolean       cache            = Boolean.FALSE;
	private              Object        data             = null;
	private              MediaType     contentType      = MediaType.APPLICATION_JSON;
	private              MediaType     dataType         = MediaType.APPLICATION_JSON;
	private              RequestMethod type             = RequestMethod.POST;

	/**
	 * <p>isAsync.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isAsync() {
		return async;
	}

	/**
	 * <p>Setter for the field <code>async</code>.</p>
	 *
	 * @param async a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.actions.AjaxAction} object.
	 */
	public AjaxAction setAsync(final boolean async) {
		this.async = async;
		return this;
	}

	/**
	 * <p>isCache.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isCache() {
		return cache;
	}

	/**
	 * <p>Setter for the field <code>cache</code>.</p>
	 *
	 * @param cache a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.actions.AjaxAction} object.
	 */
	public AjaxAction setCache(final boolean cache) {
		this.cache = cache;
		return this;
	}

	/**
	 * <p>Getter for the field <code>data</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getData() {
		return data;
	}

	/**
	 * <p>Setter for the field <code>data</code>.</p>
	 *
	 * @param data a {@link java.lang.Object} object.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.actions.AjaxAction} object.
	 */
	public AjaxAction setData(final Object data) {
		this.data = data;
		return this;
	}

	/**
	 * <p>Getter for the field <code>contentType</code>.</p>
	 *
	 * @return a {@link org.springframework.http.MediaType} object.
	 */
	public MediaType getContentType() {
		return contentType;
	}

	/**
	 * <p>Setter for the field <code>contentType</code>.</p>
	 *
	 * @param contentType a {@link org.springframework.http.MediaType} object.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.actions.AjaxAction} object.
	 */
	public AjaxAction setContentType(final MediaType contentType) {
		this.contentType = contentType;
		return this;
	}

	/**
	 * <p>Getter for the field <code>dataType</code>.</p>
	 *
	 * @return a {@link org.springframework.http.MediaType} object.
	 */
	public MediaType getDataType() {
		return dataType;
	}

	/**
	 * <p>Setter for the field <code>dataType</code>.</p>
	 *
	 * @param dataType a {@link org.springframework.http.MediaType} object.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.actions.AjaxAction} object.
	 */
	public AjaxAction setDataType(final MediaType dataType) {
		this.dataType = dataType;
		return this;
	}

	/**
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return a {@link org.springframework.web.bind.annotation.RequestMethod} object.
	 */
	public RequestMethod getType() {
		return type;
	}

	/**
	 * <p>Setter for the field <code>type</code>.</p>
	 *
	 * @param type a {@link org.springframework.web.bind.annotation.RequestMethod} object.
	 *
	 * @return a {@link org.agatom.springatom.web.action.model.actions.AjaxAction} object.
	 */
	public AjaxAction setType(final RequestMethod type) {
		this.type = type;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(async, cache, data, contentType, dataType, type,
				url);
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

		AjaxAction that = (AjaxAction) o;

		return Objects.equal(this.async, that.async) &&
				Objects.equal(this.cache, that.cache) &&
				Objects.equal(this.data, that.data) &&
				Objects.equal(this.contentType, that.contentType) &&
				Objects.equal(this.dataType, that.dataType) &&
				Objects.equal(this.type, that.type) &&
				Objects.equal(this.url, that.url);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(async)
				.addValue(cache)
				.addValue(data)
				.addValue(contentType)
				.addValue(dataType)
				.addValue(type)
				.addValue(url)
				.toString();
	}
}
