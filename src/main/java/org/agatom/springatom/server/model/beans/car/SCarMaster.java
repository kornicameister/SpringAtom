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

package org.agatom.springatom.server.model.beans.car;

import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SCarMaster.TABLE_NAME)
@Entity(name = SCarMaster.ENTITY_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "idSCarMaster", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SCarMaster
        extends PersistentObject<Long> {
    public static final  String TABLE_NAME       = "car_master";
    public static final  String ENTITY_NAME      = "SCarMaster";
    private static final long   serialVersionUID = -4932035593494629555L;
    @Embedded
    private SCarMasterManufacturingData manufacturingData;
    @BatchSize(
            size = 10
    )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH
            },
            orphanRemoval = true,
            mappedBy = "carMaster"
    )
    private Set<SCar> children = new HashSet<>();
    @Column(nullable = true,
            length = 1000,
            name = "thumbnailPath")
    private String thumbnailPath;

    public SCarMaster() {
        super();
    }

    public SCarMasterManufacturingData getManufacturingData() {
        return manufacturingData;
    }

    public SCarMaster setManufacturingData(final SCarMasterManufacturingData manufacturingData) {
        this.manufacturingData = manufacturingData;
        return this;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public SCarMaster setThumbnailPath(final String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
        return this;
    }

    public Set<SCar> getChildren() {
        return children;
    }

    public SCarMaster setChildren(final Collection<SCar> children) {
        this.children = Sets.newHashSet(children);
        return this;
    }
}
