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

package org.agatom.springatom.webmvc.converters;

import org.agatom.springatom.server.model.types.user.SRole;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;

import java.util.regex.Pattern;

/**
 * <p>SSecurityAuthorityEnumConverted class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SSecurityAuthorityEnumConverted
		implements Converter<String, SRole>,
		ConditionalConverter {

	/** Constant <code>ERR_MSG="roleName can not be null"</code> */
	public static final String ERR_MSG = "roleName can not be null";

	/** {@inheritDoc} */
	@Override
	public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
		return sourceType.getType().isAssignableFrom(String.class)
				&& targetType.getType().isAssignableFrom(SRole.class);
	}

	/** {@inheritDoc} */
	@Override
	public SRole convert(final String roleName) {
		if (roleName != null) {

			final Pattern pattern = Pattern.compile("^ROLE_\\w+$", Pattern.CASE_INSENSITIVE);

			if (pattern.matcher(roleName).matches()) {
				return SRole.valueOf(roleName);
			} else {
				return SRole.valueOf(String.format("ROLE_%s", roleName.toUpperCase()));
			}
		}
		throw new IllegalArgumentException(ERR_MSG);
	}
}
