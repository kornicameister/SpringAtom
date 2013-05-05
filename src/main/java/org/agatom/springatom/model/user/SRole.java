package org.agatom.springatom.model.user;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.hibernate.annotations.NaturalId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SRole")
@Table(name = "SRole")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSRole",
                updatable = false,
                nullable = false)
)
public class SRole extends PersistentObject {
    @NaturalId
    @Column(name = "role", updatable = false, unique = true, length = 50, nullable = false)
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SRole)) return false;
        if (!super.equals(o)) return false;

        SRole sRole = (SRole) o;

        return role.equals(sRole.role);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("role", role)
                .toString();
    }
}
