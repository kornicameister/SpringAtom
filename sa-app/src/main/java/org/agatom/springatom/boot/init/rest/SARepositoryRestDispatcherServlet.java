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

package org.agatom.springatom.boot.init.rest;

import org.springframework.data.rest.webmvc.RepositoryRestDispatcherServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 21.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
class SARepositoryRestDispatcherServlet
        extends RepositoryRestDispatcherServlet {
    private static final long serialVersionUID = 8217867511809606982L;

    public SARepositoryRestDispatcherServlet() {
        configure();
    }

    private void configure() {
        this.setContextClass(AnnotationConfigWebApplicationContext.class);
        this.setContextConfigLocation(SARepositoryRestMvcConfiguration.class.getName());
    }

    public SARepositoryRestDispatcherServlet(final WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
        configure();
    }
}
