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

import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.view.bean.SViewTitle;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SResourceBundleViewTitleResolver
        extends SSimpleViewTitleResolver {
    protected String[]       keys;
    protected SMessageSource messageSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (final String key : this.keys) {
            this.validateKey(key);
        }
    }

    @Required
    public void setKeys(final String[] keys) {
        this.keys = keys;
    }

    @Override
    public SViewTitle getViewTitle(final String viewName) {
        if (viewName == null) {
            return new SViewTitle();
        }
        SViewTitle viewTitle = null;
        for (final String key : this.keys) {
            if (viewTitle != null) {
                break;
            }

            final String tmpKey = key.replace("*", viewName);
            String result = null;

            try {
                result = this.messageSource.getMessage(tmpKey, LocaleContextHolder.getLocale());
            } finally {
                if (result != null) {
                    viewTitle = new SViewTitle().setParamName(this.paramName).setParamValue(result)
                                                .setViewKey(viewName);
                }
            }
        }
        return viewTitle;
    }

    @Required
    public void setMessageSource(final SMessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
