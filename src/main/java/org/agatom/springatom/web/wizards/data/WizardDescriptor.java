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

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.component.core.elements.DefaultComponent;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class WizardDescriptor
        extends DefaultComponent {
    private static final long                      serialVersionUID = 8779554824213960312L;
    private              Set<WizardStepDescriptor> steps            = null;

    public boolean addStep(final WizardStepDescriptor wizardStepDescriptor) {
        if (this.steps == null) {
            this.steps = Sets.newTreeSet();
        }
        wizardStepDescriptor.setIndex((short) this.steps.size());
        return this.steps.add(wizardStepDescriptor);
    }

    public WizardStepDescriptor getStep(final String key) {
        if (this.steps == null) {
            return null;
        }
        return FluentIterable.from(this.steps).firstMatch(new Predicate<WizardStepDescriptor>() {
            @Override
            public boolean apply(@Nullable final WizardStepDescriptor input) {
                return input != null && input.getStep().equals(key);
            }
        }).get();
    }

    public boolean removeStep(final WizardStepDescriptor wizardStepDescriptor) {
        return this.steps != null && this.steps.remove(wizardStepDescriptor);
    }

    public boolean containsStep(final WizardStepDescriptor wizardStepDescriptor) {
        return this.steps != null && this.steps.contains(wizardStepDescriptor);
    }

    public Set<WizardStepDescriptor> getSteps() {
        return steps;
    }

    public WizardDescriptor setSteps(final Set<WizardStepDescriptor> steps) {
        this.steps = steps;
        return this;
    }
}
