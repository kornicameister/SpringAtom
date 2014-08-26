/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.server.model.beans.enumeration;

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.ReportableEntity;

import javax.persistence.*;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SEnumerationEntry.TABLE_NAME)
@Entity(name = SEnumerationEntry.ENTITY_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "id_enum_entry", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SEnumerationEntry
        extends PersistentObject<Long> {
    protected static final String TABLE_NAME       = "enumerations_entries";
    protected static final String ENTITY_NAME      = "SEnumerationEntries";
    private static final   long   serialVersionUID = -8222922908373100651L;

    @Column(name = "entry_key", nullable = false, length = 50)
    private String       key     = null;
    @Column(name = "entry_comment", nullable = false, length = 200)
    private String       comment = null;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "see_enum", referencedColumnName = "id_s_enum", updatable = false)
    private SEnumeration entry   = null;

    public String getKey() {
        return key;
    }

    public SEnumerationEntry setKey(final String key) {
        this.key = key;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public SEnumerationEntry setComment(final String comment) {
        this.comment = comment;
        return this;
    }

    public SEnumeration getEntry() {
        return entry;
    }

    public SEnumerationEntry setEntry(final SEnumeration entry) {
        this.entry = entry;
        return this;
    }
}
