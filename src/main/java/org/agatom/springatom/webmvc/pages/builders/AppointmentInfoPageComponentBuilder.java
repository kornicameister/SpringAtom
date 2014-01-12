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

import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.web.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.web.component.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.meta.LayoutType;
import org.agatom.springatom.web.infopages.component.builder.EntityInfoPageComponentBuilder;
import org.agatom.springatom.web.infopages.component.elements.InfoPageComponent;
import org.agatom.springatom.web.infopages.component.elements.InfoPagePanelComponent;
import org.agatom.springatom.web.infopages.component.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.webmvc.pages.infopage.AppointmentInfoPage;
import org.apache.log4j.Logger;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@EntityBased(entity = SAppointment.class)
@ComponentBuilds(id = "appointmentInfopage", builds = AppointmentInfoPage.class)
public class AppointmentInfoPageComponentBuilder
        extends EntityInfoPageComponentBuilder<SAppointment> {

    private static final long serialVersionUID = 7743548253782408262L;

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(AppointmentInfoPageComponentBuilder.class);
    }

    @Override
    protected InfoPageComponent buildDefinition() {
        final InfoPageComponent cmp = new InfoPageComponent();
        this.populateBasicPanel(helper.newBasicPanel(cmp, LayoutType.VERTICAL));
        this.populateTablePanel(helper.newOneToManyPanel(cmp, LayoutType.VERTICAL));
        this.populateInfoPagePanel(helper.newManyToOnePanel(cmp, LayoutType.VERTICAL));
        return cmp;
    }

    private void populateInfoPagePanel(final InfoPagePanelComponent panel) {
        this.helper.newLinkAttribute(panel, "car", this.getEntityName());
        this.helper.newLinkAttribute(panel, "assignee", this.getEntityName());
        this.helper.newLinkAttribute(panel, "reporter", this.getEntityName());
    }

    private void populateBasicPanel(final InfoPagePanelComponent panel) {
        this.helper.newAttribute(panel, "id", "persistentobject.id", AttributeDisplayAs.VALUE);
        this.helper.newValueAttribute(panel, "begin", this.getEntityName());
        this.helper.newValueAttribute(panel, "end", this.getEntityName());
        this.helper.newValueAttribute(panel, "interval", this.getEntityName());
        this.helper.newValueAttribute(panel, "assigned", this.getEntityName());
    }

    private void populateTablePanel(final InfoPagePanelComponent panel) {
        this.helper.newTableAttribute(panel, "tasks", this.getEntityName());
    }
}
