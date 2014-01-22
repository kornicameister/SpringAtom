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

package org.agatom.springatom.web.du.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
// java annotations
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// java annotations
// spring annotations
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
// spring annotations
public @interface DataUtility {

    /**
     * The value indicates a logical data utility name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the component name, if any
     */
    String value();

}
