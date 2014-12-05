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

package org.agatom.springatom.cmp.component.core.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.core.annotations.LazyComponent;
import org.agatom.springatom.data.repositories.provider.RepositoriesHelper;
import org.agatom.springatom.data.types.PersistentVersionedBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code ComponentContextFactory} enables populating the context from {@link org.springframework.data.domain.Persistable}
 * object, by resolving information like {@link org.agatom.springatom.cmp.component.core.context.ComponentContext#version}
 * and {@link org.agatom.springatom.cmp.component.core.context.ComponentContext#revision}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 12.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@LazyComponent
public class ComponentContextFactory {
    private static final Logger             LOGGER       = LoggerFactory.getLogger(ComponentContextFactory.class);
    @Autowired
    private              RepositoriesHelper repositories = null;
    @Autowired
    private              ObjectMapper       objectMapper = null;

    @SuppressWarnings("unchecked")
    public ComponentContext buildContext(final HttpServletRequest request) throws Exception {
        final String context = request.getParameter("context");
        if (!StringUtils.hasText(context)) {
            return new MissingComponentContext();
        }

        return this.objectMapper.readValue(context, ComponentContext.class);
    }

    public ComponentContext buildContext(final Persistable<?> persistable) throws Exception {
        Assert.notNull(persistable, "Persistable can not be null");
        LOGGER.debug(String.format("buildContext(persistable=%s)", persistable));

        final ComponentContext componentContext = new ComponentContext(persistable);
        if (ClassUtils.isAssignableValue(PersistentVersionedBean.class, persistable)) {
            componentContext.setVersion(((PersistentVersionedBean) persistable).getVersion());
        } else {
            componentContext.setVersion((long) -1);
        }
        return componentContext;
    }

    private class MissingComponentContext
            extends ComponentContext {
        private static final long serialVersionUID = -1784967006215448033L;

        public MissingComponentContext() {
            super(null);
        }

        @Override
        public boolean isValid() {
            return false;
        }
    }

}