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

import org.agatom.springatom.rest.processors.InfoPageResourceProcessor;
import org.agatom.springatom.web.component.infopages.link.InfoPageLinkHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Configuration
public class AdditionalRestConfiguration {
	@Autowired(required = false)
	private InfoPageLinkHelper infoPageLinkHelper = null;

	@PostConstruct
	private void postConstruct() {
		Assert.notNull(this.infoPageLinkHelper, "infoPageLinkHelper not found [null]");
	}

	@Bean
	public ResourceProcessor<Resource<? extends Persistable<?>>> getPersistableProcessor() {
		final InfoPageResourceProcessor processor = new InfoPageResourceProcessor();
		processor.setInfoPageLinkHelper(this.infoPageLinkHelper);
		return processor;
	}

}
