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

package org.agatom.springatom.model.beans.client;

import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.appointment.SAppointment;
import org.agatom.springatom.model.beans.meta.SClientProblemReportType;
import org.agatom.springatom.model.beans.util.SIssueReporter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SClientProblemReport")
@Table(name = "SClientProblemReport")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSClientProblemReport",
                updatable = false,
                nullable = false)
)
public class SClientProblemReport extends PersistentObject {

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client",
            referencedColumnName = "idSClient",
            updatable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SClient client;

    @ManyToOne(optional = false,
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SClientProblemReportType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "appointment",
            referencedColumnName = "idSAppointment",
            updatable = false)
    private SAppointment appointment;

    @Column(name = "problem",
            nullable = false,
            length = 444)
    private String problem;

    @Embedded
    private SIssueReporter reporter;

    public SClient getClient() {
        return client;
    }

    public void setClient(final SClient client) {
        this.client = client;
    }

    public SClientProblemReportType getType() {
        return type;
    }

    public void setType(final SClientProblemReportType type) {
        this.type = type;
    }

    public SAppointment getAppointment() {
        return appointment;
    }

    public void setAppointment(final SAppointment appointment) {
        this.appointment = appointment;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(final String problem) {
        this.problem = problem;
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
        result = 31 * result + client.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (appointment != null ? appointment.hashCode() : 0);
        result = 31 * result + problem.hashCode();
        result = 31 * result + (reporter != null ? reporter.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SClientProblemReport)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SClientProblemReport that = (SClientProblemReport) o;

        return !(appointment != null ? !appointment.equals(that.appointment) : that.appointment != null) &&
                client.equals(that.client) && problem.equals(that.problem) &&
                !(reporter != null ? !reporter.equals(that.reporter) : that.reporter != null) &&
                type.equals(that.type);
    }
}
