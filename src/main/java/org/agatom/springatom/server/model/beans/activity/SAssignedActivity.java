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

package org.agatom.springatom.server.model.beans.activity;

import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.activity.AssignedActivity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {@code SAssignedActivity} is also an {@link org.agatom.springatom.server.model.beans.activity.SActivity} but given with
 * the ability of being assigned to particular {@link org.agatom.springatom.server.model.beans.user.SUser}.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class SAssignedActivity<PK extends Serializable>
		extends SActivity<PK>
		implements AssignedActivity {
	private static final long serialVersionUID = -8389898294284589238L;
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_assignee", referencedColumnName = "idSUser", updatable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected SUser assignee;

	/** {@inheritDoc} */
	@Override
	public SUser getAssignee() {
		if (this.assignee == null) {
			this.assignee = new SUser();
		}
		return this.assignee;
	}

	/** {@inheritDoc} */
	@Override
	public AssignedActivity setAssignee(final SUser assignee) {
		this.assignee = assignee;
		return this;
	}
}
