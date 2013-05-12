package org.agatom.springatom.model.user;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SUserToRole")
@Table(name = "SUserToRole")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user.pk.id", joinColumns = @JoinColumn(name = "userId")),
        @AssociationOverride(name = "pk.user.pk.version", joinColumns = @JoinColumn(name = "userVersion")),
        @AssociationOverride(name = "pk.role", joinColumns = @JoinColumn(name = "role"))
})
public class SUserToRole implements Serializable {

    @EmbeddedId
    private SUserToRolePK pk;

    public SUserToRole() {
        this.pk = new SUserToRolePK();
    }

    public SUserToRole(final SUser user, final SRole role) {
        this.pk = new SUserToRolePK(user, role);
    }

    public SUserToRolePK getPk() {
        return pk;
    }

    public void setPk(final SUserToRolePK pk) {
        this.pk = pk;
    }

    @Transient
    public SUser getUser() {
        return pk.getUser();
    }

    @Transient
    public void setUser(final SUser user) {
        pk.setUser(user);
    }

    @Transient
    public SRole getRole() {
        return pk.getRole();
    }

    @Transient
    public void setRole(final SRole role) {
        pk.setRole(role);
    }

    @Override
    public int hashCode() {
        return pk.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SUserToRole)) return false;

        SUserToRole that = (SUserToRole) o;

        return pk.equals(that.pk);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("pk", pk)
                .toString();
    }
}
