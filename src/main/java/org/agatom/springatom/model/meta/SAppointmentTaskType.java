package org.agatom.springatom.model.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = "SAT")
public class SAppointmentTaskType extends SMetaData {

    public SAppointmentTaskType() {
        super();
    }

    public SAppointmentTaskType(final String type) {
        super(type);
    }
}
