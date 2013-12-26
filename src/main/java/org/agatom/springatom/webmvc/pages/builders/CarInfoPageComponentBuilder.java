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

import org.agatom.springatom.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.component.builders.annotation.EntityBased;
import org.agatom.springatom.component.data.ComponentDataRequest;
import org.agatom.springatom.component.data.ComponentDataResponse;
import org.agatom.springatom.ip.component.builder.InfoPageComponentBuilder;
import org.agatom.springatom.ip.component.elements.InfoPageComponent;
import org.agatom.springatom.server.model.beans.car.SCar;
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
        extends InfoPageComponentBuilder {
    @Override
    protected Logger getLogger() {
        return Logger.getLogger(CarInfoPageComponentBuilder.class);
    }

    @Override
    protected InfoPageComponent buildDefinition() {
        return null;
    }

    @Override
    protected ComponentDataResponse buildData(final ComponentDataRequest dataRequest) {
        return null;
    }
}
