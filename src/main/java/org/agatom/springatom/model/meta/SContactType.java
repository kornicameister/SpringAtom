package org.agatom.springatom.model.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = SContactType.SHORT_NAME)
public class SContactType extends SMetaData {
    protected static final String SHORT_NAME = "SCT";

    public SContactType() {
        super();
    }

    public SContactType(final String type) {
        super(type);
    }
}
