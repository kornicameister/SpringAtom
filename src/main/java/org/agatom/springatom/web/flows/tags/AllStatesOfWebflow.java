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

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.engine.Flow;

import java.util.List;

/**
 * <p>AllStatesOfWebflow class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class AllStatesOfWebflow
		extends RequestContextAwareTag {

	private static final long serialVersionUID = -3389154492381887502L;
	private FlowDefinition flow;

	/**
	 * <p>Setter for the field <code>flow</code>.</p>
	 *
	 * @param flow a {@link org.springframework.webflow.definition.FlowDefinition} object.
	 */
	public void setFlow(final FlowDefinition flow) {
		this.flow = flow;
	}

	/** {@inheritDoc} */
	@Override
	protected int doStartTagInternal() throws Exception {
		Preconditions.checkArgument(this.flow != null, "FlowDefinition can not be null");
		Preconditions.checkArgument(ClassUtils.isAssignable(Flow.class, this.flow.getClass()), "FlowDefinition must be an instance of Flow");
		final Flow flowDef = (Flow) this.flow;
		final String[] stateIds = flowDef.getStateIds();
		final List<String> stateDefinitions = Lists.newArrayListWithExpectedSize(stateIds.length);
		for (final String stateId : stateIds) {
			final StateDefinition state = this.flow.getState(stateId);
			if (state.isViewState()) {
				stateDefinitions.add(state.getId());
			}
		}
		pageContext.setAttribute("states", stateDefinitions);
		return EVAL_BODY_INCLUDE;
	}
}
