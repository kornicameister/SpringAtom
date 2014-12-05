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

package org.agatom.springatom.cmp.component.core.builders;

import com.google.common.base.Preconditions;
import com.mysema.query.types.Path;
import org.agatom.springatom.cmp.component.ComponentCompilationException;
import org.agatom.springatom.cmp.component.core.builders.exception.ComponentException;
import org.agatom.springatom.cmp.component.core.data.ComponentDataRequest;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractComponentDataBuilder} is a <b>component builder</b> that can be extended
 * for builder returning nothing but data.
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 27.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractComponentDataBuilder
        extends AbstractBuilder
        implements ComponentDataBuilder {

    /** {@inheritDoc} */
    @Override
    public final Object getData(final ComponentDataRequest dataRequest) throws ComponentException {
        this.logger.debug(String.format("getData(dataRequest=%s)", dataRequest));
        final long startTime = System.nanoTime();
        try {
            Assert.notNull(dataRequest, "DataRequest can not be null");

            final Object data = this.buildData(dataRequest);

            if (data == null) {
                this.logger.warn(String.format("For request=%s, builder returned null data", dataRequest));
            } else {
                this.logger.trace(String.format("For request=%s, builder returned data=%s", dataRequest, data));
            }

            final long endTime = System.nanoTime() - startTime;
            this.logger.info(String.format("getData(dataRequest=%s) executed in %dms", dataRequest, TimeUnit.NANOSECONDS.toMillis(endTime)));

            return this.getAsImmutable(data);
        } catch (Exception exp) {
            this.logger.error(String.format("getData(dataRequest=%s) failed...", dataRequest), exp);
            throw new ComponentCompilationException(exp);
        }
    }

    /**
     * Implement this method to return actual data of this builder
     *
     * @param dataRequest request to work with
     *
     * @return response for this builder
     *
     * @throws org.agatom.springatom.cmp.component.core.builders.exception.ComponentException if any
     */
    protected abstract Object buildData(final ComponentDataRequest dataRequest) throws ComponentException;

    private Object getAsImmutable(final Object data) {
        if (ClassUtils.isAssignableValue(Collection.class, data)) {
            return Collections.unmodifiableCollection((Collection<?>) data);
        } else if (ClassUtils.isAssignableValue(Map.class, data)) {
            return Collections.unmodifiableMap((Map<?, ?>) data);
        }
        return data;
    }

    /**
     * Computes the attribute name out of {@link com.mysema.query.types.Path} through {@link com.mysema.query.types.PathMetadata}
     *
     * @param path to get the name of attribute from
     *
     * @return the name
     */
    protected String getAttributeName(final Object path) {
        Preconditions.checkNotNull(path, "Path can not be null");
        if (ClassUtils.isAssignableValue(Path.class, path)) {
            return ((Path<?>) path).getMetadata().getName();
        }
        return ObjectUtils.getDisplayString(path);
    }
}
