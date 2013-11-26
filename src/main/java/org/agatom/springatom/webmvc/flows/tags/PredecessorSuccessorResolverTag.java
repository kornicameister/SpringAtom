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

package org.agatom.springatom.webmvc.flows.tags;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PredecessorSuccessorResolverTag
        extends RequestContextAwareTag {
    private static final Logger LOGGER = Logger.getLogger(PredecessorSuccessorResolverTag.class);
    private String          var;
    private ViewState       state;
    private AssociationType type;

    public void setState(final ViewState state) {
        this.state = state;
    }

    public void setType(final String type) {
        this.type = AssociationType.valueOf(type.toUpperCase());
    }

    public void setVar(final String var) {
        this.var = var;
    }

    @Override
    protected int doStartTagInternal() throws Exception {
        final Flow owner = (Flow) this.state.getOwner();
        final String[] stateIds = owner.getStateIds();
        final int thisStatePosition = this.calculatePosition(stateIds);
        final Map<String, List<String>> result = this.initializeResultMap();

        this.logDoStartTag(stateIds);
        {
            for (int position = 0 ; position < stateIds.length ; position++) {
                final AssociationType decision = this.getSide(thisStatePosition, position);
                if (this.isIncluded(decision) && this.isTransitionable(stateIds[position], owner)) {
                    result.get(decision.toString().toLowerCase()).add(stateIds[position]);
                }
            }
        }
        this.logDoStartTagResult(result);

        this.pageContext.setAttribute(this.var, new JSONObject(result).toString());
        return EVAL_BODY_INCLUDE;
    }

    private boolean isTransitionable(final String stateId, final Flow owner) {
        try {
            return owner.getTransitionableState(stateId) != null;
        } catch (Exception ignore) {
        }
        return false;
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

    private boolean isIncluded(final AssociationType decision) {
        return decision != null && (this.type.equals(AssociationType.ALL) || decision.equals(this.type));
    }

    private AssociationType getSide(final int thisStatePosition, final int otherStatePosition) {
        if (otherStatePosition > thisStatePosition) {
            return AssociationType.SUCCESSORS;
        } else if (otherStatePosition < thisStatePosition) {
            return AssociationType.PREDECESSORS;
        }
        return null;
    }

    private void logDoStartTagResult(final Map<String, List<String>> result) {
        LOGGER.trace(String.format("ViewState -> %s", this.state.getId()));
        LOGGER.trace("predecessor/successor -> %s");
        for (final String key : result.keySet()) {
            LOGGER.trace(String.format("\ttype=%s --> items=%s", key, result.get(key)));
        }
    }

    private HashMap<String, List<String>> initializeResultMap() {
        final HashMap<String, List<String>> map = Maps.newHashMap();
        if (this.type.equals(AssociationType.ALL)) {
            map.put(AssociationType.PREDECESSORS.toString().toLowerCase(), Lists.<String>newLinkedList());
            map.put(AssociationType.SUCCESSORS.toString().toLowerCase(), Lists.<String>newLinkedList());
        } else {
            map.put(this.type.toString().toLowerCase(), Lists.<String>newLinkedList());
        }
        return map;
    }

    private void logDoStartTag(final String[] stateIds) {
        LOGGER.trace(String.format("ViewState -> %s", this.state.getId()));
        LOGGER.trace(String.format("againts -> %s", Arrays.toString(stateIds)));
        LOGGER.trace(String.format("in flow -> %s", this.state.getOwner().getId()));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(var)
                      .addValue(state)
                      .addValue(type)
                      .toString();
    }

    private enum AssociationType {
        ALL,
        PREDECESSORS,
        SUCCESSORS
    }
}
