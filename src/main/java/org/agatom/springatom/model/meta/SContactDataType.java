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
@Table(name = "SContactDataType")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSContactDataType",
                updatable = false,
                insertable = true,
                nullable = false))
public class SContactDataType extends STypeData {


    public SContactDataType() {
        super();
    }

    public SContactDataType(final Long id, final String type) {
        super(type);
    }
}
