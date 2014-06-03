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

package org.agatom.springatom.web.locale.beans;

import org.springframework.binding.collection.MapAdaptable;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>LocalizedClassModel interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface LocalizedClassModel<T>
		extends Serializable, MapAdaptable<String, LocalizedClassAttribute> {
	/**
	 * <p>getSource.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	Class<T> getSource();

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * <p>isFound.</p>
	 *
	 * @return a boolean.
	 */
	boolean isFound();

	/**
	 * <p>getAttributes.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<LocalizedClassAttribute> getAttributes();

	/**
	 * <p>getLocalizedAttribute.</p>
	 *
	 * @param attributeName a {@link java.lang.String} object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLocalizedAttribute(final String attributeName);

	/**
	 * <p>hasAttributes.</p>
	 *
	 * @return a boolean.
	 */
	boolean hasAttributes();
}
