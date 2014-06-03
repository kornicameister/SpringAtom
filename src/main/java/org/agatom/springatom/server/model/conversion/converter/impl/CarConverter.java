/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.server.model.conversion.converter.impl;

import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.conversion.annotation.PersistableConverterUtility;
import org.agatom.springatom.server.model.conversion.converter.PersistableConverterImpl;

/**
 * {@code CarConverter} handles default conversion from {@link org.agatom.springatom.server.model.beans.car.SCar} to {@link java.lang.String}.
 * Default representation returns following fields:
 * <ul>
 * <li>{@link org.agatom.springatom.server.model.beans.car.SCar#getLicencePlate()}</li>
 * <li>{@link org.agatom.springatom.server.model.beans.car.SCar#getVinNumber()}</li>
 * <li>{@link org.agatom.springatom.server.model.beans.car.SCar#getOwner()}</li>
 * </ul>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@PersistableConverterUtility
public class CarConverter
		extends PersistableConverterImpl<SCar> {

	/** {@inheritDoc} */
	@Override
	public String convert(final SCar source) {
		return String.format("%s\n%s\n%s",
				this.conversionService.convert(source.getLicencePlate(), STRING_CLASS),
				this.conversionService.convert(source.getVinNumber(), STRING_CLASS),
				this.conversionService.convert(source.getOwner(), STRING_CLASS));
	}

}
