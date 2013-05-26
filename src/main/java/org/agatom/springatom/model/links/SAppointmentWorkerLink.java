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

package org.agatom.springatom.model.links;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentVersionedObject;
import org.agatom.springatom.model.appointment.SAppointment;
import org.agatom.springatom.model.mechanic.SMechanic;
import org.agatom.springatom.model.util.SIssueReporter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SAppointmentWorkerLink")
@Table(name = "SAppointmentWorkerLink")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSAppointmentWorkerLink",
                updatable = false,
                nullable = false)
)
public class SAppointmentWorkerLink extends PersistentVersionedObject {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment", referencedColumnName = "idSAppointment", updatable = false)
    private SAppointment appointment;

    @Audited
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "assignee", referencedColumnName = "idSMechanic")
    private SMechanic assignee;

    @Embedded
    private SIssueReporter reporter;

    public SAppointment getAppointment() {
        return appointment;
    }

    public void setAppointment(final SAppointment appointment) {
        this.appointment = appointment;
    }

    public SMechanic getAssignee() {
        return assignee;
    }

    public void setAssignee(final SMechanic assignee) {
        this.assignee = assignee;
    }

    public SIssueReporter getReporter() {
        return reporter;
    }

    public void setReporter(final SIssueReporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (appointment != null ? appointment.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (reporter != null ? reporter.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SAppointmentWorkerLink)) return false;
        if (!super.equals(o)) return false;

        SAppointmentWorkerLink that = (SAppointmentWorkerLink) o;

        return !(appointment != null ? !appointment.equals(that.appointment) : that.appointment != null) &&
                !(assignee != null ? !assignee.equals(that.assignee) : that.assignee != null) &&
                !(reporter != null ? !reporter.equals(that.reporter) : that.reporter != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("appointment", appointment)
                .add("assignee", assignee)
                .add("reporter", reporter)
                .toString();
    }
}
