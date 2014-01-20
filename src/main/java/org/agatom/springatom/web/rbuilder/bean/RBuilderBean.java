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

package org.agatom.springatom.web.rbuilder.bean;

import org.agatom.springatom.core.util.LocalizationAware;
import org.springframework.hateoas.Identifiable;

import java.io.Serializable;

/**
 * {@link RBuilderBean} is an abstract class which defines plain {@code JavaBean}
 * used in rendering information in {@link org.agatom.springatom.web.flows.wizards.wizard.rbuilder.ReportWizard}'s forms.
 * It is identified by {@link org.springframework.hateoas.Identifiable#getId()} where the id is calculated and defined by subclasses.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class RBuilderBean
implements Serializable,
                   Identifiable<Integer>,
                   LocalizationAware {
    private static final long serialVersionUID = -8556431202961756939L;
    protected String  label;
    protected Integer id;

    @Override
    public Integer getId() {
        if (this.id == null) {
            this.id = this.calculateId();
        }
        return id;
    }

    protected abstract Integer calculateId();

    @Override
    public void setValueForMessageKey(final String msg) {
        this.label = msg;
    }

    public RBuilderBean setLabel(final String label) {
        this.setValueForMessageKey(label);
        return this;
    }

    public String getLabel() {
        return label;
    }
}
