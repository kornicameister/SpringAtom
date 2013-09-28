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

package org.agatom.springatom.web.locale.impl;

import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.support.locale.SLocale;
import org.agatom.springatom.web.support.locale.SLocalizedMessage;
import org.agatom.springatom.web.support.locale.SLocalizedMessages;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SMessageSourceImpl
        extends ReloadableResourceBundleMessageSource
        implements SMessageSource {

    @Override
    public String getMessage(final String key, final Locale locale) {
        return this.getMessage(key, null, locale);
    }

    @Override
    public SLocalizedMessage getLocalizedMessage(final String key, final Locale locale) {
        return new SLocalizedMessage()
                .setKey(key)
                .setMessage(this.getMessage(key, locale))
                .setLocale(SLocale.fromLocale(locale));
    }

    @Override
    public SLocalizedMessages getLocalizedMessages(final Locale locale) {
        final SLocalizedMessages preferences = new SLocalizedMessages();
        final Set<String> keys = this.getKeys(locale);

        for (final String key : keys) {
            preferences.put(key, this.getMessage(key, locale), locale);
        }

        return preferences;
    }

    /**
     * Returns all the keys from all given resource bundles ({@link ReloadableResourceBundleMessageSource#setBasenames(String...)}
     *
     * @param locale
     *         locale
     *
     * @return set of keys
     */
    private Set<String> getKeys(final Locale locale) {
        return this.getMergedProperties(locale).getProperties().stringPropertyNames();
    }
}
