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

package org.agatom.springatom.web.component.builders;

import org.agatom.springatom.web.component.builders.annotation.ComponentBuilds;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ComponentBuilders {

    ComponentBuilder<?> getBuilder(final String componentId);

    ComponentBuilder<?> getBuilder(final String componentId, final ModelMap modelMap, final WebRequest request);

    ComponentBuilder<?> getBuilder(final Class<?> target, final ModelMap modelMap, final WebRequest request);

    ComponentBuilder<?> getBuilder(final Class<?> target, final ComponentBuilds.Produces produces, final ModelMap modelMap, final WebRequest request);

    boolean hasBuilder(Class<?> target);

    boolean hasBuilder(Class<?> target, ComponentBuilds.Produces produces);

    String getBuilderId(Class<?> target);

    String getBuilderId(Class<?> target, ComponentBuilds.Produces produces);

}
