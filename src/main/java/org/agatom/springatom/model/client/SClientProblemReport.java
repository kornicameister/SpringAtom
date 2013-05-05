package org.agatom.springatom.model.client;

import org.agatom.springatom.model.PersistentObject;
import org.agatom.springatom.model.SIssueReporter;
import org.agatom.springatom.model.appointment.SAppointment;
import org.agatom.springatom.model.meta.SClientProblemReportType;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SClientProblemReport")
@Table(name = "SClientProblemReport")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSClientProblemReport",
                updatable = false,
                nullable = false)
)
public class SClientProblemReport extends PersistentObject {
    @ManyToOne(optional = false)
    @JoinColumn(name = "client", referencedColumnName = "idSPerson", updatable = false)
    private SClient client;

    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData")
    private SClientProblemReportType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "appointment", referencedColumnName = "idSAppointment", updatable = false)
    private SAppointment appointment;

    @Column(name = "problem", nullable = false, length = 444)
    private String problem;

    @Embedded
    private SIssueReporter reporter;
}
