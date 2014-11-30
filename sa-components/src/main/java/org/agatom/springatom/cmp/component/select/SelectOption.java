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

package org.agatom.springatom.cmp.component.select;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * {@code SelectOption} is a server side model of single entry in HTML select box
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-25</small>
 * </p>
 *
 * @param <V> corresponds to select box value tag
 * @param <L> corresponds to select box display value
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class SelectOption<V, L>
        implements Serializable {
    private static final long   serialVersionUID = 5287521168388848499L;
    /**
     * It is an actual value that lies and corresponds to the {@link #label}
     */
    private              V      value            = null;
    /**
     * Displays in select box. Human readable value
     */
    private              L      label            = null;
    /**
     * Can be used in view to display the tooltip
     */
    private              String tooltip          = null;

    public L getLabel() {
        return label;
    }

    public SelectOption<V, L> setLabel(final L label) {
        this.label = label;
        return this;
    }

    public V getValue() {
        return value;
    }

    public SelectOption<V, L> setValue(final V value) {
        this.value = value;
        return this;
    }

    public String getTooltip() {
        return tooltip;
    }

    public SelectOption<V, L> setTooltip(final String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("label", label)
                .toString();
    }
}
