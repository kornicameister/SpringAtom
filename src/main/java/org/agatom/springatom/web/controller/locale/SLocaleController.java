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

package org.agatom.springatom.web.controller.locale;

import org.agatom.springatom.web.util.impl.ResourceLoaderAwareImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/app/locale")
public class SLocaleController
        implements MessageSourceAware {
    @Autowired
    protected ResourceLoaderAwareImpl resourceLoaderAwareImpl;
    private   MessageSource           messageSource;

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public Map<String, String> readLocales() {
        final Resource appResources = this.resourceLoaderAwareImpl.getResource("app/web.properties");
        return null;
    }

    @Override
    public void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
