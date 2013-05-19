package org.agatom.springatom.model;

import com.google.common.base.Objects;
import org.agatom.springatom.model.util.VersionedId;

import javax.persistence.*;
import java.lang.annotation.Annotation;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class PersistentVersionedObject extends Persistent {

    @EmbeddedId
    private VersionedId pk;

    public PersistentVersionedObject() {
        super();
        this.pk = new VersionedId();
    }

    @Override
    protected void resolveIdColumnName() {
        for (Annotation annotation : this.getClass().getAnnotations()) {
            if (annotation instanceof AttributeOverride) {
                AttributeOverride attributeOverride = (AttributeOverride) annotation;
                if (attributeOverride.name().equals("pk.id")) {
                    Column column = attributeOverride.column();
                    this.idColumnName = column.name();
                    break;
                }
            } else if (annotation instanceof AttributeOverrides) {
                AttributeOverrides attributeOverrides = (AttributeOverrides) annotation;
                for (AttributeOverride attributeOverride : attributeOverrides.value()) {
                    if (attributeOverride.name().equals("pk.id")) {
                        Column column = attributeOverride.column();
                        this.idColumnName = column.name();
                        break;
                    }
                }
            }
        }
    }

    public Long getVersion() {
        return this.pk.getVersion();
    }

    @Override
    public int hashCode() {
        return pk.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentVersionedObject)) return false;

        PersistentVersionedObject that = (PersistentVersionedObject) o;

        return pk.equals(that.pk);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("pk", pk)
                .toString();
    }

    @Override
    public int compareTo(final Persistable o) {
        return this.getId().compareTo(o.getId());
    }

    public Long getId() {
        return this.pk.getId();
    }
}
