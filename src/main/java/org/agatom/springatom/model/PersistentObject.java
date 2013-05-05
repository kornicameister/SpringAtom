package org.agatom.springatom.model;

import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * MappedSuperclass for entities, aggregates all shared properties
 * that can be found in spring.database
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@MappedSuperclass
public abstract class PersistentObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "hilo")
    private Long id = null;

    public PersistentObject() {
        super();
    }

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
