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
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.web.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.web.component.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.builders.table.TableComponentBuilder;
import org.agatom.springatom.web.component.data.ComponentDataRequest;
import org.agatom.springatom.web.component.elements.table.DandelionTableComponent;
import org.springframework.util.StringUtils;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@EntityBased(entity = SAppointment.class)
@ComponentBuilds(
		id = AppointmentsTableBuilder.BUILDER_ID,
		builds = SAppointment.class,
		produces = ComponentBuilds.Produces.TABLE_COMPONENT
)
public class AppointmentsTableBuilder
		extends TableComponentBuilder<DandelionTableComponent, SAppointment> {
	protected static final String BUILDER_ID = "appointmentsTableBuilder";
	private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SAppointment.ENTITY_NAME));

	@Override
	protected DandelionTableComponent buildDefinition(final ComponentDataRequest dataRequest) {
		final DandelionTableComponent component = this.helper.newDandelionTable(TABLE_ID, BUILDER_ID);
		this.helper.newTableColumn(component, "id", "persistentobject.id");
		this.helper.newTableColumn(component, "begin", "sappointment.begin").setRenderFunctionName("renderDate");
		this.helper.newTableColumn(component, "end", "sappointment.end").setRenderFunctionName("renderDate");
		this.helper.newTableColumn(component, "allDay", "sappointment.allDay").setRenderFunctionName("renderBoolean");
		this.helper.newTableColumn(component, "car", "sappointment.car");
		this.helper.newTableColumn(component, "assignee", "sappointment.assignee");
		this.helper.newTableColumn(component, "assigned", "sappointment.assigned").setRenderFunctionName("renderDate");
		this.helper.newTableColumn(component, "reporter", "sappointment.reporter");
		return component;
	}

	@Override
	protected Object handleColumnConversion(final SAppointment object, final Object value, final String path) {
		switch (path) {
			case "car":
				return object.getCar().getLicencePlate();
			case "assignee":
				return object.getAssignee().getPerson().getIdentity();
			case "reporter":
				return object.getReporter().getPerson().getIdentity();
		}
		return super.handleColumnConversion(object, value, path);
	}

	@Override
	protected Predicate getPredicate(final Long appointmentId, final Class<?> contextClass) {
		return null;
	}
}
