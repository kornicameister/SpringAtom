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

package org.agatom.springatom.web.flows.tags;

import com.google.common.base.Objects;
import org.agatom.springatom.web.flows.wizards.events.WizardEvent;
import org.agatom.springatom.web.flows.wizards.support.WizardHeaderDescriptor;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.ViewState;

import java.util.Arrays;

/**
 * <p>TransitionResolverTag class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class TransitionResolverTag
		extends RequestContextAwareTag {
	private static final Logger LOGGER           = Logger.getLogger(TransitionResolverTag.class);
	private static final long   serialVersionUID = 1681726382300723398L;
	private WizardEvent    wizardPreviousEvent;
	private WizardEvent    wizardNextEvent;
	private String         var;
	private ViewState      state;
	private TransitionType type;

	/**
	 * <p>Setter for the field <code>state</code>.</p>
	 *
	 * @param state a {@link org.springframework.webflow.engine.ViewState} object.
	 */
	public void setState(final ViewState state) {
		this.state = state;
	}

	/**
	 * <p>Setter for the field <code>type</code>.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	public void setType(final String type) {
		this.type = TransitionType.valueOf(type.toUpperCase());
	}

	/**
	 * <p>Setter for the field <code>var</code>.</p>
	 *
	 * @param var a {@link java.lang.String} object.
	 */
	public void setVar(final String var) {
		this.var = var;
	}

	/** {@inheritDoc} */
	@Override
	protected int doStartTagInternal() throws Exception {
		this.wizardPreviousEvent = (WizardEvent) this.getRequestContext().getWebApplicationContext()
				.getBean("WizardPreviousEvent");
		this.wizardNextEvent = (WizardEvent) this.getRequestContext().getWebApplicationContext()
				.getBean("WizardNextEvent");

		final Flow owner = (Flow) this.state.getOwner();
		final String[] stateIds = owner.getStateIds();
		final int thisStatePosition = this.calculatePosition(stateIds);
		final WizardHeaderDescriptor descriptor = new WizardHeaderDescriptor(this.state.getId());

		this.logDoStartTag(stateIds);
		{
			for (int position = 0; position < stateIds.length; position++) {
				if (position == thisStatePosition) {
					continue;
				}
				final WizardEvent side = this.getSide(thisStatePosition, position);
				if (this.isIncluded(side) && this.isAccessible(stateIds[position], owner)) {
					switch (side.getName()) {
						case "previous":
							descriptor.addPredecessor(stateIds[position]);
							break;
						default:
							descriptor.addSuccessor(stateIds[position]);
					}
				}
			}
		}
		this.logDoStartTagResult(descriptor);

		this.pageContext.setAttribute(this.var, new JSONObject(descriptor).toString());
		return EVAL_BODY_INCLUDE;
	}

	private int calculatePosition(final String[] stateIds) {
		int index = 0;
		for (final String stateId : stateIds) {
			if (!stateId.equals(this.state.getId())) {
				index++;
			} else {
				break;
			}
		}
		return index;
	}

	private void logDoStartTag(final String[] stateIds) {
		LOGGER.trace(String.format("ViewState -> %s", this.state.getId()));
		LOGGER.trace(String.format("againts -> %s", Arrays.toString(stateIds)));
		LOGGER.trace(String.format("in flow -> %s", this.state.getOwner().getId()));
	}

	private WizardEvent getSide(final int thisStatePosition, final int otherStatePosition) {
		if (otherStatePosition > thisStatePosition) {
			return wizardNextEvent;
		} else if (otherStatePosition < thisStatePosition) {
			return wizardPreviousEvent;
		}
		return null;
	}

	private boolean isIncluded(final WizardEvent side) {
		return side != null && (this.type.equals(TransitionType.ALL) || side.getName().equals(this.type.action));
	}

	private boolean isAccessible(final String stateId, final Flow owner) {
		try {
			boolean isAccessible = owner.getTransitionableState(stateId) != null;
			// is available as successors
			Transition transition = (Transition) this.state.getTransition(this.wizardNextEvent.getName());
			if (!isAccessible) {
				// or maybe as predecessor
				transition = (Transition) this.state.getTransition(this.wizardPreviousEvent.getName());
			}
			return transition != null && transition.getTargetStateId().equals(stateId);
		} catch (Exception ignore) {
		}
		return false;
	}

	private void logDoStartTagResult(final WizardHeaderDescriptor result) {
		LOGGER.trace(String.format("ViewState -> %s", this.state.getId()));
		LOGGER.trace("predecessor/successor -> %s");
		LOGGER.trace(String.format("\n\t%s", result));
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(var)
				.addValue(state)
				.addValue(type)
				.toString();
	}

	private enum TransitionType {
		ALL(null),
		PREDECESSORS("previous"),
		SUCCESSORS("next");
		private final String action;

		TransitionType(final String action) {
			this.action = action;
		}
	}
}
