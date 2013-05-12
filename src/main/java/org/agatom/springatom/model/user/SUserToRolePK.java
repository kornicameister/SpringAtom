package org.agatom.springatom.model.user;

import com.google.common.base.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Embeddable
public class SUserToRolePK implements Serializable {
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "user", referencedColumnName = "idSUser"),
            @JoinColumn(name = "userVersion", referencedColumnName = "version")
    })
    private SUser user;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "idSRole")
    private SRole role;

    public SUserToRolePK() {
        super();
    }

    public SUserToRolePK(final SUser user, final SRole role) {
        this.user = user;
        this.role = role;
    }

    public SUser getUser() {
        return user;
    }

    public void setUser(final SUser user) {
        this.user = user;
    }

    public SRole getRole() {
        return role;
    }

    public void setRole(final SRole role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SUserToRolePK)) return false;

        SUserToRolePK that = (SUserToRolePK) o;

        return role.equals(that.role) &&
                user.equals(that.user);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("user", user)
                .add("role", role)
                .toString();
    }
}
