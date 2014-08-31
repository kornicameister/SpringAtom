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

package org.agatom.springatom.web.wizards.action;

import com.google.common.base.Objects;
import org.agatom.springatom.web.action.model.AbstractAction;

/**
 * <p>WizardEventAction class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class WizardEventAction
        extends AbstractAction {
    private static final long   serialVersionUID = 7927406578178789028L;
    private              String eventName        = null;
    private              String eventIconClass   = null;

    public String getEventIconClass() {
        return this.eventIconClass;
    }

    public void setEventIconClass(final String eventIconClass) {
        this.eventIconClass = eventIconClass;
    }

    @Override
    public AbstractAction setName(final String name) {
        this.init(name);
        return super.setName(name);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(name, eventName);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WizardEventAction that = (WizardEventAction) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.eventName, that.eventName);
    }

    /**
     * <p>init.</p>
     *
     * @param name a {@link java.lang.String} object.
     *
     * @return a {@link WizardEventAction} object.
     */
    private WizardEventAction init(final String name) {
        this.name = name;
        this.eventName = String.format("_eventId_%s", name);
        return this;
    }

    /**
     * <p>Getter for the field <code>eventName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getEventName() {
        return eventName;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(name)
                .addValue(eventName)
                .toString();
    }
}
