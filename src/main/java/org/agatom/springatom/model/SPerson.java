package org.agatom.springatom.model;

import com.google.common.base.Objects;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@Entity(name = "SPerson")
@Table(name = "SPerson")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSPerson",
                updatable = false,
                nullable = false)
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "who",
        discriminatorType = DiscriminatorType.STRING
)
abstract public class SPerson extends PersistentObject {

    @Column(name = "fName", length = 45, nullable = false)
    private String firstName;

    @Column(name = "lName", length = 45, nullable = false)
    private String lastName;

    @Email
    @NaturalId
    @Column(name = "email", length = 45, nullable = false, unique = true)
    private String email;

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
