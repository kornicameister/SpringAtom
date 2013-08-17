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

package org.agatom.springatom.server.model.beans.person.client;

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.meta.SClientProblemReportType;
import org.agatom.springatom.server.model.beans.meta.holder.SBasicMetaDataHolder;
import org.agatom.springatom.server.model.beans.util.SIssueReporter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class SClientProblemReport
        extends SBasicMetaDataHolder<SClientProblemReportType, Long> {

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "client", referencedColumnName = "idSClient")
    private SClient        client;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "appointment",
            referencedColumnName = "idSAppointment",
            updatable = false)
    private SAppointment   appointment;
    @NotEmpty
    @Length(min = 5, max = 444, message = "Insufficient problem description")
    @Column(name = "problem",
            nullable = false,
            length = 444)
    private String         problem;
    @NotNull
    @Embedded
    private SIssueReporter reporter;

    public SClient getClient() {
        return client;
    }

    public void setClient(final SClient client) {
        this.client = client;
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
}
