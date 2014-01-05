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

package org.agatom.springatom.web.action;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.hateoas.Identifiable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component(value = "linkAction")
@Role(value = BeanDefinition.ROLE_SUPPORT)
public class LinkAction
        implements Identifiable<String> {
    protected String url = null;

    public LinkAction setUrl(final String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public String getMode() {
        return StringUtils.uncapitalize((String) AnnotationUtils.getValue(this.getClass().getAnnotation(Component.class)));
    }

    @Override
    public String getId() {
        return String.format("%s-%d", ClassUtils.getShortName(this.getClass()), Math.abs(this.hashCode()));
    }
}
