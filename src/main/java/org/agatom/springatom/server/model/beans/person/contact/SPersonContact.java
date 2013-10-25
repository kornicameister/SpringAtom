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

package org.agatom.springatom.server.model.beans.person.contact;

import org.agatom.springatom.server.model.beans.contact.SBasicContact;
import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.types.contact.SContact;
import org.agatom.springatom.server.model.types.meta.SMetaDataEnum;
import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Audited
@AuditOverride(name = "contact",
        forClass = SBasicContact.class,
        auditJoinTable = @AuditJoinTable(
                name = "spersoncontact_history"
        )
)
abstract public class SPersonContact
        extends SBasicContact<SPerson> {
    private static final long    serialVersionUID = 86397657105677805L;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "assigned",
            updatable = true
    )
    @Type(type = "java.io.Serializable")
    protected            SPerson assigned         = null;

    public static SContact fromType(SMetaDataEnum type) {
        switch (type) {
            case SCT_CELL_PHONE:
                return new SPersonCellPhoneContact();
            case SCT_FAX:
                return new SPersonFaxContact();
            case SCT_MAIL:
                return new SPersonEmailContact();
            case SCT_PHONE:
                return new SPersonPhoneContact();
        }
        return null;
    }

    @Override
    public SPerson getAssigned() {
        return this.assigned;
    }

    @Override
    public SContact setAssigned(final
                                @NotNull
                                SPerson assigned) {
        this.assigned = assigned;
        return this;
    }
}
