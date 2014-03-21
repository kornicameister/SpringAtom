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

package org.agatom.springatom.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 21.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@ComponentScan(
		basePackageClasses = RepositoryRestController.class,
		includeFilters = @ComponentScan.Filter(RepositoryRestController.class),
		useDefaultFilters = false
)
public class SARepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

	public SARepositoryRestMvcConfiguration() {
		super();
	}

	@Override
	protected void configureJacksonObjectMapper(final ObjectMapper objectMapper) {
		super.configureJacksonObjectMapper(objectMapper);

		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.disable(SerializationFeature.INDENT_OUTPUT);

		objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}
}
