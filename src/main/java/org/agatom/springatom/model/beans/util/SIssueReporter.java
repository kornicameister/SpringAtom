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

package org.agatom.springatom.model.beans.util;

import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class SIssueReporter {
    private static final String DATE_TIME_TYPE = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "mechanic",
            referencedColumnName = "idSMechanic",
            updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SMechanic mechanic;
    @Type(type = DATE_TIME_TYPE)
    @Column(name = "assigned", nullable = false)
    private DateTime  assigned;

    public SIssueReporter(final SMechanic mechanic) {
        this();
        this.mechanic = mechanic;
    }

    public SIssueReporter() {
        this.assigned = DateTime.now();
    }

    public SMechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(final SMechanic mechanic) {
        this.mechanic = mechanic;
    }

    public DateTime getAssigned() {
        return null == this.assigned ? null : new DateTime(this.assigned);
    }

    public void setAssigned(final DateTime assigned) {
        this.assigned = assigned;
    }

}
