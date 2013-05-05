package org.agatom.springatom.model;

import com.google.common.base.Objects;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class PersistentVersionedObject extends PersistentObject {

    @Version
    @Column(name = "version")
    private Long version;

    @Type(type = "timestamp")
    @Column(name = "updatedOn")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedOn;

    protected PersistentVersionedObject() {
        super();
        this.updatedOn = new Date();
    }

    public Long getVersion() {
        return version;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentVersionedObject)) return false;
        if (!super.equals(o)) return false;

        PersistentVersionedObject that = (PersistentVersionedObject) o;

        return !(version != null ? !version.equals(that.version) : that.version != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("version", version)
                .add("updatedOn", updatedOn)
                .toString();
    }
}
