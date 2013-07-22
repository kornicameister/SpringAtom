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

package org.agatom.springatom.model.beans.meta;

import org.agatom.springatom.model.beans.PersistentObject;

import javax.persistence.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class SMetaDataHolder
        extends PersistentObject<Long>
        implements SMetaDataCapable {

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData", updatable = true)
    private SMetaData type;

    @Override
    public SMetaData getMetaInformation() {
        return this.type;
    }

    @Override
    public void setMetaInformation(final SMetaData metaData) {
        this.type = metaData;
    }
}
