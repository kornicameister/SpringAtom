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

package org.agatom.springatom.webmvc.pages.infopage;

import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.web.infopages.annotation.DomainInfoPage;
import org.agatom.springatom.web.infopages.page.BasicEntityInfoPage;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@DomainInfoPage(
        path = CarMasterInfoPage.PATH,
        rel = CarMasterInfoPage.REL,
        domain = SCarMaster.class
)
public class CarMasterInfoPage
        extends BasicEntityInfoPage {
    protected static final transient String PATH             = "car-master";
    protected static final transient String REL              = "ip.car.master";
    private static final             long   serialVersionUID = -2891681661353050412L;
}
