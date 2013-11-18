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

package org.agatom.springatom.web.beans.calendar;

import org.agatom.springatom.web.beans.WebBean;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class CalendarEvent
        extends ResourceSupport
        implements WebBean {
    private static final String BEAN_ID = "calendar_event";
    @JsonProperty(value = "id")
    private Long     primaryKey;
    private String   title;
    private DateTime start;
    private DateTime end;

    public Long getPrimaryKey() {
        return primaryKey;
    }

    public CalendarEvent setPrimaryKey(final Long id) {
        this.primaryKey = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CalendarEvent setTitle(final String title) {
        this.title = title;
        return this;
    }

    public DateTime getStart() {
        return start;
    }

    public CalendarEvent setStart(final DateTime start) {
        this.start = start;
        return this;
    }

    public DateTime getEnd() {
        return end;
    }

    public CalendarEvent setEnd(final DateTime end) {
        this.end = end;
        return this;
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }
}
