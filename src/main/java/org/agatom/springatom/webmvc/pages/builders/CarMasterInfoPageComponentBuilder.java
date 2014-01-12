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

import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.web.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.web.component.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.meta.LayoutType;
import org.agatom.springatom.web.infopages.component.builder.EntityInfoPageComponentBuilder;
import org.agatom.springatom.web.infopages.component.elements.InfoPageComponent;
import org.agatom.springatom.web.infopages.component.elements.InfoPagePanelComponent;
import org.agatom.springatom.web.infopages.component.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.webmvc.pages.infopage.CarMasterInfoPage;
import org.apache.log4j.Logger;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@EntityBased(entity = SCarMaster.class)
@ComponentBuilds(id = "carMasterInfopage", builds = CarMasterInfoPage.class)
public class CarMasterInfoPageComponentBuilder
        extends EntityInfoPageComponentBuilder<SCar> {
    private static final long serialVersionUID = 2958273340104815948L;

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(CarMasterInfoPageComponentBuilder.class);
    }

    @Override
    protected InfoPageComponent buildDefinition() {
        final InfoPageComponent cmp = new InfoPageComponent();
        this.populateBasicPanel(helper.newBasicPanel(cmp, LayoutType.VERTICAL));
        this.populateTablePanel(helper.newOneToManyPanel(cmp, LayoutType.VERTICAL));
        return cmp;
    }

    private void populateTablePanel(final InfoPagePanelComponent panel) {
        this.helper.newTableAttribute(panel, "children", this.getEntityName());
    }

    private void populateBasicPanel(final InfoPagePanelComponent panel) {
        this.helper.newAttribute(panel, "id", "persistentobject.id", AttributeDisplayAs.VALUE);
        this.helper.newValueAttribute(panel, "manufacturingData.brand", this.getEntityName());
        this.helper.newValueAttribute(panel, "manufacturingData.model", this.getEntityName());
    }
}
