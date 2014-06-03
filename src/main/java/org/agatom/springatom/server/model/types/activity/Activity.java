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

package org.agatom.springatom.server.model.types.activity;

import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.PersistentEmbeddable;
import org.joda.time.DateTime;

/**
 * <p>Activity interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Activity
		extends PersistentEmbeddable {

	/**
	 * <p>getReporter.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 */
	SUser getReporter();

	/**
	 * <p>setReporter.</p>
	 *
	 * @param user a {@link org.agatom.springatom.server.model.beans.user.SUser} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.activity.Activity} object.
	 */
	Activity setReporter(SUser user);

	/**
	 * <p>getAssigned.</p>
	 *
	 * @return a {@link org.joda.time.DateTime} object.
	 */
	DateTime getAssigned();

	/**
	 * <p>setAssigned.</p>
	 *
	 * @param assigned a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.activity.Activity} object.
	 */
	Activity setAssigned(DateTime assigned);

	/**
	 * <p>getComment.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getComment();

	/**
	 * <p>setComment.</p>
	 *
	 * @param comment a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.activity.Activity} object.
	 */
	Activity setComment(String comment);
}
