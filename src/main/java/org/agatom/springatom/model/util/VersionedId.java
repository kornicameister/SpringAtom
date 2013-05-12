package org.agatom.springatom.model.util;

import com.google.common.base.Objects;
import org.hibernate.envers.RevisionNumber;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class VersionedId implements Serializable {

    private Long id = null;

    @RevisionNumber
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionedId)) return false;

        VersionedId that = (VersionedId) o;

        return id.equals(that.id) && version.equals(that.version);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .toString();
    }
}
