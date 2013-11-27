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

package org.agatom.springatom.webmvc.flows.wizard.ui;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.agatom.springatom.web.beans.WebBean;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class WizardHeaderDescriptor
        implements WebBean {
    private static final String BEAN_ID = "wiz_header_descriptor";
    private final String       state;
    private       List<String> successors;
    private       List<String> predecessors;

    public WizardHeaderDescriptor(final String state) {
        this.state = state;
        this.successors = Lists.newLinkedList();
        this.predecessors = Lists.newLinkedList();
    }

    public String getState() {
        return state;
    }

    public List<String> getSuccessors() {
        return successors;
    }

    public WizardHeaderDescriptor setSuccessors(final List<String> successors) {
        this.successors = successors;
        return this;
    }

    public List<String> getPredecessors() {
        return predecessors;
    }

    public WizardHeaderDescriptor setPredecessors(final List<String> predecessors) {
        this.predecessors = predecessors;
        return this;
    }

    public boolean addSuccessor(final String successor) {
        return successors.add(successor);
    }

    public boolean addPredecessor(final String successor) {
        return predecessors.add(successor);
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state, successors, predecessors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WizardHeaderDescriptor that = (WizardHeaderDescriptor) o;

        return Objects.equal(this.state, that.state) &&
                Objects.equal(this.successors, that.successors) &&
                Objects.equal(this.predecessors, that.predecessors);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(state)
                      .addValue(successors)
                      .addValue(predecessors)
                      .toString();
    }
}
