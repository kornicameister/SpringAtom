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

import com.google.common.base.MoreObjects;
import org.agatom.springatom.cmp.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.cmp.locale.SMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * {@code AbstractBuilder} is a root for all <b>component builder</b>. Defines root fields and functionality.
 * It is not an actual {@link org.agatom.springatom.cmp.component.core.builders.ComponentDataBuilder}
 * or {@link org.agatom.springatom.cmp.component.core.builders.ComponentDefinitionBuilder}, yet all concrete implementations
 * starts from it
 * <small>Class is a part of <b>SpringAtom</b> and was created at 27.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
abstract public class AbstractBuilder
        implements Builder {
    protected final Logger         logger        = LoggerFactory.getLogger(getClass());
    @Autowired
    protected       SMessageSource messageSource = null;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .toString();
    }

    /** {@inheritDoc} */
    @Override
    public String getId() {
        final Class<? extends AbstractBuilder> clazz = this.getClass();
        return String.valueOf(AnnotationUtils.getValue(clazz.getAnnotation(ComponentBuilder.class)));
    }
}