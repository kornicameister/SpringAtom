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

import org.agatom.springatom.web.support.locale.SLocalizedPreferences;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SMessageSource
        extends MessageSource {

    void setStorageMode(final StorageMode mode);

    void setSplitBy(String splitBy);

    String getMessage(final String key, final Locale locale);

    SLocalizedPreferences getAll(final Locale locale);

    SLocalizedPreferences getAll(final String requestKey, final Locale locale);

    public enum StorageMode {
        SINGLE,
        COMBINED
    }
}
