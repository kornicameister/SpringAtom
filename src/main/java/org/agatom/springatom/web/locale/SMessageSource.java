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

import org.agatom.springatom.core.util.LocalizationAware;
import org.agatom.springatom.core.util.Localized;
import org.agatom.springatom.web.locale.beans.LocalizedClassAttribute;
import org.agatom.springatom.web.locale.beans.LocalizedClassModel;
import org.agatom.springatom.web.locale.beans.SLocalizedMessage;
import org.agatom.springatom.web.locale.beans.SLocalizedMessages;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public interface SMessageSource
		extends MessageSource {

	@Cacheable(value = "org.agatom.springatom.localizedClassesCache")
	<T> LocalizedClassModel<T> getLocalizedClassModel(final Class<T> clazz, final Locale locale);

	@Cacheable(value = "org.agatom.springatom.localizedClassesCache")
	<T> LocalizedClassAttribute getLocalizedClassAttribute(final Class<T> clazz, final String attributeName, final Locale locale);

	<LA extends LocalizationAware> LA localize(final LA localizationAware, final Locale locale);

	String getMessage(final Localized localized, final Locale locale);

	String getMessage(final String key, final Locale locale);

	SLocalizedMessages getLocalizedMessages(final Locale locale);

	SLocalizedMessages getLocalizedMessages(final String[] keys, final Locale locale, final boolean usePattern);

	SLocalizedMessage getLocalizedMessage(final String key, final Locale locale);

	public enum StorageMode {
		SINGLE,
		COMBINED
	}
}