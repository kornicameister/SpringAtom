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

package org.agatom.springatom.server.model.types.notification;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * {@code SNotification} describes the functionality of simple notification
 * which is sent to the given {@code principal}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SConcerningNotification<SN_C extends Persistable<PK>, SN_T extends Persistable<PK>, PK extends Serializable>
		extends STargetedNotification<SN_T, PK> {

	/**
	 * <p>getConcerning.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<SN_C> getConcerning();

	/**
	 * <p>setConcerning.</p>
	 *
	 * @param concerning a {@link java.util.Collection} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.notification.SConcerningNotification} object.
	 */
	SConcerningNotification<SN_C, SN_T, PK> setConcerning(final Collection<SN_C> concerning);

}
