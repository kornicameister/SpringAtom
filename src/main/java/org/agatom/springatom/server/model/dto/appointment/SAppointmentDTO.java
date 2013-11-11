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

package org.agatom.springatom.server.model.dto.appointment;

import com.google.common.base.Objects;
import org.agatom.springatom.server.model.dto.DTO;
import org.joda.time.DateTime;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SAppointmentDTO
        implements DTO {
    private static final long serialVersionUID = 1013016925151868926L;
    private DateTime begin;
    private DateTime end;
    private String   reporter;
    private String   assignee;
    private String   car;

    public DateTime getBegin() {
        return begin;
    }

    public void setBegin(final DateTime begin) {
        this.begin = begin;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(final DateTime end) {
        this.end = end;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(final String reporter) {
        this.reporter = reporter;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(final String assignee) {
        this.assignee = assignee;
    }

    public String getCar() {
        return car;
    }

    public void setCar(final String car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(begin)
                      .addValue(end)
                      .addValue(reporter)
                      .addValue(assignee)
                      .addValue(car)
                      .toString();
    }
}
