package org.agatom.springatom.model.user;

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
    @Column(name = "role", updatable = false, unique = true)
    private String role;
}
