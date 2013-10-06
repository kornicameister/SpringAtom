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

package org.agatom.springatom.web.support.actions.beans;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@XmlType(name = "action", propOrder = {
        "name",
        "type",
        "definitions"
})
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SActionModel
        implements Serializable {
    @XmlAttribute(name = "name", required = true)
    private String                 name;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(type = String.class, value = SActionType.SActionTypeAdapter.class)
    private SActionType            type;
    @XmlElements(value = @XmlElement(name = "action", nillable = false, required = true, type = SActionDefinition.class))
    private Set<SActionDefinition> definitions;

    public String getName() {
        return name;
    }

    public SActionModel setName(final String name) {
        this.name = name;
        return this;
    }

    public SActionType getType() {
        return type;
    }

    public SActionModel setType(final SActionType type) {
        this.type = type;
        return this;
    }

    public Set<SActionDefinition> getDefinitions() {
        return definitions;
    }

    public SActionModel setDefinitions(final Set<SActionDefinition> definitions) {
        this.definitions = definitions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SActionModel that = (SActionModel) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.type, that.type) &&
                Objects.equal(this.definitions, that.definitions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, type, definitions);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(name)
                      .addValue(type)
                      .addValue(definitions)
                      .toString();
    }
}
