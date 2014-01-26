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

package org.agatom.springatom.server.model.descriptors;

import org.agatom.springatom.core.module.AbstractModuleConfiguration;
import org.agatom.springatom.server.model.descriptors.descriptor.DynamicEntityDescriptorReader;
import org.agatom.springatom.server.model.descriptors.descriptor.EntityDescriptors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
        extends AbstractModuleConfiguration {
    private static final Logger LOGGER = Logger.getLogger(DomainDescriptorModuleConfig.class);
    @Autowired
    private EntityManagerFactory manager;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EntityDescriptorReader getDomainTypeReader() {
        this.logRegistering(DynamicEntityDescriptorReader.class, LOGGER);
        final DynamicEntityDescriptorReader reader = new DynamicEntityDescriptorReader();
        reader.setMetamodel(this.manager.getMetamodel());
        return reader;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EntityDescriptors getEntityDescriptors() {
        this.logRegistering(EntityDescriptors.class, LOGGER);
        final EntityDescriptors entityDescriptors = new EntityDescriptors();
        entityDescriptors.setReader(this.getDomainTypeReader());
        return entityDescriptors;
    }

}
