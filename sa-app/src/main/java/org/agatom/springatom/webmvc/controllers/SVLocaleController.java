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

package org.agatom.springatom.webmvc.controllers;

import com.google.common.collect.Sets;
import org.agatom.springatom.core.locale.SMessageSource;
import org.agatom.springatom.web.api.LocaleController;
import org.agatom.springatom.web.controller.SVDefaultController;
import org.agatom.springatom.web.exceptions.web.CTNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * {@code SVLocaleController} allows to access resource bundle information
 * as defined via {@link org.agatom.springatom.web.api.LocaleController}
 *
 * <p>SVLocaleController class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
public class SVLocaleController
        extends SVDefaultController
        implements LocaleController {
    private static final String         REGEX            = ",";
    @Value("#{applicationProperties['sa.locale.supports']}")
    private              String         supportedLocales = null;
    @Autowired
    private              SMessageSource messageSource    = null;

    @Override
    public Set<Locale> getAvailableLocales() {
        final String[] array = this.supportedLocales.split(REGEX);
        final Set<Locale> locales = Sets.newHashSet();

        for (final String locale : array) {
            final Locale loc = Locale.forLanguageTag(locale);
            locales.add(loc);
        }
        return locales;
    }

    @Override
    public Map<String, String> getAllMessages(final Locale locale) {
        return this.messageSource.getAllMessages(locale);
    }

    @Override
    public String getMessage(final String key, final Locale locale) throws Exception {
        try {
            final String message = this.messageSource.getMessage(key, locale);
            if (message == null || message.trim().equalsIgnoreCase(key)) {
                throw new NoSuchMessageException(key, locale);
            }
            return message;
        } catch (NoSuchMessageException exp) {
            throw new CTNotFoundException(exp);
        }
    }

}
