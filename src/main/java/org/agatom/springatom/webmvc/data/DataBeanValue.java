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

package org.agatom.springatom.webmvc.data;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.springframework.hateoas.Identifiable;

import javax.annotation.Nonnull;
import javax.annotation.meta.When;
import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DataBeanValue
        implements Serializable,
                   Comparable<DataBeanValue>,
                   Identifiable<String> {

    protected String key;
    protected String value;

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String getId() {
        return this.key;
    }

    @Override
    public int compareTo(@Nonnull(when = When.ALWAYS) final DataBeanValue dataBean) {
        return ComparisonChain.start()
                              .compare(this.key, dataBean.getKey())
                              .compare(this.value, dataBean.getValue())
                              .result();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataBeanValue that = (DataBeanValue) o;

        return Objects.equal(this.key, that.key) &&
                Objects.equal(this.value, that.value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(key)
                      .addValue(value)
                      .toString();
    }
}
