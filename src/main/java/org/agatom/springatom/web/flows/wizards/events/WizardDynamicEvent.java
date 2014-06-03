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

package org.agatom.springatom.web.flows.wizards.events;

import org.agatom.springatom.core.util.Localized;
import org.agatom.springatom.web.beans.WebBean;

/**
 * {@code WizardDynamicEvent} is a decorator over regular {@link org.agatom.springatom.web.flows.wizards.events.WizardEvent}
 *
 * @author kornicameister
 * @version 0.0.1
 * @see org.agatom.springatom.web.flows.wizards.events.WizardEvent
 * @since 0.0.1
 */
public class WizardDynamicEvent
		implements WebBean,
		Localized {
	private static final String BEAN_ID          = "dynamicWizardAction";
	private static final long   serialVersionUID = -3951680406772922964L;
	private final WizardEvent event;
	private       String      stateId;
	private       String      wizardId;
	private       String      labelName;
	private       boolean     isFinish;

	/**
	 * <p>Constructor for WizardDynamicEvent.</p>
	 *
	 * @param wizardEvent a {@link org.agatom.springatom.web.flows.wizards.events.WizardEvent} object.
	 */
	public WizardDynamicEvent(final WizardEvent wizardEvent) {
		this.event = wizardEvent;
	}

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return event.getName();
	}

	/**
	 * <p>getEventName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getEventName() {
		return event.getEventName();
	}

	/**
	 * <p>Getter for the field <code>stateId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStateId() {
		return stateId;
	}

	/**
	 * <p>Setter for the field <code>stateId</code>.</p>
	 *
	 * @param stateId a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.flows.wizards.events.WizardDynamicEvent} object.
	 */
	public WizardDynamicEvent setStateId(final String stateId) {
		this.stateId = stateId;
		return this;
	}

	/**
	 * <p>Getter for the field <code>wizardId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getWizardId() {
		return wizardId;
	}

	/**
	 * <p>Setter for the field <code>wizardId</code>.</p>
	 *
	 * @param wizardId a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.flows.wizards.events.WizardDynamicEvent} object.
	 */
	public WizardDynamicEvent setWizardId(final String wizardId) {
		this.wizardId = wizardId;
		return this;
	}

	/**
	 * <p>Getter for the field <code>labelName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLabelName() {
		return labelName;
	}

	/**
	 * <p>Setter for the field <code>labelName</code>.</p>
	 *
	 * @param labelName a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.flows.wizards.events.WizardDynamicEvent} object.
	 */
	public WizardDynamicEvent setLabelName(final String labelName) {
		this.labelName = labelName;
		return this;
	}

	/**
	 * <p>isFinish.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isFinish() {
		return isFinish;
	}

	/**
	 * <p>setFinish.</p>
	 *
	 * @param isFinish a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.flows.wizards.events.WizardDynamicEvent} object.
	 */
	public WizardDynamicEvent setFinish(final boolean isFinish) {
		this.isFinish = isFinish;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getBeanId() {
		return BEAN_ID;
	}

	/** {@inheritDoc} */
	@Override
	public String getMessageKey() {
		return String.format("wizard.%s.%s.%s", this.wizardId, this.stateId, this.event.getName());
	}
}
