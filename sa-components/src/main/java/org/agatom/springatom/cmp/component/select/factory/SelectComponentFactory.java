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

package org.agatom.springatom.cmp.component.select.factory;

import com.google.common.base.Function;
import org.agatom.springatom.cmp.component.ComponentCompilationException;
import org.agatom.springatom.cmp.component.select.SelectComponent;

import java.util.Comparator;
import java.util.Locale;

/**
 * {@code SelectComponentFactory} is a component which allows to create new {@link org.agatom.springatom.cmp.component.select.SelectComponent}
 * with set of helpers methods to extract values from passed collection of entities
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-24</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SelectComponentFactory {
    <V, L, D> SelectComponentBuilder<V, L, D> newSelectComponent();

    <V, L, D> SelectComponentBuilder<V, L, D> newSelectComponent(final String label);

    SelectComponent<String, String> fromEnumeration(final String key) throws ComponentCompilationException;

    SelectComponent<String, String> fromEnumeration(final String key, final Locale locale) throws ComponentCompilationException;

    /**
     * Interface describes process of {@link org.agatom.springatom.cmp.component.select.SelectComponent} compilation.
     * It gives an ability to set a {@code data} collection which is a data source used to build {@link org.agatom.springatom.cmp.component.select.SelectOption}.
     * By specifying {@code labelFunction} and {@code valueFunction} it is possible to provide appropriate logic to retrieve:
     * <ol>
     * <li>{@link org.agatom.springatom.cmp.component.select.SelectOption#label}</li>
     * <li>{@link org.agatom.springatom.cmp.component.select.SelectOption#value}</li>
     * </ol>
     * out of {@code <D>} object
     *
     * @param <V> {@link org.agatom.springatom.cmp.component.select.SelectOption#value} generic type
     * @param <L> {@link org.agatom.springatom.cmp.component.select.SelectOption#label} generic type
     * @param <D> {@code data} input generic type
     *
     * @author trebskit
     * @version 0.0.1
     * @since 0.0.1
     */
    interface SelectComponentBuilder<V, L, D> {
        /**
         * Sets raw data to be used to create options
         *
         * @param data raw data
         *
         * @return builder instance
         */
        SelectComponentBuilder<V, L, D> from(final Iterable<D> data);

        /**
         * Sets {@link java.util.Comparator} to order {@link org.agatom.springatom.cmp.component.select.SelectOption#value} values
         *
         * @param keyOrder comparator
         *
         * @return builder instance
         */
        SelectComponentBuilder<V, L, D> withValueOrder(final Comparator<V> keyOrder);

        /**
         * {@link com.google.common.base.Function} to retrieve {@code label} of {@link org.agatom.springatom.cmp.component.select.SelectOption}
         *
         * @param func function
         *
         * @return builder instance
         */
        SelectComponentBuilder<V, L, D> usingLabelFunction(final Function<D, L> func);

        /**
         * {@link com.google.common.base.Function} to retrieve {@code value} of {@link org.agatom.springatom.cmp.component.select.SelectOption}
         *
         * @param func function
         *
         * @return builder instance
         */
        SelectComponentBuilder<V, L, D> usingValueFunction(final Function<D, V> func);

        /**
         * {@link com.google.common.base.Function} to retrieve {@code tooltip} of {@link org.agatom.springatom.cmp.component.select.SelectOption}
         *
         * @param func function
         *
         * @return builder instance
         */
        SelectComponentBuilder<V, L, D> usingTooltipFunction(final Function<D, String> func);

        /**
         * Returns populated and fully initalized {@link org.agatom.springatom.cmp.component.select.SelectComponent}
         *
         * @return select component
         *
         * @throws ComponentCompilationException if any. Wraps other exceptions.
         */
        SelectComponent<V, L> get() throws ComponentCompilationException;
    }

}
