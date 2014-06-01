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

package org.agatom.springatom.web.component.infopages.config;

import org.agatom.springatom.core.module.AbstractModuleConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResourceLoader;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration(value = InfoPageModuleConfiguration.MODULE_NAME)
@PropertySource(value = InfoPageModuleConfiguration.LOCATION)
public class InfoPageModuleConfiguration
		extends AbstractModuleConfiguration {
	protected static final String MODULE_NAME = "ipConfiguration";
	protected static final String LOCATION = "classpath:org/agatom/springatom/web/component/infopages/infopages.properties";
	private static final   Logger LOGGER      = Logger.getLogger(InfoPageModuleConfiguration.class);

	@Bean(name = "infoPageProperties")
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	@Role(BeanDefinition.ROLE_SUPPORT)
	public PropertiesFactoryBean getInfoPageMappingProperties() {
		this.logRegistering(PropertiesFactoryBean.class, LOGGER);
		final PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
		factoryBean.setLocation(new FileSystemResourceLoader().getResource(LOCATION));
		return factoryBean;
	}

}
