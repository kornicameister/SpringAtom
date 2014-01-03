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

package org.agatom.springatom.web.infopages.component.elements;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.elements.PanelComponent;
import org.agatom.springatom.web.infopages.component.elements.attributes.InfoPageAttributeComponent;
import org.agatom.springatom.web.infopages.component.elements.meta.PanelHolds;

import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPagePanelComponent
        extends PanelComponent<InfoPageAttributeComponent> {
    private transient Map<String, InfoPageAttributeComponent> attributesPath = Maps.newHashMap();
    private           PanelHolds                              holds          = null;

    public InfoPageAttributeComponent getAttributeForPath(final String path) {
        return attributesPath.get(path);
    }

    public boolean containsAttributeForPath(final String path) {
        return attributesPath.containsKey(path);
    }

    @Override
    public boolean addContent(final InfoPageAttributeComponent infoPageAttributeComponent) {
        final boolean addContent = super.addContent(infoPageAttributeComponent);
        if (addContent) {
            final String path = infoPageAttributeComponent.getPath();
            this.attributesPath.put(path, infoPageAttributeComponent);
        }
        return addContent;
    }

    public PanelHolds getHolds() {
        if (this.holds == null) {
            this.holds = PanelHolds.BASIC_ATTRIBUTES;
        }
        return holds;
    }

    public InfoPagePanelComponent setHolds(final PanelHolds holds) {
        this.holds = holds;
        return this;
    }

    public Set<InfoPageAttributeComponent> getAttributes() {
        return this.getContent();
    }

    public boolean isBasicAttributesHolder() {
        return this.getHolds().equals(PanelHolds.BASIC_ATTRIBUTES);
    }

    public boolean isSystemAttributesHolder() {
        return this.getHolds().equals(PanelHolds.SYSTEM_ATTRIBUTES);
    }

    public boolean isOneToManyAttributesHolder() {
        return this.getHolds().equals(PanelHolds.ONE_TO_MANY_ATTRIBUTES);
    }

    public boolean isManyToOneAttributesHolder() {
        return this.getHolds().equals(PanelHolds.MANY_TO_ONE_ATTRIBUTES);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(holds, type, layout, content, title);
    }
}
