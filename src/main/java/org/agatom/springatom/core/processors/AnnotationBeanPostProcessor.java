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

package org.agatom.springatom.core.processors;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactoryAware;

import java.beans.PropertyDescriptor;

/**
 * <p>AnnotationBeanPostProcessor interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface AnnotationBeanPostProcessor
		extends BeanFactoryAware {
	/**
	 * <p>postProcessOverAnnotation.</p>
	 *
	 * @param pvs      a {@link org.springframework.beans.PropertyValues} object.
	 * @param pds      an array of {@link java.beans.PropertyDescriptor} objects.
	 * @param beanName a {@link java.lang.String} object.
	 *
	 * @return a {@link org.springframework.beans.PropertyValues} object.
	 */
	PropertyValues postProcessOverAnnotation(PropertyValues pvs, PropertyDescriptor[] pds, String beanName);

	/**
	 * <p>resolveValueFromAnnotation.</p>
	 *
	 * @param pdName   a {@link java.lang.String} object.
	 * @param beanName a {@link java.lang.String} object.
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	Object resolveValueFromAnnotation(String pdName, String beanName);
}
