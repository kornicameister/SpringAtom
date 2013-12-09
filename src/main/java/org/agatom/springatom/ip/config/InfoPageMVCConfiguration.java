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

package org.agatom.springatom.ip.config;

import org.agatom.springatom.ip.mapping.InfoPageMappings;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy(false)
@Configuration(value = "InfoPageConfiguration")
@PropertySource(value = "classpath:org/agatom/springatom/webmvc/webmvc.properties")
public class InfoPageMVCConfiguration
        implements BeanFactoryAware,
                   ApplicationContextAware {
    private static final Logger LOGGER = Logger.getLogger(InfoPageMVCConfiguration.class);
    private ListableBeanFactory beanFactory;
    private ApplicationContext  applicationContext;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public InfoPageMappings getInfoPageMapping() {
        final InfoPageMappings mappings = new InfoPageMappings();
        mappings.setBeanFactory(this.beanFactory);
        mappings.setInfoPageConfigurationSource(this.getInfoPageConfigurationSource());
        return mappings;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public InfoPageComponentProvider getInfoPageComponentProvider() {
        return new InfoPageComponentProvider();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public InfoPageAwareBeanPostProcessor getInfoPagePostProcessor() {
        final InfoPageAwareBeanPostProcessor processor = new InfoPageAwareBeanPostProcessor();
        processor.setBeanFactory(this.beanFactory);
        processor.setBasePackage(this.applicationContext.getEnvironment().getProperty("springatom.infoPages.basePackage"));
        return processor;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public InfoPageConfigurationSourceImpl getInfoPageConfigurationSource() {
        final InfoPageConfigurationSourceImpl source = new InfoPageConfigurationSourceImpl()
                .setProvider(this.getInfoPageComponentProvider())
                .setBasePackage(this.applicationContext.getEnvironment().getProperty("springatom.infoPages.basePackage"));
        source.setApplicationContext(this.applicationContext);
        return source;
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ListableBeanFactory) {
            this.beanFactory = (ListableBeanFactory) beanFactory;
            LOGGER.trace(String.format("/setBeanFactory -> %s", beanFactory));
            return;
        }
        throw new BeanInitializationException(String.format("%s is not %s", beanFactory, ListableBeanFactory.class));
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
