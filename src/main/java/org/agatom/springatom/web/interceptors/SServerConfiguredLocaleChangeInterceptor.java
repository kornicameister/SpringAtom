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

package org.agatom.springatom.web.interceptors;

import com.google.common.base.Preconditions;
import org.agatom.springatom.server.SServerConfigured;
import org.agatom.springatom.server.SpringAtomServer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.annotation.PostConstruct;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SServerConfiguredLocaleChangeInterceptor
        extends LocaleChangeInterceptor
        implements SServerConfigured {
    private static final Logger LOGGER = Logger.getLogger(SServerConfiguredLocaleChangeInterceptor.class);
    @Autowired
    private SpringAtomServer server;

    @Override
    @PostConstruct
    public void configure() {
        final String paramName = this.server.getProperty("sa.locale.requestParam");
        LOGGER.trace(String.format("Initialized with paramName=%s", paramName));
        this.setParamName(paramName);
    }

    @Override
    @Required
    public void setConfigure(final String[] params) {
        Preconditions.checkArgument(params.length == 1);
    }
}
