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

package org.agatom.springatom.web.js.meta;

import com.google.common.base.Objects;

import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class JSMetaModel {
    private String                      modelName;
    private String                      superModelName;
    private Set<String>                 fields;
    private Set<JSMetaModelAssociation> associations;
    private Class<?>                    bean;

    public String getSuperModelName() {
        return superModelName;
    }

    public JSMetaModel setSuperModelName(final String superModelName) {
        this.superModelName = superModelName;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public JSMetaModel setModelName(final String modelName) {
        this.modelName = modelName;
        return this;
    }

    public Set<String> getFields() {
        return fields;
    }

    public JSMetaModel setFields(final Set<String> fields) {
        this.fields = fields;
        return this;
    }

    public Set<JSMetaModelAssociation> getAssociations() {
        return associations;
    }

    public JSMetaModel setAssociations(final Set<JSMetaModelAssociation> associations) {
        this.associations = associations;
        return this;
    }

    public Class<?> getBean() {
        return bean;
    }

    public JSMetaModel setBean(final Class<?> bean) {
        this.bean = bean;
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

        JSMetaModel that = (JSMetaModel) o;

        return Objects.equal(this.modelName, that.modelName) &&
                Objects.equal(this.fields, that.fields) &&
                Objects.equal(this.associations, that.associations) &&
                Objects.equal(this.bean, that.bean);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(modelName, fields, associations, bean);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(modelName)
                      .addValue(fields)
                      .addValue(associations)
                      .addValue(bean)
                      .toString();
    }
}
