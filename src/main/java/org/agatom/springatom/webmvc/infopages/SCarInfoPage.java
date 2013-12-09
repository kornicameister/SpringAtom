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

package org.agatom.springatom.webmvc.infopages;

import org.agatom.springatom.ip.DomainInfoPageResource;
import org.agatom.springatom.ip.InfoPageResource;
import org.agatom.springatom.ip.page.BasicDomainInfoPage;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.repository.repositories.car.SCarRepository;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@InfoPageResource(path = SCarInfoPage.PATH, rel = SCarInfoPage.REL)
@DomainInfoPageResource(domainClass = SCar.class, repositoryClass = SCarRepository.class)
public class SCarInfoPage
        extends BasicDomainInfoPage {
    protected static final transient String PATH = "car";
    protected static final transient String REL  = "ip.car";
}
