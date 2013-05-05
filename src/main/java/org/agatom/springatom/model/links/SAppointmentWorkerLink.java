package org.agatom.springatom.model.links;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentVersionedObject;
import org.agatom.springatom.model.SIssueReporter;
import org.agatom.springatom.model.appointment.SAppointment;
import org.agatom.springatom.model.mechanic.SMechanic;
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
