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

package org.agatom.springatom.web.wizards.data.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.OID;
import org.agatom.springatom.web.wizards.data.context.WizardDataScopeHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * {@code WizardResult} is a wrapper to transport result of a method called from {@link org.agatom.springatom.web.wizards.WizardProcessor}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-22</small>
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
public class WizardResult
        implements Serializable {
    private static final long                  serialVersionUID = 7984205043436916120L;
    private final        long                  timestamp        = System.currentTimeMillis();
    private              String                wizardId         = null;
    private              String                stepId           = null;
    private              Set<FeedbackMessage>  messages         = null;
    private              Set<Throwable>        errors           = null;
    private              Set<Errors>           validationErrors = null;
    private              OID                   oid              = null;
    private              WizardDataScopeHolder data             = null;
    private              ModelMap              debugData        = null;

    public OID getOid() {
        return oid;
    }

    public WizardResult setOid(final OID oid) {
        this.oid = oid;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getWizardId() {
        return wizardId;
    }

    public WizardResult setWizardId(final String wizardId) {
        this.wizardId = wizardId;
        return this;
    }

    public String getStepId() {
        return stepId;
    }

    public WizardResult setStepId(final String stepId) {
        this.stepId = stepId;
        return this;
    }

    public WizardResult addWizardData(final Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addWizardData(data);
        return this;
    }

    public WizardResult addFormData(final Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addFormData(data);
        return this;
    }

    public WizardResult addFormDataData(final Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addFormDataData(data);
        return this;
    }

    public WizardResult addStepData(final Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addStepData(data);
        return this;
    }

    public WizardResult addStepData(final String key, final Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addStepData(key, data);
        return this;
    }

    public WizardResult addFormDataData(final String key, final Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addFormDataData(key, data);
        return this;
    }

    public WizardResult addFormData(final String key, final Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addFormData(key, data);
        return this;
    }

    public WizardResult addWizardData(final String key, final Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addWizardData(key, data);
        return this;
    }

    public WizardResult addFeedbackMessage(final FeedbackMessage message) {
        if (message == null) {
            return this;
        }
        if (this.messages == null) {
            this.messages = Sets.newLinkedHashSet();
        }
        this.messages.add(message);
        return this;
    }

    public WizardResult addError(final Throwable error) {
        if (error == null) {
            return this;
        }
        if (this.errors == null) {
            this.errors = Sets.newLinkedHashSet();
        }
        this.errors.add(error);
        return this;
    }

    public WizardResult addValidationError(final Errors error) {
        if (error == null) {
            return this;
        }
        if (this.validationErrors == null) {
            this.validationErrors = Sets.newLinkedHashSet();
        }
        this.validationErrors.add(error);
        return this;
    }

    public Set<Errors> getValidationErrors() {
        return validationErrors;
    }

    public WizardResult setValidationErrors(final Set<Errors> validationErrors) {
        if (this.validationErrors == null) {
            this.validationErrors = validationErrors;
        } else {
            this.validationErrors.addAll(validationErrors);
        }
        return this;
    }

    public Set<FeedbackMessage> getMessages() {
        return messages;
    }

    public WizardResult setMessages(final Set<FeedbackMessage> messages) {
        if (this.messages == null) {
            this.messages = messages;
        } else {
            this.messages.addAll(messages);
        }
        return this;
    }

    public Set<Throwable> getErrors() {
        return errors;
    }

    public WizardResult setErrors(final Set<Throwable> errors) {
        if (this.errors == null) {
            this.errors = errors;
        } else {
            this.errors.addAll(errors);
        }
        return this;
    }

    public boolean isSuccess() {
        return CollectionUtils.isEmpty(this.errors) && CollectionUtils.isEmpty(this.validationErrors);
    }

    @JsonIgnore
    public WizardDataScopeHolder getData() {
        return data;
    }

    public WizardResult setData(final WizardDataScopeHolder data) {
        if (this.data == null) {
            this.data = data;
        } else {
            this.data.merge(data);
        }
        return this;
    }

    public Map<String, Object> getDataMap() {
        if (this.data == null) {
            return null;
        }
        return this.data.asMap();
    }

    public WizardResult merge(final WizardResult localResult) {
        BeanUtils.copyProperties(localResult, this);
        return this;
    }

    public WizardResult addDebugData(final String attr, final Object value) {
        if (this.debugData == null) {
            this.debugData = new ModelMap(attr, value);
        } else {
            this.debugData.addAttribute(attr, value);
        }
        return this;
    }

    public ModelMap getDebugData() {
        return this.debugData;
    }

    public WizardResult setDebugData(final ModelMap debugData) {
        this.debugData = debugData;
        return this;
    }
}
