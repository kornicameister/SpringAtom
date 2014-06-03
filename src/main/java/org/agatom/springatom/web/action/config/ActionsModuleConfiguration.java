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

package org.agatom.springatom.web.action.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.core.module.AbstractModuleConfiguration;
import org.agatom.springatom.web.action.reader.ActionsModelReader;
import org.agatom.springatom.web.action.reader.impl.DefaultActionsModelReader;
import org.agatom.springatom.web.locale.SMessageSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 29.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration(value = ActionsModuleConfiguration.MODULE_NAME)
@PropertySource(value = "classpath:org/agatom/springatom/web/action/actions.properties")
public class ActionsModuleConfiguration
		extends AbstractModuleConfiguration {
	/** Constant <code>MODULE_NAME="actionsModule"</code> */
	protected static final String         MODULE_NAME   = "actionsModule";
	private static final   Logger         LOGGER        = Logger.getLogger(ActionsModuleConfiguration.class);
	@Autowired
	private                Environment    environment   = null;
	@Autowired
	@Qualifier("jackson2ObjectFactoryBean")
	private                ObjectMapper   mapper        = null;
	@Autowired
	private                SMessageSource messageSource = null;

	/**
	 * <p>getActionsModelReader.</p>
	 *
	 * @return a {@link org.agatom.springatom.web.action.reader.ActionsModelReader} object.
	 */
	@Lazy(false)
	@Bean(name = "actionsModelReader")
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public ActionsModelReader getActionsModelReader() {
		this.logRegistering(DefaultActionsModelReader.class, LOGGER);
		final DefaultActionsModelReader modelReader = new DefaultActionsModelReader();
		modelReader.setModelFile(this.environment.getProperty("springatom.actions.modelSource"));
		modelReader.setParseOnLoad(this.environment.getProperty("springatom.actions.parseOnLoad", Boolean.class));
		modelReader.setObjectMapper(this.mapper);
		modelReader.setMessageSource(this.messageSource);
		return modelReader;
	}


}
