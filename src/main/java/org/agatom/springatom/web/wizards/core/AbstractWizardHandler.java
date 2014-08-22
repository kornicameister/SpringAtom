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

package org.agatom.springatom.web.wizards.core;

import org.agatom.springatom.web.locale.SMessageSource;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.style.StylerUtils;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.Assert;
import org.springframework.validation.DataBinder;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;

import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-18</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class AbstractWizardHandler {
    private static final Logger                      LOGGER             = Logger.getLogger(AbstractWizardHandler.class);
    @Autowired
    @Qualifier(value = "springAtomConversionService")
    protected            FormattingConversionService conversionService  = null;
    @Autowired
    protected            LocalValidatorFactoryBean   delegatedValidator = null;
    @Autowired
    protected            SMessageSource              messageSource      = null;

    protected DataBinder bind(final DataBinder dataBinder, final Map<String, Object> params) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Executing bind");
        }
        this.doBind(dataBinder, params);
        return dataBinder;
    }

    private void doBind(final DataBinder binder, Map<String, Object> params) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Binding allowed request parameters in %s to form object with name '%s', pre-bind formObject toString = %s", params, binder.getObjectName(), binder.getTarget()));
            if (binder.getAllowedFields() != null && binder.getAllowedFields().length > 0) {
                LOGGER.debug(String.format("(Allowed fields are %s)", StylerUtils.style(binder.getAllowedFields())));
            } else {
                LOGGER.debug("(Any field is allowed)");
            }
        }
        binder.bind(new MutablePropertyValues(params));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Binding completed for form object with name '%s', post-bind formObject toString = %s", binder.getObjectName(), binder.getTarget()));
            LOGGER.debug(String.format("There are [%d] errors, details: %s", binder.getBindingResult().getErrorCount(), binder.getBindingResult().getAllErrors()));
        }
    }

    protected DataBinder createBinder(final Object contextObject, final String contextObjectName) {
        LOGGER.debug(String.format("createBinder(contextObject=%s,contextObjectName=%s)", contextObject, contextObjectName));

        Assert.notNull(contextObject, "contextObject must not be null");
        Assert.notNull(contextObjectName, "contextObjectName must not be null");

        final DataBinder binder = new WebDataBinder(contextObject, contextObjectName);

        binder.setIgnoreUnknownFields(true);
        binder.setAutoGrowNestedPaths(true);
        binder.setConversionService(this.conversionService);
        binder.setValidator(this.delegatedValidator);
        binder.setMessageCodesResolver(new DefaultMessageCodesResolver());

        return binder;
    }

}
