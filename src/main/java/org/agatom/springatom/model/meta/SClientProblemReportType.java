package org.agatom.springatom.model.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = SClientProblemReportType.SHORT_NAME)
public class SClientProblemReportType extends SMetaData {
    protected static final String SHORT_NAME = "SCPR";

    public SClientProblemReportType() {
        super();
    }

    public SClientProblemReportType(final String type) {
        super(type);
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }
}
