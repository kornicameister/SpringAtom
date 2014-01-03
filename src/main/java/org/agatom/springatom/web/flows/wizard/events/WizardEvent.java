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

package org.agatom.springatom.web.flows.wizard.events;

import com.google.common.base.Objects;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class WizardEvent
        implements Serializable {
    private String name;
    private String eventName;

    public String getName() {
        return name;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, eventName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WizardEvent that = (WizardEvent) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.eventName, that.eventName);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(name)
                      .addValue(eventName)
                      .toString();
    }

    public WizardEvent init(final String name) {
        this.name = name;
        this.eventName = String.format("_eventId_%s", name);
        return this;
    }
}
