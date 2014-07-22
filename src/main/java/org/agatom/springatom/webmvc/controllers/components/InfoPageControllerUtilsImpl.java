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

package org.agatom.springatom.webmvc.controllers.components;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.agatom.springatom.core.exception.SException;
import org.agatom.springatom.web.component.core.request.ComponentRequestAttribute;
import org.agatom.springatom.web.component.infopages.elements.InfoPageAttributeComponent;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.elements.InfoPagePanelComponent;
import org.agatom.springatom.web.component.infopages.link.InfoPageLinkHelper;
import org.agatom.springatom.web.component.infopages.link.InfoPageRequest;
import org.agatom.springatom.web.component.infopages.provider.builder.InfoPageComponentBuilderService;
import org.agatom.springatom.web.component.infopages.request.InfoPageComponentRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.07.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component("ipCtrlUtils")
@Role(BeanDefinition.ROLE_SUPPORT)
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Description("ipCtrlUtils combines several useful method that eases with InfoPage component operations")
class InfoPageControllerUtilsImpl
		implements InfoPageControllerUtils {
	private static final Logger                                                           LOGGER             = Logger.getLogger(InfoPageControllerUtilsImpl.class);
	private static final Function<HttpServletRequest, String>                             GET_PATH           = new Function<HttpServletRequest, String>() {
		@Nullable
		@Override
		public String apply(final HttpServletRequest input) {
			final String uri = input.getRequestURI();
			if (uri.contains("/generic/")) {
				return uri.replace("/generic", "");
			}
			if (uri.contains("/config/")) {
				return uri.replace("/config", "");
			}
			return uri;
		}
	};
	private static final Function<InfoPagePanelComponent, Set<ComponentRequestAttribute>> GIPCR_FUNC         = new Function<InfoPagePanelComponent, Set<ComponentRequestAttribute>>() {
		@Nullable
		@Override
		public Set<ComponentRequestAttribute> apply(@Nullable final InfoPagePanelComponent input) {
			final Set<ComponentRequestAttribute> attributes = Sets.newHashSet();
			if (input == null) {
				return attributes;
			}
			for (final InfoPageAttributeComponent attr : input) {
				attributes.add(new ComponentRequestAttribute().setPath(attr.getPath()).setType(attr.getDisplayAs().name()));
			}
			return attributes;
		}
	};
	@Autowired
	private              InfoPageLinkHelper                                               infoPageLinkHelper = null;
	@Autowired
	private              InfoPageComponentBuilderService                                  builderService     = null;

	@Override
	public InfoPageRequest getInfoPageRequest(final HttpServletRequest request) throws Exception {
		LOGGER.debug(String.format("getInfoPageRequest(request=%s)", request));
		try {
			Assert.notNull(request, "Request was null");
			return this.infoPageLinkHelper.toInfoPageRequest(request, GET_PATH);
		} catch (Exception exp) {
			LOGGER.error(String.format("getInfoPageRequest failed with %s", ClassUtils.getShortName(exp.getClass())), exp);
			throw exp;
		}
	}

	@Override
	public InfoPageComponent getInfoPageComponent(final Class<Persistable<?>> objectClass) throws SException {
		LOGGER.debug(String.format("getInfoPageComponent(objectClass=%s)", objectClass));
		return this.builderService.buildInfoPage(objectClass);
	}

	@Override
	public InfoPageComponent getInfoPageComponent(final InfoPageRequest infoPageRequest) throws SException {
		LOGGER.debug(String.format("getInfoPageComponent(infoPageRequest=%s)", infoPageRequest));
		return this.getInfoPageComponent(infoPageRequest.getObjectClass());
	}

	@Override
	public InfoPageComponentRequest getInfoPageComponentRequest(final InfoPageComponent infoPageComponent, final InfoPageRequest pageRequest) {
		LOGGER.debug(String.format("getInfoPageComponentRequest(infoPageComponent=%s,pageRequest=%s)", infoPageComponent, pageRequest));
		final ImmutableSet<ComponentRequestAttribute> attributes = FluentIterable
				.from(infoPageComponent.getContent())
				.transformAndConcat(GIPCR_FUNC)
				.toSet();
		return (InfoPageComponentRequest) new InfoPageComponentRequest()
				.setPageId(infoPageComponent.getId())
				.setDomain(infoPageComponent.getDomain())
				.setId(pageRequest.getObjectId())
				.setVersion(pageRequest.getObjectVersion())
				.setRevision(pageRequest.getObjectVersion())
				.setAttributes(attributes
				);
	}
}
