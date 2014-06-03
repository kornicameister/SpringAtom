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

package org.agatom.springatom.web.component.infopages.link;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.beans.WebBean;
import org.springframework.binding.collection.MapAdaptable;
import org.springframework.data.domain.Persistable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 26.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageRequest
		implements WebBean, MapAdaptable<String, Object> {
	private static final long                            serialVersionUID = -3121788324225630599L;
	private static final String                          BEAN_ID          = "infoPageContext";
	private              HttpServletRequest              request          = null;
	private              String                          context          = null;
	private              Long                            objectVersion    = null;
	private              Long                            objectId         = null;
	private              Class<? extends Persistable<?>> objectClass      = null;

	/**
	 * <p>Constructor for InfoPageRequest.</p>
	 *
	 * @param request a {@link javax.servlet.http.HttpServletRequest} object.
	 */
	public InfoPageRequest(final HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * <p>Getter for the field <code>context</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getContext() {
		return context;
	}

	/**
	 * <p>Setter for the field <code>context</code>.</p>
	 *
	 * @param context a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest} object.
	 */
	public InfoPageRequest setContext(final String context) {
		this.context = context;
		return this;
	}

	/**
	 * <p>isValid.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isValid() {
		return this.objectClass != null && (this.objectId != null && this.objectId > 0);
	}

	/**
	 * <p>getURI.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getURI() {
		return this.request.getRequestURI();
	}

	/**
	 * <p>Getter for the field <code>request</code>.</p>
	 *
	 * @return a {@link javax.servlet.http.HttpServletRequest} object.
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/** {@inheritDoc} */
	@Override
	public String getBeanId() {
		return BEAN_ID;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(context, objectVersion, objectId, objectClass);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InfoPageRequest that = (InfoPageRequest) o;

		return Objects.equal(this.context, that.context) &&
				Objects.equal(this.objectVersion, that.objectVersion) &&
				Objects.equal(this.objectId, that.objectId) &&
				Objects.equal(this.objectClass, that.objectClass);
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, Object> asMap() {
		final Map<String, Object> map = Maps.newHashMap();
		map.put("id", this.getObjectId());
		map.put("version", this.getObjectVersion());
		map.put("domain", this.getObjectClass().getName());
		return map;
	}

	/**
	 * <p>Getter for the field <code>objectId</code>.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * <p>Setter for the field <code>objectId</code>.</p>
	 *
	 * @param objectId a {@link java.lang.Long} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest} object.
	 */
	public InfoPageRequest setObjectId(final Long objectId) {
		this.objectId = objectId;
		return this;
	}

	/**
	 * <p>Getter for the field <code>objectVersion</code>.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	public Long getObjectVersion() {
		return objectVersion;
	}

	/**
	 * <p>Setter for the field <code>objectVersion</code>.</p>
	 *
	 * @param objectVersion a {@link java.lang.Long} object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest} object.
	 */
	public InfoPageRequest setObjectVersion(final Long objectVersion) {
		this.objectVersion = objectVersion;
		return this;
	}

	/**
	 * <p>Getter for the field <code>objectClass</code>.</p>
	 *
	 * @param <T> a T object.
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Persistable<?>> Class<T> getObjectClass() {
		return (Class<T>) objectClass;
	}

	/**
	 * <p>Setter for the field <code>objectClass</code>.</p>
	 *
	 * @param objectClass a {@link java.lang.Class} object.
	 * @param <T>         a T object.
	 *
	 * @return a {@link org.agatom.springatom.web.component.infopages.link.InfoPageRequest} object.
	 */
	public <T extends Persistable<?>> InfoPageRequest setObjectClass(final Class<T> objectClass) {
		this.objectClass = objectClass;
		return this;
	}
}
