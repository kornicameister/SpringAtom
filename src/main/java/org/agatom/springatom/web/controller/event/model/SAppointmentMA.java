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

package org.agatom.springatom.web.controller.event.model;

import com.google.common.base.Objects;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class SAppointmentMA {
    private String   _dc;
    private DateTime startDate;
    private DateTime endDate;

    public SAppointmentMA() {
    }

    public String get_dc() {
        return _dc;
    }

    public SAppointmentMA set_dc(final String _dc) {
        this._dc = _dc;
        return this;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public SAppointmentMA setStartDate(final DateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public SAppointmentMA setEndDate(final DateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(_dc)
                      .addValue(startDate)
                      .addValue(endDate)
                      .toString();
    }
}
