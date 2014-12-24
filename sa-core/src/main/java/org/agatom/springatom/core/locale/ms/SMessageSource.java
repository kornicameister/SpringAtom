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

import org.agatom.springatom.core.util.Localized;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;

/**
 * <p>SMessageSource interface.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public interface SMessageSource
        extends MessageSource {

    /**
     * Allows to returns localized value of the {@link org.agatom.springatom.core.util.Localized#getMessageKey()}
     *
     * @param localized localized to get rb value for it
     * @param locale    current locale
     *
     * @return returned value
     */
    String getMessage(final Localized localized, final Locale locale);

    /**
     * Convenient version of {@link #getMessage(String, Object[], java.util.Locale)} where
     * {@code args} are assumed to be null
     *
     * @param key    key of the msg
     * @param locale current locale
     *
     * @return localized value
     */
    String getMessage(final String key, final Locale locale);

    /**
     * Works similarly to {@link #getMessage(String, java.util.Locale)}, difference is adding
     * {@code default value}.
     *
     * @param key        key of the msg
     * @param defaultMsg default msg
     * @param locale     current locale
     *
     * @return localized value
     */
    String getMessage(final String key, final String defaultMsg, final Locale locale);

    /**
     * Returns all possible messages that were loaded for this message source
     *
     * @param locale current locale
     *
     * @return all messages
     */
    Map<String, String> getAllMessages(final Locale locale);
}
