package org.agatom.springatom.model.meta;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SMetaData")
@Table(name = "SMetaData")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSMetaData",
                updatable = false,
                nullable = false))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "meta",
        discriminatorType = DiscriminatorType.STRING
)
abstract public class SMetaData extends PersistentObject {

    @NaturalId
    @Column(nullable = false,
            length = 20,
            unique = true,
            updatable = false,
            name = "type")
    private String type;

    protected SMetaData() {
        super();
    }

    public SMetaData(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .toString();
    }
}
