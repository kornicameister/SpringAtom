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

package org.agatom.springatom.server.model.beans.report.entity;

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.types.report.entity.ReportEntity;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * {@code SReportEntity} is a {@link org.springframework.data.domain.Persistable} which
 * provides full information required by report an {@link javax.persistence.Entity} that can be
 * used in a {@link org.agatom.springatom.server.model.beans.report.SReport}.
 * <p/>
 * <p>
 * {@code SReportEntity} is <code style="color:red">immutable</code> as all fields defined in it are unique.
 * This particular {@link javax.persistence.Entity}, hence corresponding table, is meant to be populated once
 * during application first run using {@link org.agatom.springatom.server.populators.ReportableEntitiesPopulator}.
 * </p>
 * <p>
 * Actual link between an instance of {@link org.agatom.springatom.server.model.beans.report.SReport} and
 * entities used in it is represented by {@link org.agatom.springatom.server.model.beans.report.links.entity.SReportEntityLink}
 * </p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Table(name = SReportEntity.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(
                name = "reportEntity_clazz_unique",
                columnNames = "reportEntity_clazz"
        )
)
@Entity(name = SReportEntity.ENTITY_NAME)
@AttributeOverride(name = "id", column = @Column(name = "idSReportEntity", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
public class SReportEntity
        extends PersistentObject<Long>
        implements ReportEntity {
    public static final  String TABLE_NAME       = "reports_e";
    public static final  String ENTITY_NAME      = "SReportEntity";
    private static final long   serialVersionUID = 7086049728517905122L;
    @NotNull
    @NaturalId
    @Length(min = 10, max = 300)
    @Column(name = "reportEntity_clazz", nullable = false, unique = true, updatable = false, length = 300)
    protected Class<?> clazz;

    public Class<?> getEntityClass() {
        return clazz;
    }

    public SReportEntity setEntityClass(final Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

}
