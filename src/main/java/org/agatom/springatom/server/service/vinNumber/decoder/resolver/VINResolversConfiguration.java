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

package org.agatom.springatom.server.service.vinNumber.decoder.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.core.module.AbstractModuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 11.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Configuration(value = VINResolversConfiguration.MODULE_NAME)
public class VINResolversConfiguration
		extends AbstractModuleConfiguration {

	protected static final String       MODULE_NAME  = "vinResolversConfiguration";
	@Autowired
	@Qualifier(value = "jackson2ObjectFactoryBean")
	private                ObjectMapper objectMapper = null;

	@Bean
	public WMIManufacturedInResolver getWmiManufacturedInResolver() {
		final DefaultWMIManufacturedInResolver resolver = new DefaultWMIManufacturedInResolver();
		resolver.setObjectMapper(this.objectMapper);
		return resolver;
	}

	@Bean
	public VISYearResolver getVisYearResolver() {
		return new DefaultVISYearResolver();
	}

}
