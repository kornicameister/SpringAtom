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

package org.agatom.springatom.cmp.wizards.validation.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.agatom.springatom.cmp.wizards.data.result.WizardResult;

import java.util.Map;

/**
 * {@code ValidationBean} describes single validation call.
 * Fields are used what type of validator should be used.
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-27</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public final class ValidationBean {
    private String              commandBeanName = null;
    private String              stepId          = null;
    private Object              commandBean     = null;
    private WizardResult        partialResult   = null;
    private Map<String, Object> formData        = null;
    private Map<String, Object> bindingModel    = null;

    public String getCommandBeanName() {
        return commandBeanName;
    }

    public ValidationBean setCommandBeanName(final String commandBeanName) {
        this.commandBeanName = commandBeanName;
        return this;
    }

    public String getStepId() {
        return stepId;
    }

    public ValidationBean setStepId(final String stepId) {
        this.stepId = stepId;
        return this;
    }

    public Object getCommandBean() {
        return commandBean;
    }

    public ValidationBean setCommandBean(final Object commandBean) {
        this.commandBean = commandBean;
        return this;
    }

    public WizardResult getPartialResult() {
        return partialResult;
    }

    public ValidationBean setPartialResult(final WizardResult partialResult) {
        this.partialResult = partialResult;
        return this;
    }

    public Map<String, Object> getFormData() {
        return this.formData;
    }

    public ValidationBean setFormData(final Map<String, Object> formData) {
        this.formData = formData;
        return this;
    }

    public Map<String, Object> getBindingModel() {
        return this.bindingModel;
    }

    public ValidationBean setBindingModel(final Map<String, Object> bindingModel) {
        this.bindingModel = bindingModel;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(commandBeanName, stepId, commandBean, partialResult, formData, bindingModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationBean that = (ValidationBean) o;

        return Objects.equal(this.commandBeanName, that.commandBeanName) &&
                Objects.equal(this.stepId, that.stepId) &&
                Objects.equal(this.commandBean, that.commandBean) &&
                Objects.equal(this.partialResult, that.partialResult) &&
                Objects.equal(this.formData, that.formData) &&
                Objects.equal(this.bindingModel, that.bindingModel);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("commandBeanName", commandBeanName)
                .add("stepId", stepId)
                .add("commandBean", commandBean)
                .add("partialResult", partialResult)
                .add("formData", formData)
                .add("bindingModel", bindingModel)
                .toString();
    }
}
