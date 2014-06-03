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

package org.agatom.springatom.server.model.types.issue;

import org.agatom.springatom.server.model.types.activity.AssignedActivity;

/**
 * {@code Issue} is an interface marking implementing classes as the Issue. Issue, by being also {@link
 * org.agatom.springatom.server.model.types.activity.AssignedActivity},
 * is an object with following properties:
 * <ol>
 * <li>assignee - {@link org.agatom.springatom.server.model.beans.user.SUser}</li>
 * <li>reporter - {@link org.agatom.springatom.server.model.beans.user.SUser}</li>
 * <li>assigned date - {@link org.joda.time.DateTime}</li>
 * <li>message - {@link java.lang.String}</li>
 * <li>type - {@link org.agatom.springatom.server.model.types.issue.IssueType}</li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Issue
		extends AssignedActivity {
	/**
	 * <p>getMessage.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getMessage();

	/**
	 * <p>setMessage.</p>
	 *
	 * @param issue a {@link java.lang.String} object.
	 */
	void setMessage(final String issue);

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.types.issue.IssueType} object.
	 */
	IssueType getType();

	/**
	 * <p>setType.</p>
	 *
	 * @param type a {@link org.agatom.springatom.server.model.types.issue.IssueType} object.
	 */
	void setType(final IssueType type);
}
