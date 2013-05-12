package org.agatom.springatom.model.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = SNotificationType.SHORT_NAME)
public class SNotificationType extends SMetaData {
    protected static final String SHORT_NAME = "SNT";

    public SNotificationType() {
        super();
    }

    public SNotificationType(final String type) {
        super(type);
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }
}
