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

package org.agatom.springatom.web.infopages.link;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.beans.WebBean;
import org.springframework.binding.collection.MapAdaptable;

import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 26.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageRequest implements WebBean, MapAdaptable<String, Object> {
	private static final long     serialVersionUID = -3121788324225630599L;
	public static final  String   BEAN_ID          = "infoPageContext";
	private              String   context          = null;
	private              Long     objectVersion    = null;
	private              Long     objectId         = null;
	private              Class<?> objectClass      = null;

	public InfoPageRequest setContext(final String context) {
		this.context = context;
		return this;
	}

	public InfoPageRequest setObjectVersion(final Long objectVersion) {
		this.objectVersion = objectVersion;
		return this;
	}

	public InfoPageRequest setObjectId(final Long objectId) {
		this.objectId = objectId;
		return this;
	}

	public InfoPageRequest setObjectClass(final Class<?> objectClass) {
		this.objectClass = objectClass;
		return this;
	}

	public String getContext() {
		return context;
	}

	public Long getObjectVersion() {
		return objectVersion;
	}

	public Long getObjectId() {
		return objectId;
	}

	public Class<?> getObjectClass() {
		return objectClass;
	}

	public boolean isValid() {
		return this.objectClass != null && (this.objectId != null && this.objectId > 0);
	}

	@Override
	public String getBeanId() {
		return BEAN_ID;
	}

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

	@Override
	public int hashCode() {
		return Objects.hashCode(context, objectVersion, objectId, objectClass);
	}

	@Override
	public Map<String, Object> asMap() {
		final Map<String, Object> map = Maps.newHashMap();
		map.put("id", this.getObjectId());
		map.put("version", this.getObjectVersion());
		map.put("domain", this.getObjectClass().getName());
		return map;
	}
}
