package org.agatom.springatom.model.util;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentVersionedObject;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Entity(name = "SPerson")
@Table(name = "SPerson")
@AttributeOverrides(value = {
        @AttributeOverride(
                name = "pk.id",
                column = @Column(
                        name = "idSPerson",
                        updatable = false,
                        nullable = false)
        ),
        @AttributeOverride(
                name = "pk.version",
                column = @Column(
                        name = "version",
                        updatable = false,
                        nullable = false)
        )
})
@Inheritance(strategy = InheritanceType.JOINED)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
abstract public class SPerson extends PersistentVersionedObject {

    @Column(name = "fName", length = 45, nullable = false)
    private String firstName;

    @Column(name = "lName", length = 45, nullable = false)
    private String lastName;

    @Email
    @Audited
    @NaturalId
    @Column(name = "email", length = 45, nullable = false, unique = true)
    private String email;

    @Audited
    @Type(type = "boolean")
    @Column(name = "disabled")
    private Boolean disabled;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(final Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("email", email)
                .add("disabled", disabled)
                .toString();
    }
}
