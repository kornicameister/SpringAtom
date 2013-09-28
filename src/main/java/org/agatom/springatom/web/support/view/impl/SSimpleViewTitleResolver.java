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

package org.agatom.springatom.web.support.view.impl;

import org.agatom.springatom.web.support.view.SViewTitleResolver;
import org.agatom.springatom.web.support.view.bean.SViewTitle;
import org.agatom.springatom.web.support.view.exception.SViewTitleResolverException;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SSimpleViewTitleResolver
        implements SViewTitleResolver {
    public static final String DEFAULT_PAGE_TITLE_KEY = "page.*.title";
    protected String paramName;
    protected String key;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.key == null) {
            this.key = DEFAULT_PAGE_TITLE_KEY;
        }
        this.validateKey(this.key);
    }

    protected void validateKey(final String key) throws SViewTitleResolverException {
        if (!key.contains("*")) {
            throw new SViewTitleResolverException("Key must contain character *");
        }
    }

    @Override
    public SViewTitle getViewTitle(final String viewName) {
        return null;
    }

    public String getParamName() {
        return paramName;
    }

    @Required
    public void setParamName(final String paramName) {
        this.paramName = paramName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }
}
