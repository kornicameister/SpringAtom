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

package org.agatom.springatom.web.component.tag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.agatom.springatom.web.component.core.builders.Builder;
import org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.web.component.core.builders.ComponentProduces;
import org.agatom.springatom.web.component.core.context.ComponentContext;
import org.agatom.springatom.web.component.core.repository.ComponentBuilderRepository;
import org.agatom.springatom.web.component.infopages.builder.InfoPageComponentBuilder;
import org.agatom.springatom.web.component.table.TableComponentBuilder;
import org.agatom.springatom.webmvc.exceptions.ControllerTierException;
import org.apache.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.jsp.JspWriter;
import java.io.Serializable;
import java.util.List;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 23.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentConfigurationTag
		extends RequestContextAwareTag {
	private static final long              serialVersionUID  = -1423230482877816008L;
	private static final Logger            LOGGER            = Logger.getLogger(ComponentConfigurationTag.class);
	private              String            componentId       = null;
	private              Class<?>          contextClazz      = null;
	private              ComponentProduces componentProduces = null;
	private              String            var               = null;

	public String getVar() {
		return var;
	}

	public void setVar(final String var) {
		this.var = var;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(final String componentId) {
		this.componentId = componentId;
	}

	public Class<?> getContextClazz() {
		return contextClazz;
	}

	public void setContextClazz(final Class<?> contextClazz) {
		this.contextClazz = contextClazz;
	}

	public ComponentProduces getComponentProduces() {
		return componentProduces;
	}

	public void setComponentProduces(final ComponentProduces componentProduces) {
		this.componentProduces = componentProduces;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		try {
			final ComponentBuilderRepository repository = this.getComponentBuilderRepository();
			final String builderId = this.getBuilderId(repository);
			final Builder builder = this.getBuilder(repository, builderId);

			if (builder != null) {

				Assert.isInstanceOf(ComponentDefinitionBuilder.class, builder, "Not ComponentDefinitionBuilder builder");
				final StandaloneComponentConfiguration data = this.getComponentConfiguration(builderId, builder);

				if (this.var != null) {
					this.pageContext.setAttribute(this.var, data);
				} else {
					final JspWriter out = this.pageContext.getOut();
					out.write(this.writeAsString(data));
				}
			}

		} catch (Exception exp) {
			LOGGER.error("Failed to get component configuration", exp);
			throw exp;
		}
		return EVAL_BODY_INCLUDE;
	}

	private String writeAsString(final Object data) throws JsonProcessingException {
		final ObjectMapper objectMapper = this.getObjectMapper();
		return objectMapper != null ? objectMapper.writeValueAsString(data) : null;
	}

	private ObjectMapper getObjectMapper() {
		final WebApplicationContext context = this.getRequestContext().getWebApplicationContext();
		return context != null ? (ObjectMapper) context.getBean("jackson2ObjectFactoryBean") : null;
	}

	private Builder getBuilder(final ComponentBuilderRepository repository, final String builderId) {
		Builder builder = null;
		if (StringUtils.hasText(builderId)) {
			builder = repository.getBuilder(builderId);
		}
		return builder;
	}

	private String getBuilderId(final ComponentBuilderRepository repository) {
		final String builderId;
		if (this.contextClazz != null && !StringUtils.hasText(this.componentId)) {
			if (this.componentProduces == null) {
				builderId = repository.getBuilderId(this.contextClazz);
			} else {
				builderId = repository.getBuilderId(this.contextClazz, this.componentProduces);
			}
		} else {
			builderId = this.componentId;
		}
		return builderId;
	}

	private ComponentBuilderRepository getComponentBuilderRepository() {
		final WebApplicationContext context = this.getRequestContext().getWebApplicationContext();
		return context != null ? context.getBean(ComponentBuilderRepository.class) : null;
	}

	private StandaloneComponentConfiguration getComponentConfiguration(final String builderId, final Builder builder) throws ControllerTierException {
		LOGGER.trace(String.format("onConfigurationRequest(builderId=%s)", builderId));
		try {
			Assert.hasText(builderId, "BuilderId not found in request");
			final List<Link> links = Lists.newArrayListWithCapacity(2);

			if (ClassUtils.isAssignableValue(InfoPageComponentBuilder.class, builder)) {
				links.add(new Link("/app/cmp/data/ip/", "data"));
			} else if (ClassUtils.isAssignableValue(TableComponentBuilder.class, builder)) {
				links.add(new Link("/app/cmp/data/table/" + builderId, "data"));
				links.add(new Link("/app/cmp/config/table/" + builderId, "configuration"));
			}

			return new StandaloneComponentConfiguration()
					.setUrls(links)
					.setBuilderId(builderId)
					.setValue(null);

		} catch (Exception exp) {
			LOGGER.trace(String.format("onConfigurationRequest(builderId=%s) failed...", builderId), exp);
			throw new ControllerTierException(String.format("Failed to get configuration URLs for component=%s", builderId), exp);
		}
	}

	private class StandaloneComponentConfiguration
			implements Serializable {
		private static final long             serialVersionUID = 8699538335615005572L;
		private              String           builderId        = null;
		private              List<Link>       urls             = null;
		private              ComponentContext value            = null;

		public String getBuilderId() {
			return builderId;
		}

		public StandaloneComponentConfiguration setBuilderId(final String builderId) {
			this.builderId = builderId;
			return this;
		}

		public List<Link> getUrls() {
			return urls;
		}

		public StandaloneComponentConfiguration setUrls(final List<Link> urls) {
			this.urls = urls;
			return this;
		}

		public ComponentContext getValue() {
			return value;
		}

		public StandaloneComponentConfiguration setValue(final ComponentContext value) {
			this.value = value;
			return this;
		}
	}

}
