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

package org.agatom.springatom.model.beans.car;

import com.google.common.base.Objects;
import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.car.embeddable.SCarMasterManufacturingData;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SCarMaster")
@Table(name = "SCarMaster")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSCarMaster",
                updatable = false,
                insertable = true,
                nullable = false)
)
public class SCarMaster extends PersistentObject<Long> {
    @Embedded
    private SCarMasterManufacturingData manufacturingData;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carMaster")
    @BatchSize(size = 10)
    private Set<SCar>                   children;
    @Column(nullable = false,
            length = 1000,
            name = "thumbnailPath")
    private String                      thumbnailPath;

    public SCarMaster() {
        super();
    }

    public SCarMasterManufacturingData getManufacturingData() {
        return manufacturingData;
    }

    public void setManufacturingData(final SCarMasterManufacturingData manufacturingData) {
        this.manufacturingData = manufacturingData;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(final String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Set<SCar> getChildren() {
        return children;
    }

    public void setChildren(final Set<SCar> children) {
        this.children = children;
    }

    public boolean addChild(final SCar sCar) {
        return this.children.add(sCar);
    }

    public boolean removeChild(final Object car) {
        return this.children.remove(car);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("manufacturingData", manufacturingData)
                .add("thumbnailPath", thumbnailPath)
                .toString();
    }
}
