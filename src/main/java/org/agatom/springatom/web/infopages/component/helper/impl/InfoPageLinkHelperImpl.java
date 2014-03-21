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

package org.agatom.springatom.web.infopages.component.helper.impl;

import org.agatom.springatom.web.infopages.SInfoPage;
import org.agatom.springatom.web.infopages.component.helper.InfoPageLinkHelper;
import org.agatom.springatom.webmvc.controllers.SVInfoPageController;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageLinkHelperImpl implements InfoPageLinkHelper {
	private static final Logger LOGGER = Logger.getLogger(InfoPageLinkHelperImpl.class);

	@Override
	public <T extends Serializable> Link getInfoPageLink(final SInfoPage page, final Persistable<T> persistable) {
		final String path = page.getPath();
		final T id = persistable.getId();
		try {
			return this.getInfoPageLink(path, id);
		} catch (Throwable e) {
			LOGGER.error(String.format("Failed to generate link for InfoPage=%s, returning null", page), e);
			return null;
		}
	}

	@Override
	public <T extends Serializable> Link getInfoPageLink(final String path, final T id) {

		Assert.notNull(path, "Path can not be null");
		Assert.isTrue(!path.isEmpty(), "Path can not be empty");
		Assert.notNull(id, "Id can not be null");

		LOGGER.trace(String.format("Generating link for path=%s and id=%s", path, ObjectUtils.getDisplayString(id)));

		Link infoPage = linkTo(SVInfoPageController.class).slash(path).slash(id).withRel("infoPage");

		final String href = infoPage.getHref();
		if (href.contains("/rest/")) {
			infoPage = new Link(href.replace("/rest/", "/app/"), infoPage.getRel());
			infoPage.expand(new TemplateVariable("origin", TemplateVariable.VariableType.FRAGMENT, "yes"));
		}

		LOGGER.trace(String.format("Resulting link is => %s", infoPage));

		return infoPage;
	}
}
