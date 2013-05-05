package org.agatom.springatom.model;

import com.google.common.base.Objects;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.RevisionNumber;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class PersistentVersionedObject extends PersistentObject {

    @NaturalId
    @RevisionNumber
    @GeneratedValue
    @Column(name = "version", nullable = false)
    private Long version;

    protected PersistentVersionedObject() {
        super();
    }

    public Long getVersion() {
        return version;
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
                .toString();
    }
}
