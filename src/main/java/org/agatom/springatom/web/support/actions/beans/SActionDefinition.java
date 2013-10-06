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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@XmlType(name = "definition", propOrder = {
        "name",
        "view"
})
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SActionDefinition
        implements Serializable {
    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlAttribute(name = "view", required = true)
    private String view;
    @XmlAttribute(name = "uri", required = false)
    private String uri;

    public String getName() {
        return name;
    }

    public SActionDefinition setName(final String name) {
        this.name = name;
        return this;
    }

    public String getView() {
        return view;
    }

    public SActionDefinition setView(final String view) {
        this.view = view;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public SActionDefinition setUri(final String uri) {
        this.uri = uri;
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

        SActionDefinition that = (SActionDefinition) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.view, that.view) &&
                Objects.equal(this.uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, view, uri);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(name)
                      .addValue(view)
                      .addValue(uri)
                      .toString();
    }
}
