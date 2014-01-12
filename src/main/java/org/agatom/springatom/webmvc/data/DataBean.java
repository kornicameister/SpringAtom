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
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.beans.WebBean;
import org.springframework.ui.ModelMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class DataBean
        implements WebBean,
                   Iterable<String> {
    private static final String             BEAN_ID          = "restDataBean";
    private static final long               serialVersionUID = -8852958687534065708L;
    private              Set<DataBeanValue> values           = Sets.newHashSet();

    public Set<DataBeanValue> getValues() {
        return this.values;
    }

    public void setValues(final Set<DataBeanValue> values) {
        this.values = values;
    }

    public ModelMap toModelMap() {
        final ModelMap modelMap = new ModelMap();
        for (final DataBeanValue beanValue : this.getValues()) {
            modelMap.addAttribute(beanValue.getKey(), beanValue.getValue());
        }
        return modelMap;
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }

    @Override
    public Iterator<String> iterator() {
        return this.keySet().iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(values)
                      .toString();
    }

    public int size() {
        return this.values.size();
    }

    public boolean isEmpty() {
        return this.values.size() == 0;
    }

    public boolean containsKey(final Object key) {
        return key instanceof String && this.keySet().contains(key);
    }

    public boolean containsValue(final Object value) {
        return value instanceof DataBeanValue && this.values.contains(value);
    }

    public DataBeanValue get(final Object key) {
        if (key instanceof String) {
            final Optional<DataBeanValue> match = FluentIterable
                    .from(this.values)
                    .firstMatch(new Predicate<DataBeanValue>() {
                        @Override
                        public boolean apply(@Nullable final DataBeanValue input) {
                            assert input != null;
                            return input.getKey().equals(key);
                        }
                    });
            if (match.isPresent()) {
                return match.get();
            }
        }
        return null;
    }

    public DataBeanValue put(final DataBeanValue value) {
        if (this.values.add(value)) {
            return value;
        }
        return null;
    }

    public DataBeanValue remove(final Object key) {
        DataBeanValue value = this.get(key);
        if (value != null && this.values.remove(value)) {
            return value;
        }
        return null;
    }

    public void clear() {
        this.values.clear();
    }

    @Nonnull
    public Set<String> keySet() {
        final Set<String> keys = Sets.newHashSet();
        for (final DataBeanValue value : this.values) {
            keys.add(value.getKey());
        }
        return keys;
    }
}
