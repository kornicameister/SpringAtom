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

package org.agatom.springatom.rest.processors;


import org.agatom.springatom.web.infopages.SEntityInfoPage;
import org.agatom.springatom.web.infopages.link.InfoPageLinkHelper;
import org.agatom.springatom.web.infopages.mapping.InfoPageMappings;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageResourceProcessor implements ResourceProcessor<Resource<? extends Persistable<?>>> {
	private static final Logger           LOGGER           = Logger.getLogger(InfoPageResourceProcessor.class);
	private              InfoPageMappings infoPageMappings = null;
	private InfoPageLinkHelper infoPageLinkHelper;

	@Override
	public Resource<? extends Persistable<?>> process(final Resource<? extends Persistable<?>> resource) {
		if (resource == null) {
			LOGGER.warn("Empty resource detected, returning without affecting");
			return null;
		}

		final Persistable<?> content = resource.getContent();
		if (content == null) {
			LOGGER.warn("Resource not null but content is, returning without affecting");
			return null;
		}

		final Class<? extends Persistable> contentClass = content.getClass();
		final SEntityInfoPage infoPageForEntity = this.infoPageMappings.getInfoPageForEntity(ClassUtils.getUserClass(contentClass));
		if (infoPageForEntity != null) {
			final Link infoPageLink = this.infoPageLinkHelper.getInfoPageLink(infoPageForEntity, content);
			if (infoPageLink != null) {
				resource.add(infoPageLink);
			} else {
				LOGGER.warn(String.format("%s has infoPage but link was not created", ClassUtils.getShortName(contentClass)));
			}
		} else {
			LOGGER.trace(String.format("%s has no infoPage", ClassUtils.getShortName(contentClass)));
		}
		return resource;
	}

	public void setInfoPageMappings(final InfoPageMappings infoPageMappings) {
		Assert.notNull(infoPageMappings, "infoPageMappings can not be null");
		this.infoPageMappings = infoPageMappings;
	}

	public void setInfoPageLinkHelper(final InfoPageLinkHelper infoPageLinkHelper) {
		Assert.notNull(infoPageLinkHelper, "infoPageLinkHelper can not be null");
		this.infoPageLinkHelper = infoPageLinkHelper;
	}
}
