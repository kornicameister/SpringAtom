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

package org.agatom.springatom.web.component.select.factory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.core.annotations.LazyComponent;
import org.agatom.springatom.web.component.ComponentCompilationException;
import org.agatom.springatom.web.component.select.SelectComponent;
import org.agatom.springatom.web.component.select.SelectOption;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-24</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */

@LazyComponent("selectComponentFactory")
@Role(BeanDefinition.ROLE_SUPPORT)
class SelectComponentFactoryImpl
        implements SelectComponentFactory {
    private static final Logger LOGGER = Logger.getLogger(SelectComponentBuilderImpl.class);

    @Override
    public <V, L, D> SelectComponentBuilder<V, L, D> newSelectComponent() {
        LOGGER.debug("newSelectComponent()");
        return this.newSelectComponent(null);
    }

    @Override
    public <V, L, D> SelectComponentBuilder<V, L, D> newSelectComponent(final String label) {
        LOGGER.debug(String.format("newSelectComponent(label=%s)", label));
        return new SelectComponentBuilderImpl<>(label);
    }

    private class SelectComponentBuilderImpl<V, L, D>
            implements SelectComponentBuilder<V, L, D> {
        private final Logger         LOGGER          = Logger.getLogger(SelectComponentBuilder.class);
        private       String         label           = null;
        private       Collection<D>  data            = null;
        private       Comparator<V>  valueComparator = null;
        private       Function<D, L> labelFunction   = null;
        private       Function<D, V> valueFunction   = null;

        private SelectComponentBuilderImpl(final String label) {
            this.label = label;
        }

        @Override
        public SelectComponentBuilder<V, L, D> from(final Collection<D> data) {
            Assert.notNull(data, "Data collection can not be null");
            this.data = data;
            return this;
        }

        @Override
        public SelectComponentBuilder<V, L, D> withValueOrder(final Comparator<V> valueComparator) {
            this.valueComparator = valueComparator;
            return this;
        }

        @Override
        public SelectComponentBuilder<V, L, D> usingLabelFunction(final Function<D, L> labelFunction) {
            Assert.notNull(labelFunction, "Label function can not be null");
            this.labelFunction = labelFunction;
            return this;
        }

        @Override
        public SelectComponentBuilder<V, L, D> usingValueFunction(final Function<D, V> valueFunction) {
            Assert.notNull(valueFunction, "Value function can not be null");
            this.valueFunction = valueFunction;
            return this;
        }

        @Override
        public SelectComponent<V, L> get() throws ComponentCompilationException {
            final long startTime = System.nanoTime();
            try {
                final SelectComponent<V, L> cmp = new SelectComponent<>();

                cmp.setLabel(this.label);
                cmp.setOptions(this.getOptions());

                final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

                cmp.addDynamicProperty("compilationTime", endTime);
                cmp.addDynamicProperty("builder", ClassUtils.getShortName(this.getClass()));

                LOGGER.trace(String.format("Compilation of SelectComponent took %d ms", endTime));

                return cmp;
            } catch (Exception exp) {
                LOGGER.error("Failed to compile SelectComponent", exp);
                throw new ComponentCompilationException(exp);
            }
        }

        private Collection<SelectOption<V, L>> getOptions() throws Exception {
            final Collection<SelectOption<V, L>> options;

            if (this.valueComparator != null) {
                options = Sets.newTreeSet(new Comparator<SelectOption<V, L>>() {

                    @Override
                    public int compare(final SelectOption<V, L> o1, final SelectOption<V, L> o2) {
                        return valueComparator.compare(o1.getValue(), o2.getValue());
                    }

                });
            } else {
                options = Lists.newArrayListWithExpectedSize(this.data.size());
            }

            try {
                for (final D dataChunk : this.data) {
                    if (LOGGER.isTraceEnabled()) {
                        LOGGER.trace(String.format("Processing dataChunk=%s", dataChunk));
                    }

                    final L label = this.labelFunction.apply(dataChunk);
                    final V value = this.valueFunction.apply(dataChunk);

                    // assert valid values
                    Assert.notNull(label, String.format("%s returned null key, this is not acceptable", this.labelFunction));
                    Assert.notNull(value, String.format("%s returned null value, this is not acceptable", this.valueFunction));

                    if (LOGGER.isTraceEnabled()) {
                        LOGGER.trace(String.format("Found pair [key=%s, value=%s]", label, value));
                    }

                    options.add(
                            new SelectOption<V, L>()
                                    .setLabel(label)
                                    .setValue(value)
                    );
                }
            } catch (Exception exp) {
                LOGGER.error(String.format("Failed to retrieve optionsMap for %d entries", this.data.size()));
                throw exp;
            }
            return options;
        }


    }
}
