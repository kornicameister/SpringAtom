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

package org.agatom.springatom.webmvc.pages.builders;

import com.mysema.query.types.Predicate;
import org.agatom.springatom.server.model.beans.appointment.QSAppointmentTask;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.web.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.web.component.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.builders.table.TableComponentBuilder;
import org.agatom.springatom.web.component.elements.table.DandelionTableComponent;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@EntityBased(entity = SAppointmentTask.class)
@ComponentBuilds(
        id = AppointmentTaskTableBuilder.BUILDER_ID,
        builds = SAppointmentTask.class,
        produces = ComponentBuilds.Produces.TABLE_COMPONENT
)
public class AppointmentTaskTableBuilder
        extends TableComponentBuilder<DandelionTableComponent, SAppointmentTask> {

    protected static final String BUILDER_ID = "appointmentTaskTableBuilder";
    private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SAppointmentTask.ENTITY_NAME));
    private static final   Logger LOGGER     = Logger.getLogger(AppointmentTaskTableBuilder.class);

    @Override
    protected DandelionTableComponent buildDefinition() {
        final DandelionTableComponent component = this.helper.newDandelionTable(TABLE_ID, BUILDER_ID);
        this.helper.newTableColumn(component, "id", "persistentobject.id");
        this.helper.newTableColumn(component, "type", "sappointment.task.type");
        this.helper.newTableColumn(component, "task", "sappointment.task.task").setSortable(false);
        return component;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Predicate getPredicate(final Long appointmentId, final Class<?> contextClass) {
        return QSAppointmentTask.sAppointmentTask.appointment.id.eq(appointmentId);
    }
}
