package org.agatom.springatom.model.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = "SCT")
public class SContactType extends SMetaData {

    public SContactType() {
        super();
    }

    public SContactType(final String type) {
        super(type);
    }
}
