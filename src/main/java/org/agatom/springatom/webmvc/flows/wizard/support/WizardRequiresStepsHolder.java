/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.webmvc.flows.wizard.support;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.io.Serializable;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class WizardRequiresStepsHolder
        implements Serializable {
    private Set<String> requiredStepsIds = Sets.newLinkedHashSet();

    public Set<String> getRequiredStepsIds() {
        return requiredStepsIds;
    }

    public boolean add(final String step) {
        return this.requiredStepsIds.add(step);
    }

    public boolean remove(final Object step) {
        return this.requiredStepsIds.remove(step);
    }

    public boolean has(final String stateId) {
        return this.requiredStepsIds.contains(stateId);
    }
}
