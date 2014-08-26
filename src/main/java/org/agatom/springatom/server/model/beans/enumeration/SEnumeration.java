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

import com.google.common.collect.Lists;
import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */

@Table(name = SEnumeration.TABLE_NAME)
@Entity(name = SEnumeration.ENTITY_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "id_s_enum", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SEnumeration
        extends PersistentObject<Long> {
    protected static final String                  TABLE_NAME       = "enumerations";
    protected static final String                  ENTITY_NAME      = "SEnumeration";
    private static final   long                    serialVersionUID = 8265311348298963881L;
    @Column(name = "enum_name", nullable = false, length = 50)
    private                String                  name             = null;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entry", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private                List<SEnumerationEntry> entries          = null;

    public String getName() {
        return name;
    }

    public SEnumeration setName(final String name) {
        this.name = name;
        return this;
    }

    public List<SEnumerationEntry> getEntries() {
        return entries;
    }

    public SEnumeration setEntries(final List<SEnumerationEntry> entries) {
        this.entries = entries;
        return this;
    }

    public SEnumeration addEntry(final SEnumerationEntry entry) {
        Assert.notNull(entry, "Entry can not null to add");
        if (this.entries == null) {
            this.entries = Lists.newArrayList();
        }
        this.entries.add(entry);
        return this;
    }

    public SEnumeration removeEntry(final SEnumerationEntry entry) {
        if (this.entries == null) {
            return this;
        }
        this.entries.remove(entry);
        return this;
    }

    @Override
    public String getIdentity() {
        return this.name;
    }
}
