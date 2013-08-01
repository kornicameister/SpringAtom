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
import org.agatom.springatom.model.types.meta.SMetaDataEnum;
import org.agatom.springatom.model.types.meta.SMetaDataType;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SMetaData")
@Table(name = "SMetaData")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSMetaData",
                updatable = false,
                nullable = false))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "meta",
        discriminatorType = DiscriminatorType.STRING
)
@Immutable
abstract public class SMetaData
        extends PersistentObject<Long>
        implements SMetaDataType<Long> {

    @Type(type = "org.hibernate.type.EnumType")
    @Column(nullable = false,
            length = 30,
            updatable = false,
            name = "type")
    @NaturalId(mutable = false)
    @Enumerated(value = EnumType.STRING)
    private SMetaDataEnum type;

    protected SMetaData() {
        super();
    }

    @Override
    public SMetaDataEnum getType() {
        return this.type;
    }
}
