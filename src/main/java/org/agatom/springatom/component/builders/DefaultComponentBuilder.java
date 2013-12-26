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

package org.agatom.springatom.component.builders;

import com.google.common.base.Objects;
import org.agatom.springatom.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.component.builders.exception.ComponentException;
import org.agatom.springatom.component.data.ComponentDataRequest;
import org.agatom.springatom.component.data.ComponentDataResponse;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class DefaultComponentBuilder<COMP extends Serializable>
        implements ComponentBuilder<COMP> {
    protected Logger logger = this.getLogger();
    private String                   id;
    private Class<?>                 builds;
    private ComponentBuilds.Produces produces;
    private COMP                     definition;
    private ComponentDataRequest     dataRequest;

    protected abstract Logger getLogger();

    @Override
    public final String getId() {
        return this.id;
    }

    @Override
    public Class<?> getBuilds() {
        return builds;
    }

    @Override
    public ComponentBuilds.Produces getProduces() {
        return produces;
    }

    public final void setProduces(final ComponentBuilds.Produces produces) {
        this.produces = produces;
    }

    @Override
    public ComponentDataResponse getData() throws ComponentException {
        return this.buildData(this.dataRequest);
    }

    @Override
    public final COMP getDefinition() throws ComponentException {
        if (this.definition == null) {
            this.definition = this.buildDefinition();
            this.postProcessDefinition(definition);
            logger.info(String.format("%s has definition %s", this.id, this.definition));
        }
        return this.definition;
    }

    protected void postProcessDefinition(final COMP definition) {

    }

    @Override
    public void init(final ComponentDataRequest dataRequest) {
        this.dataRequest = dataRequest;
    }

    public final void setBuilds(final Class<?> clazz) {
        this.builds = clazz;
    }

    public final void setId(final String id) {
        this.id = id;
    }

    protected abstract COMP buildDefinition() throws ComponentException;

    protected abstract ComponentDataResponse buildData(final ComponentDataRequest dataRequest) throws ComponentException;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(builds)
                      .addValue(id)
                      .addValue(definition)
                      .addValue(dataRequest)
                      .toString();
    }
}
