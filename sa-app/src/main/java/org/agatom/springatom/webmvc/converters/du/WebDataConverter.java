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

package org.agatom.springatom.webmvc.converters.du;

import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.webmvc.converters.du.exception.WebConverterException;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface WebDataConverter<T extends Serializable> {

    /**
     * Returns {@code T} value for given {@code key} out of {@code value}
     *
     * @param key         the key
     * @param value       the value
     * @param persistable source of the value
     * @param request     request
     *
     * @return the converted value
     *
     * @throws org.agatom.springatom.webmvc.converters.du.exception.WebConverterException if any.
     */
    T convert(String key, Object value, Persistable<?> persistable, ComponentDataRequest request) throws WebConverterException;
}
