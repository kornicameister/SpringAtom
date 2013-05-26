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

package org.agatom.springatom.model.meta;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SMetaData")
@Table(name = "SMetaData", uniqueConstraints = {
        @UniqueConstraint(columnNames = "type")
})
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
abstract public class SMetaData extends PersistentObject {

    @NaturalId
    @Column(nullable = false,
            length = 20,
            unique = true,
            updatable = false,
            name = "type")
    private String type;

    protected SMetaData() {
        super();
    }

    public SMetaData(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    protected void resolveIdColumnName() {
        this.idColumnName = "idSMetaData";
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .toString();
    }
}
