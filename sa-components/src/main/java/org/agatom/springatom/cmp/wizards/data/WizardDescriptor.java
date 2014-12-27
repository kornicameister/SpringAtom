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

package org.agatom.springatom.cmp.wizards.data;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.cmp.component.Component;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Set;

/**
 * {@code WizardDescriptor} describes entire wizard. Contains meta information (title, labels, list of steps) that are used
 * to render a wizard properly in the client side.
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
public class WizardDescriptor
        extends Component
        implements Serializable {
    private static final long                      serialVersionUID = 8779554824213960312L;
    private              Set<WizardStepDescriptor> descriptorSet    = null;

    public boolean addStep(final WizardStepDescriptor wizardStepDescriptor) {
        return this.addContent(wizardStepDescriptor);
    }

    private boolean addContent(final WizardStepDescriptor wizardStepDescriptor) {
        return wizardStepDescriptor != null && this.getContent().add(wizardStepDescriptor);
    }

    private Set<WizardStepDescriptor> getContent() {
        if (this.descriptorSet == null) {
            this.descriptorSet = Sets.newHashSetWithExpectedSize(3);
        }
        return this.descriptorSet;
    }

    public WizardStepDescriptor getStep(final String key) {
        return FluentIterable.from(this.getContent()).firstMatch(new Predicate<WizardStepDescriptor>() {
            @Override
            public boolean apply(@Nullable final WizardStepDescriptor input) {
                return input != null && input.getStep().equals(key);
            }
        }).get();
    }

    public boolean removeStep(final WizardStepDescriptor wizardStepDescriptor) {
        return this.getContent().remove(wizardStepDescriptor);
    }

    public boolean containsStep(final WizardStepDescriptor wizardStepDescriptor) {
        return this.getContent() != null && this.getContent().contains(wizardStepDescriptor);
    }
}
