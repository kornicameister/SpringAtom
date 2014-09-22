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

package org.agatom.springatom.web.component.core.builders.annotation;

import java.lang.annotation.*;

/**
 * {@code EntityBased} marks given {@link org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder}
 * as capable of producing and/or definition/data for given entity
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Inherited
@Documented
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EntityBased {
    Class<?>[] entities() default {};

    Class<?> entity() default Void.class;
}
