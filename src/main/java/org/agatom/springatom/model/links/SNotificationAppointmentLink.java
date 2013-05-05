package org.agatom.springatom.model.links;

import com.google.common.base.Objects;
import org.agatom.springatom.model.appointment.SAppointment;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = "SNAL")
public class SNotificationAppointmentLink extends SNotificationLink {

    @ManyToOne
    @JoinColumn(name = "appointment", referencedColumnName = "idSAppointment")
    private SAppointment appointment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workerLink", referencedColumnName = "idSAppointmentWorkerLink", updatable = false)
    private SAppointmentWorkerLink workerLink;

    public SAppointment getAppointment() {
        return appointment;
    }

    public void setAppointment(final SAppointment appointment) {
        this.appointment = appointment;
    }

    public SAppointmentWorkerLink getWorkerLink() {
        return workerLink;
    }

    public void setWorkerLink(final SAppointmentWorkerLink workerLink) {
        this.workerLink = workerLink;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (appointment != null ? appointment.hashCode() : 0);
        result = 31 * result + (workerLink != null ? workerLink.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SNotificationAppointmentLink)) return false;
        if (!super.equals(o)) return false;

        SNotificationAppointmentLink that = (SNotificationAppointmentLink) o;

        return !(appointment != null ? !appointment.equals(that.appointment) : that.appointment != null) &&
                !(workerLink != null ? !workerLink.equals(that.workerLink) : that.workerLink != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("appointment", appointment)
                .add("workerLink", workerLink)
                .toString();
    }
}
