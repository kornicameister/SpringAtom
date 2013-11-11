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

package org.agatom.springatom.server.model.beans.person;

import org.agatom.springatom.server.model.beans.contact.SAbstractContact;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Audited
@AuditOverride(name = "contact",
        forClass = SAbstractContact.class,
        auditJoinTable = @AuditJoinTable(
                name = "spersoncontact_history"
        )
)
@DiscriminatorValue("person")
public class SPersonContact
        extends SAbstractContact<SPerson> {
    private static final long    serialVersionUID = 86397657105677805L;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned", updatable = true)
    protected            SPerson assigned         = null;

    @Override
    public SPerson getAssigned() {
        return this.assigned;
    }

    @Override
    public void setAssigned(final SPerson assigned) {
        this.assigned = assigned;
    }
}
