package org.agatom.springatom.model.meta;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table(name = "SNotificationType")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSNotificationType",
                updatable = false,
                insertable = true,
                nullable = false))
public class SNotificationType extends STypeData {


    public SNotificationType() {
        super();
    }

    public SNotificationType(final Long id, final String type) {
        super(type);
    }
}
