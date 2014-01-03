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
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class ReportableBean
        implements Serializable,
                   Identifiable<Integer>,
                   LocalizationAware {
    private static int GEN_ID_SEED = 0;
    protected       String  label;
    protected final Integer id;

    protected ReportableBean() {
        this.id = ReportableBean.GEN_ID_SEED++;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setValueForMessageKey(final String msg) {
        this.label = msg;
    }

    public ReportableBean setLabel(final String label) {
        this.label = label;
        return this;
    }

    public String getLabel() {
        return label;
    }
}
