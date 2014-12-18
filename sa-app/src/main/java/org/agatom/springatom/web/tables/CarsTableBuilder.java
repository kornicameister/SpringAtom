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

package org.agatom.springatom.web.tables;

import com.mysema.query.types.Predicate;
import org.agatom.springatom.cmp.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.cmp.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.infopages.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.cmp.component.table.TableComponentBuilder;
import org.agatom.springatom.cmp.component.table.elements.ng.NgTable;
import org.agatom.springatom.data.hades.model.car.NCar;
import org.agatom.springatom.data.hades.model.car.QNCar;
import org.agatom.springatom.data.locale.model.AttributedLocalizedClassModel;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

@EntityBased(entity = NCar.class)
@ComponentBuilder(CarsTableBuilder.BUILDER_ID)
public class CarsTableBuilder
        extends TableComponentBuilder<NgTable, NCar> {
    /** Constant <code>BUILDER_ID="carsTableBuilder"</code> */
    protected static final String BUILDER_ID = "carsTableBuilder";
    private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(ClassUtils.getShortName(NCar.class)));

    /** {@inheritDoc} */
    @Override
    protected Predicate getPredicate(final Long id, final Class<?> contextClass) {
        return QNCar.nCar.carMaster.id.eq(id);
    }

    /** {@inheritDoc} */
    @Override
    protected NgTable buildDefinition(final ComponentDataRequest dataRequest) {

        final QNCar car = QNCar.nCar;
        final AttributedLocalizedClassModel lModel = this.getLocalizedClassModel();
        final NgTable table = new NgTable(TABLE_ID, BUILDER_ID)
                .setBorder(false)
                .setSortable(true)
                .setCollapsible(false);

        table.addContent(this.newColumn(car.id, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
        table.addContent(this.newColumn(car.owner, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
        table.addContent(this.newColumn(car.licencePlate, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
        table.addContent(this.newColumn(car.vinNumber, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));

        return table;
    }
}
