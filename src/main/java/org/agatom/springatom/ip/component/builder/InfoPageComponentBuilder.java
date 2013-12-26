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

package org.agatom.springatom.ip.component.builder;

import org.agatom.springatom.component.builders.DefaultComponentBuilder;
import org.agatom.springatom.component.builders.exception.ComponentException;
import org.agatom.springatom.ip.component.elements.InfoPageComponent;
import org.agatom.springatom.ip.component.elements.InfoPagePanelComponent;
import org.agatom.springatom.ip.component.elements.attributes.InfoPageAttributeComponent;
import org.agatom.springatom.ip.component.helper.InfoPageComponentHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class InfoPageComponentBuilder
        extends DefaultComponentBuilder<InfoPageComponent> {

    @Autowired
    protected InfoPageComponentHelper helper;

    public InfoPageAttributeComponent getAttributeForPath(final String key) throws ComponentException {
        final InfoPageComponent definition = this.getDefinition();
        for (final InfoPagePanelComponent component : definition.getContent()) {
            if (component.containsAttributeForPath(key)) {
                return component.getAttributeForPath(key);
            }
        }
        return null;
    }
}
