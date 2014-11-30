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

package org.agatom.springatom.cmp.component.core.builders;

import org.agatom.springatom.cmp.component.ComponentCompilationException;
import org.agatom.springatom.cmp.component.core.builders.exception.ComponentException;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.agatom.springatom.cmp.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.cmp.component.table.elements.TableComponent;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * <p>Abstract AbstractComponentDefinitionBuilder class.</p>
 * Contains information about:
 * <ol>
 * <li>{@link org.agatom.springatom.cmp.component.core.builders.ComponentProduces}, which depends on {@link #getBuilds()}</li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.4
 * @since 0.0.1
 */
abstract public class AbstractComponentDefinitionBuilder<COMP extends Serializable>
        extends AbstractComponentDataBuilder
        implements ComponentDefinitionBuilder<COMP> {
    private Class<?>          builds            = getBuilds();
    private ComponentProduces componentProduces = getProduces();

    /** {@inheritDoc} */
    @Override
    public final ComponentProduces getProduces() {
        if (this.componentProduces == null) {
            if (ClassUtils.isAssignable(InfoPageComponent.class, this.getBuilds())) {
                this.componentProduces = ComponentProduces.PAGE_COMPONENT;
            } else if (ClassUtils.isAssignable(TableComponent.class, this.getBuilds())) {
                this.componentProduces = ComponentProduces.TABLE_COMPONENT;
            } else {
                this.componentProduces = ComponentProduces.OTHER_COMPONENT;
            }
            this.logger.trace(String.format("%s produces %s", ClassUtils.getShortName(this.getClass()), this.componentProduces));
        }
        return componentProduces;
    }

    /** {@inheritDoc} */
    @Override
    public final Class<?> getBuilds() {
        if (this.builds == null) {
            this.builds = GenericTypeResolver.resolveTypeArgument(getClass(), ComponentDefinitionBuilder.class);
            this.logger.trace(String.format("%s builds %s", ClassUtils.getShortName(this.getClass()), ClassUtils.getShortName(this.builds)));
        }
        return this.builds;
    }

    /** {@inheritDoc} */
    @Override
    public final COMP getDefinition(final ComponentDataRequest dataRequest) throws ComponentException {
        this.logger.debug(String.format("getDefinition(dataRequest=%s)", dataRequest));
        final long startTime = System.nanoTime();
        try {
            Assert.notNull(dataRequest, "DataRequest can not be null");

            final COMP definition = this.buildDefinition(dataRequest);

            if (definition == null) {
                this.logger.warn(String.format("For request=%s, builder returned null definition", dataRequest));
            } else {
                this.logger.trace(String.format("For request=%s, builder returned definition=%s", dataRequest, definition));
            }

            final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            this.logger.info(String.format("getDefinition(dataRequest=%s) executed in %dms", dataRequest, endTime));

            return definition;
        } catch (Exception exp) {
            this.logger.fatal(String.format("getDefinition(dataRequest=%s) failed...", dataRequest), exp);
            throw new ComponentCompilationException(exp);
        }
    }

    /**
     * Actual method to create definition. Param is used to create conditional definition based on data available in the request
     *
     * @param dataRequest request to work with
     *
     * @return the definition of {@link #getBuilds()}
     *
     * @throws org.agatom.springatom.cmp.component.core.builders.exception.ComponentException if any
     */
    protected abstract COMP buildDefinition(final ComponentDataRequest dataRequest) throws ComponentException;

}
