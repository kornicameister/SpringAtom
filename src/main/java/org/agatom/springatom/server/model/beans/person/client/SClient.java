/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.server.model.beans.person.client;

import org.agatom.springatom.server.model.beans.person.SPerson;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = "SClient")
@Entity(name = "SClient")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSClient",
                updatable = false,
                nullable = false
        )
)
@PrimaryKeyJoinColumn(name = "idSClient")
public class SClient
        extends SPerson {

    @BatchSize(size = 10)
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "client",
            cascade = {
                    CascadeType.REMOVE
            }
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<SClientProblemReport> problemReportSet = new HashSet<>();

    public SClient() {
        super();
    }

    public Set<SClientProblemReport> getProblemReportSet() {
        return problemReportSet;
    }

    public void setProblemReportSet(final Set<SClientProblemReport> problemReportSet) {
        this.problemReportSet = problemReportSet;
    }
}
