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
import com.google.common.base.Preconditions;
import org.agatom.springatom.web.flows.tags.exception.WebflowTagEvaluationException;
import org.apache.log4j.Logger;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.engine.Flow;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class StateIdTag
        extends RequestContextAwareTag {
    private static final Logger LOGGER           = Logger.getLogger(StateIdTag.class);
    private static final long   serialVersionUID = -1317114256305704439L;
    private FlowDefinition  flow;
    private StateDefinition state;
    private Short           index;
    private String          var;

    public void setFlow(final FlowDefinition flow) {
        this.flow = flow;
    }

    public void setState(final StateDefinition state) {
        this.state = state;
    }

    public void setIndex(final Short index) {
        this.index = index;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    @Override
    protected int doStartTagInternal() throws Exception {
        Preconditions.checkArgument(var != null, "Var to assign stateId can not be null");
        String stateId = null;
        if (this.isExtractableFromFlow()) {
            final String[] stateIds = ((Flow) this.flow).getStateIds();
            try {
                if (this.index >= stateIds.length) {
                    throw new ArrayIndexOutOfBoundsException(index);
                }
                stateId = stateIds[index];
            } catch (ArrayIndexOutOfBoundsException e) {
                LOGGER.error(e);
                throw new WebflowTagEvaluationException(String.format("%s is to high state index", this.index), e);
            } catch (Exception e) {
                throw new WebflowTagEvaluationException(e);
            }
        } else if (this.isExtractableFromState()) {
            stateId = this.state.getId();
        }
        if (stateId != null) {
            LOGGER.debug(String.format("Resolved stateId -> %s", stateId));
            pageContext.setAttribute(this.var, stateId);
            return SKIP_BODY;
        }
        throw new WebflowTagEvaluationException("Failed to set stateId");
    }

    private boolean isExtractableFromState() {
        return this.state != null;
    }

    private boolean isExtractableFromFlow() {
        return (this.flow != null && this.index != null) && ClassUtils.isAssignable(Flow.class, this.flow.getClass());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(flow)
                      .addValue(state)
                      .addValue(index)
                      .addValue(var)
                      .toString();
    }


}
