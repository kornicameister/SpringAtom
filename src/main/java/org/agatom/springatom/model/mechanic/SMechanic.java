package org.agatom.springatom.model.mechanic;

import org.agatom.springatom.model.SPerson;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SMechanic")
@Table(name = "SMechanic")
@PrimaryKeyJoinColumn(name = "idSMechanic")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class SMechanic extends SPerson {
}
