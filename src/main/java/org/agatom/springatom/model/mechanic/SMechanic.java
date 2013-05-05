package org.agatom.springatom.model.mechanic;

import org.agatom.springatom.model.SPerson;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SMechanic")
@DiscriminatorValue(value = "SM")
public class SMechanic extends SPerson {
    public SMechanic() {
        super();
    }
}
