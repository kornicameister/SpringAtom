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

package org.agatom.springatom.web.wizards.data;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.EmbeddableComponent;
import org.agatom.springatom.web.component.core.elements.DefaultComponent;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * {@code WizardStepDescriptor} describes single step of a wizard. It is a part of {@link org.agatom.springatom.web.wizards.data.WizardDescriptor}
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.2
 * @see org.agatom.springatom.web.component.core.elements.DefaultComponent
 * @see org.agatom.springatom.web.component.core.EmbeddableComponent
 * @since 0.0.1
 */
public class WizardStepDescriptor
        extends DefaultComponent
        implements EmbeddableComponent {
    private static final long                serialVersionUID = 3388862863826004584L;
    protected            String              step             = null;
    protected short index = -1;
    protected            boolean             required         = false;
    protected            Map<String, String> labels           = Maps.newHashMap();

    public boolean isRequired() {
        return required;
    }

    public WizardStepDescriptor setRequired(final boolean required) {
        this.required = required;
        return this;
    }

    public String getStep() {
        return step;
    }

    public WizardStepDescriptor setStep(final String step) {
        this.step = step;
        return this;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public WizardStepDescriptor setLabels(final Map<String, String> labels) {
        this.labels = labels;
        return this;
    }

    public WizardStepDescriptor addLabel(final String key, final String value) {
        this.labels.put(key, value);
        return this;
    }

    @Override
    public int compareTo(@Nonnull final EmbeddableComponent ws) {
        return ComparisonChain.start().compare(this.index, ((WizardStepDescriptor) ws).index).result();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index, required, label, step);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WizardStepDescriptor that = (WizardStepDescriptor) o;

        return Objects.equal(this.index, that.index) &&
                Objects.equal(this.required, that.required) &&
                Objects.equal(this.step, that.step) &&
                Objects.equal(this.label, that.label);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("index", index)
                .add("step", step)
                .add("label", label)
                .add("required", required)
                .toString();
    }

    @Override
    public int getPosition() {
        return this.index;
    }

    @Override
    public void setPosition(final int position) {
        this.index = (short) position;
    }
}
