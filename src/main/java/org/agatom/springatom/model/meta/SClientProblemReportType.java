package org.agatom.springatom.model.meta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@DiscriminatorValue(value = "SCPR")
public class SClientProblemReportType extends SMetaData {

    public SClientProblemReportType() {
        super();
    }

    public SClientProblemReportType(final String type) {
        super(type);
    }
}
