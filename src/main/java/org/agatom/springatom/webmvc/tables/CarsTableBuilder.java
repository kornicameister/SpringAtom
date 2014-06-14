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

package org.agatom.springatom.webmvc.tables;

import com.mysema.query.types.Predicate;
import org.agatom.springatom.server.model.beans.car.QSCar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.web.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.table.TableComponentBuilder;
import org.agatom.springatom.web.component.table.elements.extjs.ExtJSTable;
import org.agatom.springatom.web.component.table.elements.extjs.ExtJSTableColumn;
import org.agatom.springatom.web.locale.beans.LocalizedClassModel;
import org.springframework.util.StringUtils;

/**
 * <p>CarsTableBuilder class.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@EntityBased(entity = SCar.class)
@ComponentBuilder(CarsTableBuilder.BUILDER_ID)
public class CarsTableBuilder
		extends TableComponentBuilder<ExtJSTable, SCar> {
	/** Constant <code>BUILDER_ID="carsTableBuilder"</code> */
	protected static final String BUILDER_ID = "carsTableBuilder";
	private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SCar.ENTITY_NAME));

	/** {@inheritDoc} */
	@Override
	protected Predicate getPredicate(final Long id, final Class<?> contextClass) {
		return QSCar.sCar.carMaster.id.eq(id);
	}

	/** {@inheritDoc} */
	@Override
	protected ExtJSTable buildDefinition(final ComponentDataRequest dataRequest) {

		final QSCar car = QSCar.sCar;
		final ExtJSTable table = new ExtJSTable(TABLE_ID, BUILDER_ID);
		final LocalizedClassModel<SCar> lModel = this.getLocalizedClassModel();

		table.setBorder(false)
				.setSortableColumns(true)
				.setCollapsible(false)
				.setForceFit(true);

		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(car.id))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(car.id)))
		);
		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(car.owner))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(car.owner)))
		);
		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(car.licencePlate))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(car.licencePlate)))
		);
		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(car.vinNumber))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(car.vinNumber)))
		);

		return null;
	}
}
