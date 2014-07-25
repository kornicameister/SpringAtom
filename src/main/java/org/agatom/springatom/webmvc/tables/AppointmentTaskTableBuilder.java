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
import org.agatom.springatom.server.model.beans.appointment.QSAppointmentTask;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.web.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.web.component.table.TableComponentBuilder;
import org.agatom.springatom.web.component.table.elements.extjs.NgTable;
import org.agatom.springatom.web.component.table.elements.extjs.NgTableColumn;
import org.agatom.springatom.web.component.table.elements.extjs.feature.NgSummaryFeature;
import org.agatom.springatom.web.locale.beans.LocalizedClassModel;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * <p>AppointmentTaskTableBuilder class.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@EntityBased(entity = SAppointmentTask.class)
@ComponentBuilder(AppointmentTaskTableBuilder.BUILDER_ID)
public class AppointmentTaskTableBuilder
		extends TableComponentBuilder<NgTable, SAppointmentTask> {
	/** Constant <code>BUILDER_ID="appointmentTaskTableBuilder"</code> */
	protected static final String BUILDER_ID = "appointmentTaskTableBuilder";
	private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SAppointmentTask.ENTITY_NAME));

	/** {@inheritDoc} */
	@Override
	protected NgTable buildDefinition(final ComponentDataRequest dataRequest) {
		final QSAppointmentTask task = QSAppointmentTask.sAppointmentTask;
		final NgTable table = new NgTable(TABLE_ID, BUILDER_ID);
		final LocalizedClassModel<SAppointmentTask> lModel = this.getLocalizedClassModel();

		table.setBorder(false)
				.addFeature(new NgSummaryFeature().setRemoteRoot(this.getAttributeName(task.type)))

				.setSortableColumns(true)
				.setCollapsible(false)
				.setForceFit(true);

		table.addContent(
				(NgTableColumn) new NgTableColumn()
						.setTooltip(lModel.getLocalizedAttribute(this.getAttributeName(task.id)))
						.setDataIndex(this.getAttributeName(task.id))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(task.id)))
						.setDisplayAs(AttributeDisplayAs.VALUE_ATTRIBUTE)
		);
		table.addContent(
				(NgTableColumn) new NgTableColumn()
						.setTooltip(lModel.getLocalizedAttribute(this.getAttributeName(task.type)))
						.setDataIndex(this.getAttributeName(task.type))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(task.type)))
						.setDisplayAs(AttributeDisplayAs.VALUE_ATTRIBUTE)
		);
		table.addContent(
				(NgTableColumn) new NgTableColumn()
						.setTooltip(lModel.getLocalizedAttribute(this.getAttributeName(task.task)))
						.setDataIndex(this.getAttributeName(task.task))
						.setText(lModel.getLocalizedAttribute(this.getAttributeName(task.task)))
						.setDisplayAs(AttributeDisplayAs.VALUE_ATTRIBUTE)
		);

		return table;
	}

	/** {@inheritDoc} */
	@Override
	protected Predicate getPredicate(final Long appointmentId, final Class<?> contextClass) {
		if (ClassUtils.isAssignable(SAppointment.class, contextClass)) {
			return QSAppointmentTask.sAppointmentTask.appointment.id.eq(appointmentId);
		}
		return null;
	}
}
