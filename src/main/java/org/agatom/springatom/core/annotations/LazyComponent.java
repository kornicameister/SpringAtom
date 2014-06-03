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

package org.agatom.springatom.core.annotations;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * {@link org.agatom.springatom.core.annotations.LazyComponent} combines three annotations:
 * <ol>
 * <li>{@link org.springframework.stereotype.Component}</li>
 * <li>{@link org.springframework.context.annotation.Lazy}</li>
 * <li>{@link org.springframework.context.annotation.Scope} with {@link org.springframework.beans.factory.config.ConfigurableBeanFactory#SCOPE_SINGLETON}</li>
 * </ol>
 * <p/>
 * which explicitly makes annotated bean as {@code lazy} and {@code singleton} at the same time
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// spring context
@Lazy
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public @interface LazyComponent {
	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 *
	 * @return the suggested component name, if any
	 */
	String value() default "";
}
