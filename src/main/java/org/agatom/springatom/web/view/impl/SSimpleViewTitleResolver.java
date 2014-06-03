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

package org.agatom.springatom.web.view.impl;

import org.agatom.springatom.web.view.SViewTitleResolver;
import org.agatom.springatom.web.view.bean.SViewTitle;
import org.agatom.springatom.web.view.exception.SViewTitleResolverException;
import org.springframework.beans.factory.annotation.Required;

/**
 * <p>SSimpleViewTitleResolver class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SSimpleViewTitleResolver
		implements SViewTitleResolver {
	/** Constant <code>DEFAULT_PAGE_TITLE_KEY="page.*.title"</code> */
	public static final String DEFAULT_PAGE_TITLE_KEY = "page.*.title";
	protected String paramName;
	protected String key;

	/** {@inheritDoc} */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.key == null) {
			this.key = DEFAULT_PAGE_TITLE_KEY;
		}
		this.validateKey(this.key);
	}

	/**
	 * <p>validateKey.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 *
	 * @throws org.agatom.springatom.web.view.exception.SViewTitleResolverException if any.
	 */
	protected void validateKey(final String key) throws SViewTitleResolverException {
		if (!key.contains("*")) {
			throw new SViewTitleResolverException("Key must contain character *");
		}
	}

	/** {@inheritDoc} */
	@Override
	public SViewTitle getViewTitle(final String viewName) {
		return null;
	}

	/**
	 * <p>Getter for the field <code>paramName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * <p>Setter for the field <code>paramName</code>.</p>
	 *
	 * @param paramName a {@link java.lang.String} object.
	 */
	@Required
	public void setParamName(final String paramName) {
		this.paramName = paramName;
	}

	/**
	 * <p>Getter for the field <code>key</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * <p>Setter for the field <code>key</code>.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 */
	public void setKey(final String key) {
		this.key = key;
	}
}
