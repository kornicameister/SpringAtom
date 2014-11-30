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

package org.agatom.springatom.cmp.component.infopages;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResourceLoader;

/**
 * <p>InfoPageModuleConfiguration class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration(value = InfoPageModuleConfiguration.MODULE_NAME)
@ComponentScan(
        basePackageClasses = InfoPageModuleConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Configuration.class})
        }
)
@PropertySource(value = InfoPageModuleConfiguration.LOCATION)
public class InfoPageModuleConfiguration {
    /** Constant <code>MODULE_NAME="ipConfiguration"</code> */
    protected static final String MODULE_NAME = "ipConfiguration";
    /** Constant <code>LOCATION="classpath:org/agatom/springatom/web/com"{trunked}</code> */
    protected static final String LOCATION    = "classpath:org/agatom/springatom/cmp/ip/infopages.properties";

    /**
     * <p>getInfoPageMappingProperties.</p>
     *
     * @return a {@link org.springframework.beans.factory.config.PropertiesFactoryBean} object.
     */
    @Bean(name = "infoPageProperties")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    @Role(BeanDefinition.ROLE_SUPPORT)
    public PropertiesFactoryBean getInfoPageMappingProperties() {
        final PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(new FileSystemResourceLoader().getResource(LOCATION));
        return factoryBean;
    }

}
