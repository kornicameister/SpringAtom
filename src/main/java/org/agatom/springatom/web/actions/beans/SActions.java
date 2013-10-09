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

package org.agatom.springatom.web.actions.beans;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@XmlRootElement(name = "actions")
@XmlType(name = "model", propOrder = {
        "component",
        "actionModels"
})
@XmlSeeAlso(value = {
        SActionModel.class
})
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SActions
        implements Serializable {
    @XmlAttribute(name = "component", required = true)
    private String            component;
    @XmlElements(value = @XmlElement(name = "model", nillable = false, type = SActionModel.class))
    private Set<SActionModel> actionModels;

    public String getComponent() {
        return component;
    }

    public SActions setComponent(final String component) {
        this.component = component;
        return this;
    }

    public Set<SActionModel> getModel() {
        if (actionModels == null) {
            return Sets.newHashSet();
        }
        return actionModels;
    }

    public SActions setModel(final Set<SActionModel> model) {
        this.actionModels = model;
        return this;
    }

    public SActionDefinition getDefinitionForPath(final String actionPath) {
        final ArrayList<String> path = Lists.newArrayList(actionPath.split("\\."));
        final String componentName = path.remove(0);
        if (this.component.equals(componentName)) {
            for (final SActionModel action : this.actionModels) {
                if (action.getName().equals(path.get(0))) {
                    path.remove(0);
                    for (final SActionDefinition actionDefinition : action.getDefinitions()) {
                        if (actionDefinition.getName().equals(path.get(0))) {
                            return actionDefinition;
                        }
                    }
                }
            }
        }
        return null;
    }
}
