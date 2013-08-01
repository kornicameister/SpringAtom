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

package org.agatom.springatom.model.beans.meta.holder;

import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.meta.SMetaData;
import org.agatom.springatom.model.types.meta.SMetaDataHolder;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class SBasicMetaDataHolder<MD extends SMetaData, PK extends Serializable>
        extends PersistentObject<PK>
        implements SMetaDataHolder<MD> {

    @NotNull(message = "SMetaDataHolder requires knowledge of the meta data, it can not be null")
    @Index(name = "sbmdh_type")
    @OrderColumn(nullable = false)
    @ManyToOne(optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH,
            targetEntity = SMetaData.class
    )
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData", updatable = true)
    private MD type;

    @Override
    public MD getMetaInformation() {
        return this.type;
    }

    @Override
    public <MDS extends MD> SMetaDataHolder setMetaInformation(final MDS metaData) {
        this.type = metaData;
        return this;
    }
}
