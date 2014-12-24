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

package org.agatom.springatom.core.locale.ms;

import com.google.common.collect.Maps;
import org.agatom.springatom.core.util.Localized;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * <p>SMessageSourceImpl class.</p>
 * {@code SMessageSourceImpl} is an implementation of the {@link SMessageSource}
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class SAMessageSource
        extends ReloadableResourceBundleMessageSource
        implements SMessageSource {

    /** {@inheritDoc} */
    @Override
    public String getMessage(final Localized localized, final Locale locale) {
        return this.getMessage(localized.getMessageKey(), locale);
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage(final String key, final Locale locale) {
        return this.getMessage(key, (Object[]) null, locale);
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage(final String key, final String defaultMsg, final Locale locale) {
        final String msg = this.getMessage(key, locale);
        if (!StringUtils.hasText(msg) || msg.equalsIgnoreCase(key)) {
            return defaultMsg;
        }
        return msg;
    }

    @Override
    public Map<String, String> getAllMessages(final Locale locale) {
        final Properties properties = this.getMergedProperties(locale).getProperties();
        final Map<String, String> map = Maps.newHashMapWithExpectedSize(properties.size());
        for (final Object propKey : properties.keySet()) {
            map.put(propKey.toString(), properties.get(propKey).toString());
        }
        return map;
    }

}
