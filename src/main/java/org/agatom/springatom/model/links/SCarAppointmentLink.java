package org.agatom.springatom.model.links;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.agatom.springatom.model.appointment.SAppointment;
import org.agatom.springatom.model.car.SCar;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SCarAppointmentLink")
@Table(name = "SCarAppointmentLink")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSCarAppointmentLink",
                updatable = false,
                nullable = false)
)
public class SCarAppointmentLink extends PersistentObject {

    @ManyToOne(optional = false)
    @JoinColumns(value = {
            @JoinColumn(name = "car", referencedColumnName = "idScar"),
            @JoinColumn(name = "carVersion", referencedColumnName = "version")
    })
    private SCar car;

    @ManyToOne(optional = false)
    @JoinColumn(name = "appointment", referencedColumnName = "idSAppointment")
    private SAppointment appointment;

    public SCar getCar() {
        return car;
    }

    public void setCar(final SCar car) {
        this.car = car;
    }

    public SAppointment getAppointment() {
        return appointment;
    }

    public void setAppointment(final SAppointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (appointment != null ? appointment.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SCarAppointmentLink)) return false;
        if (!super.equals(o)) return false;

        SCarAppointmentLink that = (SCarAppointmentLink) o;

        return !(appointment != null ? !appointment.equals(that.appointment) : that.appointment != null) &&
                !(car != null ? !car.equals(that.car) : that.car != null) &&
                !(car != null ? !car.equals(that.car) : that.car != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("car", car)
                .add("carRevision", car)
                .add("appointment", appointment)
                .toString();
    }
}
