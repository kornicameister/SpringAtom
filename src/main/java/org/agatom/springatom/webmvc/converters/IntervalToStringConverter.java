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

import com.google.common.base.Preconditions;
import org.joda.time.Interval;
import org.joda.time.PeriodType;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>IntervalToStringConverter class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class IntervalToStringConverter
		implements Converter<Interval, String>,
		ConditionalConverter {

	/** {@inheritDoc} */
	@Override
	public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
		return sourceType.getType().isAssignableFrom(Interval.class)
				&& targetType.getType().isAssignableFrom(String.class);
	}

	/** {@inheritDoc} */
	@Override
	public String convert(final Interval source) {
		Preconditions.checkNotNull(source);
		return String.valueOf(source.toPeriod(PeriodType.minutes()).getMinutes());
	}
}
