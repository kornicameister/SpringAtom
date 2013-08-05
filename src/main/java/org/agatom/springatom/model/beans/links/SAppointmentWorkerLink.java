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

package org.agatom.springatom.model.beans.links;

import org.agatom.springatom.model.beans.PersistentVersionedObject;
import org.agatom.springatom.model.beans.appointment.SAppointment;
import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.agatom.springatom.model.beans.util.SIssueReporter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
public class SAppointmentWorkerLink
        extends PersistentVersionedObject {

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "appointment", referencedColumnName = "idSAppointment", updatable = false)
    private SAppointment   appointment;
    @Audited
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignee", referencedColumnName = "idSMechanic")
    private SMechanic      assignee;
    @Embedded
    private SIssueReporter reporter;

    public SAppointment getAppointment() {
        return appointment;
    }

    public SAppointmentWorkerLink setAppointment(final SAppointment appointment) {
        this.appointment = appointment;
        return this;
    }

    public SMechanic getAssignee() {
        return assignee;
    }

    public SAppointmentWorkerLink setAssignee(final SMechanic assignee) {
        this.assignee = assignee;
        return this;
    }

    public SIssueReporter getReporter() {
        return reporter;
    }

    public SAppointmentWorkerLink setReporter(final SIssueReporter reporter) {
        this.reporter = reporter;
        return this;
    }

}
