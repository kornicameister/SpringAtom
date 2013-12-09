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

package org.agatom.springatom.server.model.descriptors;

import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.agatom.springatom.server.model.descriptors.reader.EntityDescriptorReader;
import org.agatom.springatom.server.model.descriptors.reader.impl.DynamicEntityDescriptorReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;

import javax.persistence.EntityManagerFactory;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Lazy(value = false)
@Configuration(value = "DDMConfiguration")
@ComponentScan(
        basePackages = {"org.agatom.springatom.server.model.descriptors.descriptor"}
)
public class DomainDescriptorModuleConfig
        implements BeanFactoryAware,
                   ApplicationContextAware {
    private ListableBeanFactory  factoryContext;
    private ApplicationContext   applicationContext;
    @Autowired
    private EntityManagerFactory manager;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.factoryContext = (ListableBeanFactory) beanFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EntityDescriptorReader getDomainTypeReader() {
        final DynamicEntityDescriptorReader reader = new DynamicEntityDescriptorReader();
        reader.setMetamodel(this.manager.getMetamodel());
        reader.setBeanFactory(this.factoryContext);
        reader.setApplicationContext(this.applicationContext);
        return reader;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EntityDescriptors getEntityDescriptors() {
        final EntityDescriptors entityDescriptors = new EntityDescriptors();
        entityDescriptors.setReader(this.getDomainTypeReader());
        return entityDescriptors;
    }

}
