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

package org.agatom.springatom.web.component.infopages.link;

import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.infopages.InfoPageNotFoundException;
import org.agatom.springatom.web.component.infopages.mapping.InfoPageMappingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Persistable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 20.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
@Component
@Role(BeanDefinition.ROLE_SUPPORT)
@Scope(BeanDefinition.SCOPE_SINGLETON)
class InfoPageLinkHelperImpl
		implements InfoPageLinkHelper {
	private static final Logger                 LOGGER              = Logger.getLogger(InfoPageLinkHelperImpl.class);
	private              UriTemplate            infoPageUriTemplate = null;
	@Autowired
	private              InfoPageMappingService mappingService      = null;

	@PostConstruct
	private UriTemplate getInfoPageUriTemplate() {
		this.infoPageUriTemplate = new UriTemplate(
				String.format("{%s}/cmp/ip/{%s}/{%s}{%s}",
						PathElement.CONTEXT.getInternalKey(),
						PathElement.OBJECT_PATH.getInternalKey(),
						PathElement.OBJECT_ID.getInternalKey(),
						PathElement.OBJECT_VERSION.getInternalKey()
				)
		);
		return this.infoPageUriTemplate;
	}

	@Override
	public <T extends Serializable> Link getInfoPageLink(final Persistable<T> persistable) throws InfoPageNotFoundException {
		LOGGER.trace(String.format("getInfoPageLink(persistable=%s)", persistable));

		Assert.isTrue(!persistable.isNew(), "Persistable must be persisted to retrieve InfoPage");

		final String rel = this.mappingService.getMappedRel(persistable.getClass());
		final T id = persistable.getId();

		try {
			return this.getInfoPageLink(rel, id);
		} catch (Throwable e) {
			LOGGER.error(String.format("Failed to generate link for InfoPage=%s, returning null", persistable), e);
			return null;
		}
	}

	@Override
	public <T extends Serializable> Link getInfoPageLink(final String path, final T id) {
		LOGGER.trace(String.format("getInfoPageLink(path=%s,id=%s)", path, id));

		Assert.notNull(path, "Path can not be null");
		Assert.isTrue(!path.isEmpty(), "Path can not be empty");
		Assert.notNull(id, "Id can not be null");

		final UriTemplate localTemplate = new UriTemplate(String.format("/app/cmp/ip/{%s}/{%s}",
				PathElement.OBJECT_PATH.getInternalKey(),
				PathElement.OBJECT_ID.getInternalKey()
		));
		final Map<String, Object> expandVars = Maps.newHashMap();
		expandVars.put(PathElement.OBJECT_ID.getInternalKey(), id.toString());
		expandVars.put(PathElement.OBJECT_PATH.getInternalKey(), path);

		Link infoPage = new Link(localTemplate.expand(expandVars).toString());

		LOGGER.trace(String.format("Resulting link is => %s", infoPage));

		return infoPage;
	}

	@Override
	public boolean isInfoPageLink(final String path) {
		return !StringUtils.hasText(path) || this.infoPageUriTemplate.matches(path);
	}

	@Override
	public InfoPageRequest toInfoPageRequest(final HttpServletRequest request) throws InfoPageNotFoundException {
		final String path = request.getRequestURI();

		if (!StringUtils.hasText(path)) {
			return null;
		}

		final InfoPageRequest link = new InfoPageRequest(request);
		final Map<String, String> match = this.infoPageUriTemplate.match(path);

		for (final String key : match.keySet()) {

			final PathElement element = PathElement.fromInternalKey(key);
			final String value = match.get(key);

			switch (element) {
				case CONTEXT:
					link.setContext(value);
					break;
				case OBJECT_PATH:
					link.setObjectClass(this.mappingService.getMappedClass(value));
					break;
				case OBJECT_ID:
					link.setObjectId(Long.valueOf(value));
					break;
				case OBJECT_VERSION:
					if (StringUtils.hasLength(value)) {
						link.setObjectVersion(Long.valueOf(value));
					}
					break;
			}
		}

		return link;
	}

	private static enum PathElement {
		CONTEXT("?.app*"),
		OBJECT_PATH("path"),
		OBJECT_VERSION("?.objectVersion*"),
		OBJECT_ID("objectId");

		private final String internalKey;

		PathElement(final String internalKey) {
			this.internalKey = internalKey;
		}

		public static PathElement fromInternalKey(final String key) {
			for (PathElement pathElement : PathElement.values()) {
				if (pathElement.getInternalKey().equalsIgnoreCase(key)) {
					return pathElement;
				}
			}
			return null;
		}

		public String getInternalKey() {
			return this.internalKey;
		}
	}

}
