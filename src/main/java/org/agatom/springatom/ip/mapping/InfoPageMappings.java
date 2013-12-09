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

package org.agatom.springatom.ip.mapping;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.agatom.springatom.ip.DomainInfoPage;
import org.agatom.springatom.ip.InfoPage;
import org.agatom.springatom.ip.config.InfoPageConfigurationSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class InfoPageMappings
        implements BeanFactoryAware,
                   Iterable<Class<?>> {
    private final Map<Class<?>, InfoPage>     domainToPageCache           = Maps.newHashMap();
    private final Map<String, InfoPage>       pathToPageCache             = Maps.newHashMap();
    private final List<String>                infoPageBeanNames           = Lists.newLinkedList();
    private       ListableBeanFactory         beanFactory                 = null;
    private       InfoPageConfigurationSource infoPageConfigurationSource = null;

    public DomainInfoPage getInfoPageForDomain(final Class<?> domainClass) {
        if (this.domainToPageCache.containsKey(domainClass)) {
            return (DomainInfoPage) this.domainToPageCache.get(domainClass);
        }
        final InfoPage infoPage = this.infoPageConfigurationSource.getFromDomain(domainClass);
        if (infoPage != null) {
            this.domainToPageCache.put(domainClass, infoPage);
            return (DomainInfoPage) infoPage;
        }
        return null;
    }

    public InfoPage getInfoPageForPath(final String path) {
        if (this.pathToPageCache.containsKey(path)) {
            return this.pathToPageCache.get(path);
        }
        final InfoPage infoPage = this.infoPageConfigurationSource.getFromPath(path);
        if (infoPage != null) {
            this.pathToPageCache.put(path, infoPage);
            return infoPage;
        }
        return null;
    }

    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    public InfoPageConfigurationSource getInfoPageConfigurationSource() {
        return infoPageConfigurationSource;
    }

    public void setInfoPageConfigurationSource(final InfoPageConfigurationSource ipcs) {
        this.infoPageConfigurationSource = ipcs;
    }

    @Override
    public Iterator<Class<?>> iterator() {
        final Set<Class<?>> keysAsClazz = this.infoPageConfigurationSource.getAllInfoPageClasses();
        return keysAsClazz.iterator();
    }
}
