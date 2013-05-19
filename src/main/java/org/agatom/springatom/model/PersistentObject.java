package org.agatom.springatom.model;

import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Annotation;

/**
 * MappedSuperclass for entities, aggregates all shared properties
 * that can be found in spring.database
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@MappedSuperclass
abstract public class PersistentObject extends Persistent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "hilo")
    private Long id = null;

    public PersistentObject() {
        super();
    }

    @Override
    protected void resolveIdColumnName() {
        for (Annotation annotation : this.getClass().getAnnotations()) {
            if (annotation instanceof AttributeOverride) {
                AttributeOverride attributeOverride = (AttributeOverride) annotation;
                if (attributeOverride.name().equals("id")) {
                    Column column = attributeOverride.column();
                    this.idColumnName = column.name();
                    break;
                }
            } else if (annotation instanceof AttributeOverrides) {
                AttributeOverrides attributeOverrides = (AttributeOverrides) annotation;
                for (AttributeOverride attributeOverride : attributeOverrides.value()) {
                    if (attributeOverride.name().equals("id")) {
                        Column column = attributeOverride.column();
                        this.idColumnName = column.name();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int compareTo(final Persistable object) {
        return this.getId().compareTo(object.getId());
    }

    @Override
    public final Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentObject)) return false;

        PersistentObject that = (PersistentObject) o;

        return this.id.equals(that.id);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
