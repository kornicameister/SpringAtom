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

package org.agatom.springatom.web.infopages.config;

import org.agatom.springatom.core.processors.AbstractAnnotationBeanPostProcessorAdapter;
import org.agatom.springatom.web.infopages.SInfoPage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

class InfoPageAwareBeanPostProcessor
        extends AbstractAnnotationBeanPostProcessorAdapter
        implements BeanFactoryAware {
    private String basePackage;

    public void setBasePackage(final String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(InfoPageAwareBeanPostProcessor.class);
    }

    @Override
    protected boolean isProcessable(final Object bean) {
        return this.isInPackage(bean.getClass().getName()) || bean instanceof SInfoPage;
    }

    private boolean isInPackage(final String name) {
        return name != null && name.contains(this.basePackage);
    }
}
