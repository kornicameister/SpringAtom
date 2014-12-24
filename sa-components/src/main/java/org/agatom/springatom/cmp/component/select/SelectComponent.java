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

import com.google.common.base.Objects;
import org.agatom.springatom.cmp.component.Component;

import java.util.Collection;

/**
 * {@code SelectComponent} represents HTML select component.
 * It is described by {@link #label} and {@link #options} whereas the most crucial part
 * of this class are <b>options</b> being a collection of {@link org.agatom.springatom.cmp.component.select.SelectOption}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-24</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class SelectComponent<K, V>
        extends Component {
    private static final long                           serialVersionUID = -8009416832581079382L;
    private              Collection<SelectOption<K, V>> options          = null;

    public Collection<SelectOption<K, V>> getOptions() {
        return this.options;
    }

    public SelectComponent setOptions(final Collection<SelectOption<K, V>> options) {
        this.options = options;
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("options", options)
                .add("label", label)
                .toString();
    }
}
