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

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.core.util.LocalizationAware;
import org.agatom.springatom.core.util.Localized;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.locale.beans.SLocale;
import org.agatom.springatom.web.locale.beans.SLocalizedMessage;
import org.agatom.springatom.web.locale.beans.SLocalizedMessages;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SMessageSourceImpl
        extends ReloadableResourceBundleMessageSource
        implements SMessageSource {

    @Override
    public <LA extends LocalizationAware> LA localize(final LA localizationAware, final Locale locale) {
        final String messageKey = localizationAware.getMessageKey();
        final String msg = this.getMessage(messageKey, locale);
        if (StringUtils.hasText(msg)) {
            localizationAware.setValueForMessageKey(msg);
        }
        return localizationAware;
    }

    @Override
    public String getMessage(final Localized localized, final Locale locale) {
        return this.getMessage(localized.getMessageKey(), locale);
    }

    @Override
    public String getMessage(final String key, final Locale locale) {
        return this.getMessage(key, null, locale);
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

    @Override
    public SLocalizedMessages getLocalizedMessages(final String[] keys, final Locale locale, final boolean usePattern) {
        if (!usePattern) {
            return this.getLocalizedMessages(locale);
        }
        final Set<String> allKeys = this.getKeys(locale);
        final SLocalizedMessages messages = new SLocalizedMessages();

        for (final String key : keys) {
            final Pattern pattern = Pattern.compile(key, Pattern.CASE_INSENSITIVE);
            {
                final Set<String> filteredMsgKeys = FluentIterable
                        .from(allKeys)
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean apply(@Nullable final String input) {
                                return pattern.matcher(input).matches();
                            }
                        })
                        .toSet();
                for (final String msgKey : filteredMsgKeys) {
                    messages.put(this.getLocalizedMessage(msgKey, locale));
                }
            }
        }

        return messages;
    }

    @Override
    public SLocalizedMessage getLocalizedMessage(final String key, final Locale locale) {
        return new SLocalizedMessage()
                .setKey(key)
                .setMessage(this.getMessage(key, locale))
                .setLocale(SLocale.fromLocale(locale));
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
