package org.agatom.springatom.model.mechanic;

import org.agatom.springatom.model.util.SPerson;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SMechanic")
@Table(name = "SMechanic")
@PrimaryKeyJoinColumns(value = {
        @PrimaryKeyJoinColumn(name = "idSMechanic"),
        @PrimaryKeyJoinColumn(name = "version")
})
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class SMechanic extends SPerson {
    @Override
    public String getIdColumnName() {
        return "idSMechanic";
    }
}
