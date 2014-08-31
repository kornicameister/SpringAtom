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
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.OID;
import org.agatom.springatom.web.wizards.data.context.WizardDataScopeHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.binding.message.Message;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;

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
    private static final long                  serialVersionUID   = 7984205043436916120L;
    private final        long                  timestamp          = System.currentTimeMillis();
    private              String                wizardId           = null;
    private              String                stepId             = null;
    private              Set<FeedbackMessage>  feedbackMessages   = null;
    private              Set<Throwable>        errors             = null;
    private              OID                   oid                = null;
    private              WizardDataScopeHolder data               = null;
    private              ModelMap              debugData          = null;
    private              Set<Message>          validationMessages = null;
    private              Set<ObjectError>      bindingErrors      = null;

    public OID getOid() {
        return oid;
    }

    public WizardResult setOid(final OID oid) {
        if (this.oid != null) {
            return this;
        }
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
        if (StringUtils.hasText(this.wizardId)) {
            return this;
        }
        this.wizardId = wizardId;
        return this;
    }

    public String getStepId() {
        return stepId;
    }

    public WizardResult setStepId(final String stepId) {
        if (StringUtils.hasText(this.stepId)) {
            return this;
        }
        this.stepId = stepId;
        return this;
    }

    public WizardResult addWizardData(final java.lang.Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addWizardData(data);
        return this;
    }

    public WizardResult addFormData(final java.lang.Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addFormData(data);
        return this;
    }

    public WizardResult addStepData(final java.lang.Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addStepData(data);
        return this;
    }

    public WizardResult addStepData(final String key, final java.lang.Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addStepData(key, data);
        return this;
    }

    public WizardResult addFormData(final String key, final java.lang.Object data) {
        if (this.data == null) {
            this.data = new WizardDataScopeHolder();
        }
        this.data.addFormData(key, data);
        return this;
    }

    public WizardResult addWizardData(final String key, final java.lang.Object data) {
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
        if (this.feedbackMessages == null) {
            this.feedbackMessages = Sets.newLinkedHashSet();
        }
        this.feedbackMessages.add(message);
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

    public WizardResult addBindingError(final ObjectError error) {
        if (error == null) {
            return this;
        }
        if (this.bindingErrors == null) {
            this.bindingErrors = Sets.newLinkedHashSet();
        }
        this.bindingErrors.add(error);
        return this;
    }

    public Set<ObjectError> getBindingErrors() {
        return bindingErrors;
    }

    public WizardResult setBindingErrors(final Set<ObjectError> bindingErrors) {
        if (CollectionUtils.isEmpty(bindingErrors)) {
            return this;
        }
        if (this.bindingErrors == null) {
            this.bindingErrors = bindingErrors;
        } else {
            this.bindingErrors.addAll(bindingErrors);
        }
        return this;
    }

    public Set<Message> getValidationMessages() {
        return validationMessages;
    }

    public WizardResult setValidationMessages(final Set<Message> validationMessages) {
        if (CollectionUtils.isEmpty(validationMessages)) {
            return this;
        }
        if (this.validationMessages == null) {
            this.validationMessages = validationMessages;
            return this;
        } else {
            this.validationMessages.addAll(validationMessages);
        }
        return this;
    }

    public Set<FeedbackMessage> getFeedbackMessages() {
        return feedbackMessages;
    }

    public WizardResult setFeedbackMessages(final Set<FeedbackMessage> feedbackMessages) {
        if (CollectionUtils.isEmpty(feedbackMessages)) {
            return this;
        }
        if (this.feedbackMessages == null) {
            this.feedbackMessages = feedbackMessages;
        } else {
            this.feedbackMessages.addAll(feedbackMessages);
        }
        return this;
    }

    public Set<Throwable> getErrors() {
        return errors;
    }

    public WizardResult setErrors(final Set<Throwable> errors) {
        if (CollectionUtils.isEmpty(errors)) {
            return this;
        }
        if (this.errors == null) {
            this.errors = errors;
        } else {
            this.errors.addAll(errors);
        }
        return this;
    }

    public boolean isSuccess() {
        return !this.hasErrors();
    }

    public boolean hasErrors() {
        return !CollectionUtils.isEmpty(this.errors) || !CollectionUtils.isEmpty(this.bindingErrors) || !CollectionUtils.isEmpty(this.validationMessages);
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

    public Map<String, java.lang.Object> getDataMap() {
        if (this.data == null) {
            return null;
        }
        return this.data.asMap();
    }

    public WizardResult merge(final WizardResult localResult) {
        BeanUtils.copyProperties(localResult, this);
        return this;
    }

    public WizardResult addDebugData(final String attr, final java.lang.Object value) {
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
        if (CollectionUtils.isEmpty(debugData)) {
            return this;
        }
        if (this.debugData == null) {
            this.debugData = debugData;
        } else {
            this.debugData.putAll(debugData);
        }
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(timestamp, wizardId, stepId, feedbackMessages, errors, oid,
                data, debugData, validationMessages, bindingErrors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WizardResult that = (WizardResult) o;

        return Objects.equal(this.timestamp, that.timestamp) &&
                Objects.equal(this.wizardId, that.wizardId) &&
                Objects.equal(this.stepId, that.stepId) &&
                Objects.equal(this.feedbackMessages, that.feedbackMessages) &&
                Objects.equal(this.errors, that.errors) &&
                Objects.equal(this.oid, that.oid) &&
                Objects.equal(this.data, that.data) &&
                Objects.equal(this.debugData, that.debugData) &&
                Objects.equal(this.validationMessages, that.validationMessages) &&
                Objects.equal(this.bindingErrors, that.bindingErrors);
    }
}
