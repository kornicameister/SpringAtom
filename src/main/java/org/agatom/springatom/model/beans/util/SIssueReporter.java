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

import com.google.common.base.Objects;
import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class SIssueReporter {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "mechanic",
            referencedColumnName = "idSMechanic",
            updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SMechanic mechanic;

    @Type(type = "timestamp")
    @Column(name = "assigned")
    private Date assigned;

    public SIssueReporter() {
        this.assigned = new Date();
    }

    public SIssueReporter(final SMechanic mechanic) {
        this();
        this.mechanic = mechanic;
    }

    public SMechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(final SMechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Date getAssigned() {
        return assigned;
    }

    public void setAssigned(final Date assigned) {
        this.assigned = assigned;
    }

    @Override
    public int hashCode() {
        int result = mechanic != null ? mechanic.hashCode() : 0;
        result = 31 * result + (assigned != null ? assigned.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SIssueReporter)) {
            return false;
        }

        SIssueReporter that = (SIssueReporter) o;

        return !(assigned != null ? !assigned.equals(that.assigned) : that.assigned != null) &&
                !(mechanic != null ? !mechanic.equals(that.mechanic) : that.mechanic != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("mechanic", mechanic)
                .add("assigned", assigned)
                .toString();
    }
}
