package org.agatom.springatom.model.appointment;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SAppointment")
@Table(name = "SAppointment")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSAppointment",
                updatable = false,
                nullable = false)
)
public class SAppointment extends PersistentObject {

    @Type(type = "timestamp")
    @Column(name = "startDate")
    private Date startDate;

    @Type(type = "timestamp")
    @Column(nullable = false,
            name = "endDate")
    private Date endDate;

    @Type(type = "time")
    @Column(nullable = false,
            name = "startTime")
    private Time startTime;

    @Type(type = "time")
    @Column(nullable = false,
            name = "endTime")
    private Time endTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appointment")
    private Set<SAppointmentTask> tasks;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(final Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(final Time endTime) {
        this.endTime = endTime;
    }

    public Set<SAppointmentTask> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<SAppointmentTask> tasks) {
        this.tasks = tasks;
    }

    public boolean addAppointment(final SAppointmentTask sAppointmentTask) {
        if (this.tasks == null) {
            this.tasks = new HashSet<>();
        }
        return this.tasks.add(sAppointmentTask);
    }

    public boolean removeAppointment(final SAppointmentTask sAppointmentTask) {
        return this.tasks != null && this.tasks.remove(sAppointmentTask);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SAppointment)) return false;
        if (!super.equals(o)) return false;

        SAppointment that = (SAppointment) o;

        return !(endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) &&
                !(endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) &&
                !(startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) &&
                !(startTime != null ? !startTime.equals(that.startTime) : that.startTime != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .toString();
    }
}
