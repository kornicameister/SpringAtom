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

package org.agatom.springatom.web.flows.wizards.wizard.newUser;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.beans.user.authority.SAuthority;
import org.agatom.springatom.server.model.types.user.SRole;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.flows.wizards.wizard.WizardFormAction;
import org.agatom.springatom.web.locale.SMessageSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.converters.StringToEnum;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/**
 * {@code NewUserWizardStep2} is responsible for preparing, converting and binding
 * the set of {@link org.agatom.springatom.server.model.beans.user.authority.SAuthority} for
 * given {@link org.agatom.springatom.server.model.beans.user.SUser}
 * [{@link org.agatom.springatom.web.flows.wizards.wizard.newUser.NewUserWizardStep2#getCommandBean(org.springframework.webflow.execution.RequestContext)}].
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WizardAction("newUserWizardStep2")
public class NewUserWizardStep2
		extends WizardFormAction<SUser> {
	public static final  String          AUTHORITIES_REQUIRED_FIELD        = "authorities";
	private static final Logger          LOGGER                            = Logger.getLogger(NewUserWizardStep2.class);
	private static final String          FORM_OBJECT_NAME                  = "user";
	private static final String          LOCALIZED_ROLES_KEY               = "localizedRoles";
	private              Converter<?, ?> STRING_TO_GRANTED_AUTHORITY       = new StringToGrantedAuthorityConverter();
	private              Converter<?, ?> STRING_ARR_TO_GRANTED_AUTHORITIES = new ArrayToGrantedAuthoritiesConverter();
	private              Set<SRole>      excludedRoles                     = Sets.newHashSet();
	@Autowired
	private              SMessageSource  messageSource                     = null;

	public NewUserWizardStep2() {
		super();
		this.setFormObjectName(FORM_OBJECT_NAME);
	}

	@Override
	protected void initAction() {
		super.initAction();
		this.getRolesToExcludeInWizard();
	}

	/**
	 * Reads the set of {@link org.agatom.springatom.server.model.types.user.SRole} to be excluded in
	 * wizard from being assigned to new {@link org.agatom.springatom.server.model.beans.user.SUser}
	 */
	private void getRolesToExcludeInWizard() {
		try {
			final File file = ResourceUtils.getFile("classpath:org/agatom/springatom/web/flows/wizards/wizard/newUser/excluded-roles.properties");
			if (file.canRead()) {
				final Properties properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(file));
				final String property = properties.getProperty("springatom.excludedRolesInCreate", "");
				if (StringUtils.hasText(property)) {
					final StringToEnum stringToEnum = new StringToEnum();
					final String[] split = StringUtils.split(property, ",");
					for (String propRaw : split) {
						final SRole role = (SRole) stringToEnum.convertSourceToTargetClass(propRaw, SRole.class);
						this.excludedRoles.add(role);
					}
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("Failed to access excluded roles properties", e);
		} catch (IOException e) {
			LOGGER.error("Failed to read properties", e);
		} catch (Exception e) {
			LOGGER.error("General failure in reading excluded properties", e);
		}
		if (!this.excludedRoles.isEmpty()) {
			LOGGER.trace(String.format("Read %d roles to be excluded = %s", this.excludedRoles.size(), this.excludedRoles));
		}
	}

	@Override
	public Event setupForm(final RequestContext context) throws Exception {
		LOGGER.trace(String.format("setupForm(context=%s)", context));
		final Locale locale = LocaleContextHolder.getLocale();

		final SRole[] sRoles = SRole.values();
		final List<LocalizedRole> localizedRoles = Lists.newArrayListWithExpectedSize(sRoles.length);
		for (SRole sRole : sRoles) {
			if (this.excludedRoles.contains(sRole)) {
				LOGGER.trace(String.format("%s suppressed, it was found in excluded roles", sRole));
				continue;
			}
			localizedRoles.add(new LocalizedRole().setLabel(this.messageSource.getMessage(sRole.name(), locale)).setRole(sRole));
		}

		final MutableAttributeMap<Object> viewScope = context.getViewScope();
		viewScope.put(LOCALIZED_ROLES_KEY, localizedRoles);
		return super.setupForm(context);
	}

	@Override
	public Event resetForm(final RequestContext context) throws Exception {
		this.getCommandBean(context).clearAuthorities();
		return super.resetForm(context);
	}

	@Override
	protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
		conversionService.addConverter(STRING_ARR_TO_GRANTED_AUTHORITIES);
		conversionService.addConverter(STRING_TO_GRANTED_AUTHORITY);
		binder.setRequiredFields(AUTHORITIES_REQUIRED_FIELD);
		return super.doInitBinder(binder, conversionService);
	}

	/**
	 * Converter responsible for generating the set of {@link org.agatom.springatom.server.model.beans.user.authority.SAuthority}
	 * from passed {@link java.util.Set} of {@link java.lang.String} where each one is mapped to {@link org.agatom.springatom.server.model.types.user.SRole}
	 * with the help of {@link org.springframework.binding.convert.converters.StringToEnum}
	 */
	private abstract class BaseConverter
			extends MatcherConverter {
		protected Set<GrantedAuthority> doConvert(final Set<String> list) {
			LOGGER.trace(String.format("converting with selected clazz=%s", list));
			Preconditions.checkNotNull(list);
			Preconditions.checkArgument(!list.isEmpty());
			final Set<GrantedAuthority> authorities = Sets.newHashSet();
			final StringToEnum stringToEnum = new StringToEnum();
			for (final String role : list) {
				try {
					final SRole sRole = (SRole) stringToEnum.convertSourceToTargetClass(role, SRole.class);
					final SAuthority sAuthority = new SAuthority();
					sAuthority.setRole(sRole);
					authorities.add(sAuthority);
					LOGGER.trace(String.format("Resolved authority from %s to %s", sRole, sAuthority));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return authorities;
		}
	}

	/**
	 * {@code LocalizedRole} holds a pair of {@link org.agatom.springatom.server.model.types.user.SRole}
	 * and its localized value under {@link org.agatom.springatom.web.flows.wizards.wizard.newUser.NewUserWizardStep2.LocalizedRole#label}
	 * value
	 */
	private class LocalizedRole
			implements Serializable {
		private static final long   serialVersionUID = -6064570964928557059L;
		private              String label            = null;
		private              SRole  role             = null;

		public String getLabel() {
			return label;
		}

		public LocalizedRole setLabel(final String label) {
			this.label = label;
			return this;
		}

		public SRole getRole() {
			return role;
		}

		public LocalizedRole setRole(final SRole role) {
			this.role = role;
			return this;
		}
	}

	private class ArrayToGrantedAuthoritiesConverter
			extends BaseConverter
			implements Converter<String[], Set<GrantedAuthority>> {

		@Override
		public Set<GrantedAuthority> convert(final String[] attributes) {
			return this.doConvert(Sets.newHashSet(attributes));
		}

		@Override
		public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
			final Class<?> sourceTypeClass = sourceType.getType();
			return ClassUtils.isAssignable(String[].class, sourceTypeClass)
					&& ClassUtils.isAssignable(Set.class, targetType.getType());
		}
	}

	private class StringToGrantedAuthorityConverter
			extends BaseConverter
			implements Converter<String, Set<GrantedAuthority>> {

		@Override
		public Set<GrantedAuthority> convert(final String clazz) {
			return this.doConvert(Sets.newHashSet(clazz));
		}

		@Override
		public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
			final Class<?> sourceTypeClass = sourceType.getType();
			return ClassUtils.isAssignable(String.class, sourceTypeClass)
					&& ClassUtils.isAssignable(Set.class, targetType.getType());
		}
	}
}
