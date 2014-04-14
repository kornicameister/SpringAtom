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

import org.agatom.springatom.server.model.beans.car.QSCar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.web.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.web.component.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.meta.LayoutType;
import org.agatom.springatom.web.infopages.component.builder.EntityInfoPageComponentBuilder;
import org.agatom.springatom.web.infopages.component.elements.InfoPageComponent;
import org.agatom.springatom.web.infopages.component.elements.InfoPagePanelComponent;
import org.agatom.springatom.web.infopages.component.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.webmvc.pages.infopage.CarInfoPage;
import org.apache.log4j.Logger;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@EntityBased(entity = SCar.class)
@ComponentBuilds(id = "carInfopage", builds = CarInfoPage.class)
public class CarInfoPageComponentBuilder
        extends EntityInfoPageComponentBuilder<SCar> {
    private static final long serialVersionUID = 4901099476349020351L;

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(CarInfoPageComponentBuilder.class);
    }

    @Override
    protected InfoPageComponent buildDefinition() {
        final InfoPageComponent cmp = new InfoPageComponent();
        this.populateBasicPanel(helper.newBasicPanel(cmp, LayoutType.VERTICAL));
        this.populateInfoPagePanel(helper.newManyToOnePanel(cmp, LayoutType.VERTICAL));
        return cmp;
    }

	private void populateBasicPanel(final InfoPagePanelComponent panel) {
		this.helper.newAttribute(panel, "id", "persistentobject.id", AttributeDisplayAs.VALUE);
		this.helper.newValueAttribute(panel, QSCar.sCar.licencePlate.getMetadata().getName(), this.getEntityName());
		this.helper.newValueAttribute(panel, QSCar.sCar.vinNumber.getMetadata().getName(), this.getEntityName());
		this.helper.newValueAttribute(panel, QSCar.sCar.fuelType.getMetadata().getName(), this.getEntityName());
		this.helper.newValueAttribute(panel, QSCar.sCar.yearOfProduction.getMetadata().getName(), this.getEntityName());
	}

	private void populateInfoPagePanel(final InfoPagePanelComponent panel) {
		this.helper.newLinkAttribute(panel, "carMaster", this.getEntityName());
		this.helper.newLinkAttribute(panel, "owner", this.getEntityName());
	}
}
