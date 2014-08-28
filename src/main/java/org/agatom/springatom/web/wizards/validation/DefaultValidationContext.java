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

package org.agatom.springatom.web.wizards.validation;

import com.google.common.base.Objects;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.ui.ModelMap;

import java.security.Principal;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class DefaultValidationContext
        implements ValidationContext {
    private String         stepId         = null;
    private ModelMap       bindingModel   = null;
    private MessageContext messageContext = null;
    private Principal      principal      = null;
    private ModelMap       formData       = null;

    public MessageContext getMessageContext() {
        return this.messageContext;
    }

    public DefaultValidationContext setMessageContext(final MessageContext messageContext) {
        this.messageContext = messageContext;
        return this;
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getUserEvent() {
        return this.getStepId();
    }

    public Object getUserValue(String property) {
        return this.formData != null ? this.formData.get(property) : null;
    }

    public String getStepId() {
        return stepId;
    }

    public DefaultValidationContext setStepId(final String stepId) {
        this.stepId = stepId;
        return this;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public DefaultValidationContext setPrincipal(final Principal principal) {
        this.principal = principal;
        return this;
    }

    public ModelMap getBindingModel() {
        return bindingModel;
    }

    public DefaultValidationContext setBindingModel(final ModelMap bindingModel) {
        this.bindingModel = bindingModel;
        return this;
    }

    public ModelMap getFormData() {
        return this.formData;
    }

    public DefaultValidationContext setFormData(final ModelMap formData) {
        this.formData = formData;
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("stepId", stepId)
                .add("bindingModel", bindingModel)
                .add("messageContext", messageContext)
                .add("principal", principal)
                .add("formData", formData)
                .toString();
    }
}

