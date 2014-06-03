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

package org.agatom.springatom.server.service.vinNumber.decoder;

import org.agatom.springatom.server.service.vinNumber.exception.VinDecodingException;
import org.agatom.springatom.server.service.vinNumber.model.VinNumber;
import org.agatom.springatom.server.service.vinNumber.model.VinNumberData;
import org.springframework.cache.annotation.Cacheable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 08.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface VinDecoder {

	/**
	 * <p>decode.</p>
	 *
	 * @param vinNumber a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.service.vinNumber.model.VinNumberData} object.
	 *
	 * @throws org.agatom.springatom.server.service.vinNumber.exception.VinDecodingException if any.
	 */
	@Cacheable(value = "vinDecoder")
	VinNumberData decode(final String vinNumber) throws VinDecodingException;

	/**
	 * <p>decode.</p>
	 *
	 * @param vinNumber a {@link org.agatom.springatom.server.service.vinNumber.model.VinNumber} object.
	 *
	 * @return a {@link org.agatom.springatom.server.service.vinNumber.model.VinNumberData} object.
	 *
	 * @throws org.agatom.springatom.server.service.vinNumber.exception.VinDecodingException if any.
	 */
	@Cacheable(value = "vinDecoder")
	VinNumberData decode(final VinNumber vinNumber) throws VinDecodingException;

}
