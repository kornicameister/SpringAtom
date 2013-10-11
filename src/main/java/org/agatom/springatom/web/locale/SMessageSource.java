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

package org.agatom.springatom.web.locale;

import org.agatom.springatom.web.locale.beans.SLocalizedMessage;
import org.agatom.springatom.web.locale.beans.SLocalizedMessages;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SMessageSource
        extends MessageSource {
    String getMessage(final String key, final Locale locale);

    SLocalizedMessages getLocalizedMessages(final Locale locale);

    SLocalizedMessages getLocalizedMessages(final String[] keys, final Locale locale, final boolean usePattern);

    SLocalizedMessage getLocalizedMessage(final String key, final Locale locale);


    public enum StorageMode {
        SINGLE,
        COMBINED
    }
}