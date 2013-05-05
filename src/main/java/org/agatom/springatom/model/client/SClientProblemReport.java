package org.agatom.springatom.model.client;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.agatom.springatom.model.SIssueReporter;
import org.agatom.springatom.model.appointment.SAppointment;
import org.agatom.springatom.model.meta.SClientProblemReportType;
import org.hibernate.annotations.NaturalId;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "client", referencedColumnName = "idSClient", updatable = false)
    private SClient client;

    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData")
    private SClientProblemReportType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "appointment", referencedColumnName = "idSAppointment", updatable = false)
    private SAppointment appointment;

    @Column(name = "problem", nullable = false, length = 444)
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
        result = 31 * result + reporter.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SClientProblemReport)) return false;
        if (!super.equals(o)) return false;

        SClientProblemReport that = (SClientProblemReport) o;

        return !(appointment != null ? !appointment.equals(that.appointment) : that.appointment != null)
                && client.equals(that.client) && problem.equals(that.problem)
                && reporter.equals(that.reporter) && type.equals(that.type);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("client", client)
                .add("type", type)
                .add("appointment", appointment)
                .add("problem", problem)
                .add("reporter", reporter)
                .toString();
    }
}
