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
import org.agatom.springatom.server.model.beans.appointment.QSAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.web.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.web.component.table.TableComponentBuilder;
import org.agatom.springatom.web.component.table.elements.extjs.ExtJSTable;
import org.agatom.springatom.web.component.table.elements.extjs.ExtJSTableColumn;
import org.agatom.springatom.web.component.table.elements.extjs.feature.ExtJSSummaryFeature;
import org.agatom.springatom.web.locale.beans.LocalizedClassModel;
import org.springframework.util.StringUtils;

/**
 * <p>AppointmentsTableBuilder class.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@EntityBased(entity = SAppointment.class)
@ComponentBuilder(AppointmentsTableBuilder.BUILDER_ID)
public class AppointmentsTableBuilder
		extends TableComponentBuilder<ExtJSTable, SAppointment> {
	/** Constant <code>BUILDER_ID="appointmentsTableBuilder"</code> */
	protected static final String BUILDER_ID = "appointmentsTableBuilder";
	private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SAppointment.ENTITY_NAME));

	/** {@inheritDoc} */
	@Override
	protected ExtJSTable buildDefinition(final ComponentDataRequest dataRequest) {
		final QSAppointment appointment = QSAppointment.sAppointment;
		final ExtJSTable table = new ExtJSTable(TABLE_ID, BUILDER_ID);
		final LocalizedClassModel<SAppointment> lModel = this.getLocalizedClassModel();

		table.setBorder(false)
				.addFeature(new ExtJSSummaryFeature().setRemoteRoot(this.getAttributeName(appointment.car)))
				.setSortableColumns(true)
				.setForceFit(true);

		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(appointment.id))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(appointment.id)))
		);
		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(appointment.begin))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(appointment.begin)))
		);
		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName("interval"))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName("interval")))
		);
		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(appointment.end))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(appointment.end)))
		);
		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(appointment.allDay))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(appointment.allDay)))
		);
		table.addContent(
				(ExtJSTableColumn) new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(appointment.car))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(appointment.car)))
						.setDisplayAs(AttributeDisplayAs.INFOPAGE_ATTRIBUTE)
		);
		table.addContent(
				(ExtJSTableColumn) new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(appointment.assignee))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(appointment.assignee)))
						.setDisplayAs(AttributeDisplayAs.INFOPAGE_ATTRIBUTE)
		);
		table.addContent(
				(ExtJSTableColumn) new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(appointment.reporter))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(appointment.reporter)))
						.setDisplayAs(AttributeDisplayAs.INFOPAGE_ATTRIBUTE)
		);
		table.addContent(
				new ExtJSTableColumn()
						.setDataIndex(this.getAttributeName(appointment.assigned))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(appointment.assigned)))
		);

		return table;
	}

	/** {@inheritDoc} */
	@Override
	protected Predicate getPredicate(final Long appointmentId, final Class<?> contextClass) {
		return null;
	}
}
