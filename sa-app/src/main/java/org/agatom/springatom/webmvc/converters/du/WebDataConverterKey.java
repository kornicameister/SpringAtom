/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.webmvc.converters.du;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-21</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import org.springframework.util.ClassUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Used in {@link java.util.Map} as the key for which converter will be picked up
 */
final class WebDataConverterKey {
    private final String         key;
    private final List<Class<?>> types;
    private final String         converterId;

    WebDataConverterKey(final String converterId, final String key, final Class<?>... types) {
        this.converterId = converterId;
        this.key = key;
        this.types = Lists.newArrayList();

        Collections.addAll(this.getTypes(), types);
        Collections.sort(this.getTypes(), new Comparator<Class<?>>() {

            @Override
            public int compare(final Class<?> o1, final Class<?> o2) {
                if (o1.equals(o2)) {
                    return 0;
                }
                final Class<?> commonAncestor = ClassUtils.determineCommonAncestor(o1, o2);
                if (commonAncestor == null) {
                    return ComparisonChain
                            .start()
                            .compare(getDistanceFromCommonAncestor(o1, Object.class), getDistanceFromCommonAncestor(o2, Object.class))
                            .result();
                }

                final int fromCommonAncestor1 = getDistanceFromCommonAncestor(o1, commonAncestor);
                final int fromCommonAncestor2 = getDistanceFromCommonAncestor(o2, commonAncestor);

                return ComparisonChain
                        .start()
                        .compare(fromCommonAncestor1, fromCommonAncestor2)
                        .result();
            }
        });
    }

    public List<Class<?>> getTypes() {
        return types;
    }

    private int getDistanceFromCommonAncestor(Class<?> from, final Class<?> to) {
        int distance = 0;
        while (from != null && !from.equals(to)) {
            distance++;
            from = from.getSuperclass();
        }
        return distance;
    }

    public boolean capableOfType(final Class<?> valueType) {
        for (Class<?> type : this.getTypes()) {
            if (ClassUtils.isAssignable(type, valueType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getConverterId(), getKey(), getConverterId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebDataConverterKey that = (WebDataConverterKey) o;

        return Objects.equal(this.getConverterId(), that.getKey()) &&
                Objects.equal(this.getKey(), that.getKey()) &&
                Objects.equal(this.getTypes(), that.getTypes());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getConverterId())
                .add("key", getKey())
                .add("types", getTypes())
                .toString();
    }

    public String getConverterId() {
        return converterId;
    }

    public String getKey() {
        return key;
    }
}
