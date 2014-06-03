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

package org.agatom.springatom.web.view.bean;

import com.google.common.base.Objects;
import org.agatom.springatom.web.beans.WebBean;

/**
 * {@code SViewTitle} bean is a <b>POJO</b> class that embeds given page title configuration
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SViewTitle
		implements WebBean {
	private final static String BEAN_ID          = "pageTitleBean";
	private static final long   serialVersionUID = 574303147624174474L;
	private String paramName;
	private String paramValue;
	private String viewKey;

	/**
	 * <p>Getter for the field <code>paramName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getParamName() {
		return this.paramName;
	}

	/**
	 * <p>Setter for the field <code>paramName</code>.</p>
	 *
	 * @param paramName a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.view.bean.SViewTitle} object.
	 */
	public SViewTitle setParamName(final String paramName) {
		this.paramName = paramName;
		return this;
	}

	/**
	 * <p>Getter for the field <code>paramValue</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getParamValue() {
		return this.paramValue;
	}

	/**
	 * <p>Setter for the field <code>paramValue</code>.</p>
	 *
	 * @param paramValue a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.view.bean.SViewTitle} object.
	 */
	public SViewTitle setParamValue(final String paramValue) {
		this.paramValue = paramValue;
		return this;
	}

	/**
	 * <p>Getter for the field <code>viewKey</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getViewKey() {
		return this.viewKey;
	}

	/**
	 * <p>Setter for the field <code>viewKey</code>.</p>
	 *
	 * @param viewKey a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.view.bean.SViewTitle} object.
	 */
	public SViewTitle setViewKey(final String viewKey) {
		this.viewKey = viewKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(paramName, paramValue, viewKey);
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

		SViewTitle that = (SViewTitle) o;

		return Objects.equal(this.paramName, that.paramName) &&
				Objects.equal(this.paramValue, that.paramValue) &&
				Objects.equal(this.viewKey, that.viewKey);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(paramName)
				.addValue(paramValue)
				.addValue(viewKey)
				.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String getBeanId() {
		return BEAN_ID;
	}
}
