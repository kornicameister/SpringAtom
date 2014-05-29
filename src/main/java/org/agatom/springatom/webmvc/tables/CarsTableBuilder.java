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
import org.agatom.springatom.web.component.core.elements.table.DandelionTableComponent;
import org.agatom.springatom.web.component.table.TableComponentBuilder;
import org.springframework.util.StringUtils;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@EntityBased(entity = SCar.class)
@ComponentBuilder(CarsTableBuilder.BUILDER_ID)
public class CarsTableBuilder
		extends TableComponentBuilder<DandelionTableComponent, SCar> {
	protected static final String BUILDER_ID = "carsTableBuilder";
	private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SCar.ENTITY_NAME));

	@Override
	protected Object handleColumnConversion(final SCar object, final Object value, final String path) {
		switch (path) {
			case "owner": {
				return object.getOwner().getPerson().getIdentity();
			}
		}
		return super.handleColumnConversion(object, value, path);
	}

	@Override
	protected Predicate getPredicate(final Long id, final Class<?> contextClass) {
		return QSCar.sCar.carMaster.id.eq(id);
	}

	@Override
	protected DandelionTableComponent buildDefinition(final ComponentDataRequest dataRequest) {
		final DandelionTableComponent component = this.helper.newDandelionTable(TABLE_ID, BUILDER_ID);
		this.helper.newTableColumn(component, "id", "persistentobject.id");
		this.helper.newTableColumn(component, "owner", "scar.owner");
		this.helper.newTableColumn(component, "licencePlate", "scar.licenceplate");
		this.helper.newTableColumn(component, "vinNumber", "scar.vinnumber");
		return component;
	}
}
