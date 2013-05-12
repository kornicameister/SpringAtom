package org.agatom.springatom.model.util;

import com.google.common.base.Objects;
import org.agatom.springatom.model.mechanic.SMechanic;
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
    @JoinColumns(value = {
            @JoinColumn(name = "mechanic", referencedColumnName = "idSMechanic", updatable = false),
            @JoinColumn(name = "mechanicVersion", referencedColumnName = "version", updatable = false)
    })
    private SMechanic mechanic;

    @Type(type = "timestamp")
    @Column(name = "assigned")
    private Date assigned;

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
        if (this == o) return true;
        if (!(o instanceof SIssueReporter)) return false;

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
