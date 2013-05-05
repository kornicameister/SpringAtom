package org.agatom.springatom.model.user;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentVersionedObject;
import org.agatom.springatom.model.SPerson;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Entity(name = "SUser")
@Table(name = "SUser")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSUser",
                updatable = false,
                nullable = false)
)
public class SUser extends PersistentVersionedObject {
    @NaturalId
    @Column(name = "login", length = 45, unique = true, nullable = false)
    private String login;

    @Column(name = "secPass", length = 66, nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "person", referencedColumnName = "idSPerson", updatable = false, nullable = false)
    private SPerson person;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SUserToRole",
            joinColumns = @JoinColumn(name = "user", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role", nullable = false))
    private Set<SRole> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public SPerson getPerson() {
        return person;
    }

    public void setPerson(final SPerson person) {
        this.person = person;
    }

    public Set<SRole> getRoles() {
        return roles;
    }

    public void setRoles(final Set<SRole> roles) {
        this.roles = roles;
    }

    public boolean addRole(final SRole sRole) {
        return roles.add(sRole);
    }

    public boolean removeRole(final SRole role) {
        return roles.remove(role);
    }

    public boolean containsRoles(final Collection<SRole> roles) {
        return this.roles.containsAll(roles);
    }

    public boolean containsRole(final SRole role) {
        return roles.contains(role);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SUser)) return false;
        if (!super.equals(o)) return false;

        SUser sUser = (SUser) o;

        return login.equals(sUser.login);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("login", login)
                .add("password", password)
                .toString();
    }
}
