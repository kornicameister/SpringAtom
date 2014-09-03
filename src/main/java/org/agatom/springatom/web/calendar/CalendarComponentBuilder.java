/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.web.calendar;

import com.google.common.collect.Maps;
import org.agatom.springatom.web.component.core.builders.AbstractBuilder;
import org.agatom.springatom.web.component.core.builders.ComponentDefinitionBuilder;
import org.agatom.springatom.web.component.core.builders.ComponentProduces;
import org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.web.component.core.builders.exception.ComponentException;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.core.data.ComponentDataResponse;
import org.agatom.springatom.web.locale.SMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-01</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@ComponentBuilder(CalendarComponentBuilder.BUILDER_ID)
public class CalendarComponentBuilder
        extends AbstractBuilder
        implements ComponentDefinitionBuilder<Calendar> {
    protected static final String         BUILDER_ID            = "calendarBuilder";
    private static final   String[]       EMPTY_ARRAY_OF_STRING = new String[]{};
    private static final   String         DAY_NAMES             = "dayNames";
    private static final   String         DAY_NAMES_SHORT       = "dayNamesShort";
    private static final   String         MONTH_NAMES           = "monthNames";
    private static final   String         MONTH_NAMES_SHORT     = "monthNamesShort";
    private static final   String         AXIS_FORMAT           = "axisFormat";
    private static final   String         ALL_DAY_TEXT          = "allDayText";
    private static final   String         COLUMN_FORMAT         = "columnFormat";
    private static final   String         EVENT_SOURCES         = "eventSources";
    private static final   String         MIN_TIME              = "minTime";
    private static final   String         MAX_TIME              = "maxTime";
    private static final   String         FIRST_HOUR            = "firstHour";
    private static final   String         DEFAULT_VIEW          = "defaultView";
    private static final   String         DEFAULT_EVENT_MINUTES = "defaultEventMinutes";
    private static final   String         WIZARD_HREF           = "wizardHref";
    @Autowired
    private                SMessageSource messageSource         = null;
    @Autowired
    @Qualifier("applicationPropertiesBean")
    private                Properties     applicationProperties = null;

    @Override
    public ComponentProduces getProduces() {
        return ComponentProduces.OTHER_COMPONENT;
    }

    @Override
    public Class<?> getBuilds() {
        return Calendar.class;
    }

    @Override
    public ComponentDataResponse getDefinition(final ComponentDataRequest dataRequest) throws ComponentException {
        final Calendar configuration = new Calendar();

        final Locale locale = LocaleContextHolder.getLocale();
        final long startTime = System.nanoTime();

        configuration.put(MONTH_NAMES, this.toJSArray(messageSource.getMessage("date.months", locale)));
        configuration.put(MONTH_NAMES_SHORT, this.toJSArray(messageSource.getMessage("date.months.short", locale)));
        configuration.put(DAY_NAMES, this.toJSArray(messageSource.getMessage("date.days", locale)));
        configuration.put(DAY_NAMES_SHORT, this.toJSArray(messageSource.getMessage("date.days.short", locale)));

        configuration.put(AXIS_FORMAT, messageSource.getMessage("date.format.hours", locale));
        configuration.put(ALL_DAY_TEXT, messageSource.getMessage("date.allDayText", locale));
        configuration.put(COLUMN_FORMAT, this.getColumnFormat(applicationProperties));
        configuration.put(EVENT_SOURCES, this.getEventSources(applicationProperties));

        configuration.put(MIN_TIME, Integer.valueOf(applicationProperties.getProperty("component.calendar.minTime")));
        configuration.put(MAX_TIME, Integer.valueOf(applicationProperties.getProperty("component.calendar.maxTime")));
        configuration.put(FIRST_HOUR, Integer.valueOf(applicationProperties.getProperty("component.calendar.firstHour")));
        configuration.put(DEFAULT_VIEW, applicationProperties.getProperty("component.calendar.view"));
        configuration.put(DEFAULT_EVENT_MINUTES, Integer.valueOf(applicationProperties.getProperty("component.calendar.defaultEventMinutes")));

        configuration.setLabel(this.messageSource.getMessage("scalendar", locale));

        return ComponentDataResponse.success(BUILDER_ID, configuration, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
    }

    private String[] toJSArray(final String message) {
        if (!StringUtils.hasText(message)) {
            return EMPTY_ARRAY_OF_STRING;
        }
        return StringUtils.tokenizeToStringArray(message, ",");
    }

    private Map<String, String> getColumnFormat(final Properties applicationProperties) {
        final Map<String, String> map = Maps.newHashMap();
        map.put("month", applicationProperties.getProperty("component.calendar.columnFormat.month"));
        map.put("day", applicationProperties.getProperty("component.calendar.columnFormat.day"));
        map.put("week", applicationProperties.getProperty("component.calendar.columnFormat.week"));
        return map;
    }

    private Object[] getEventSources(final Properties applicationProperties) {
        final Map<String, String> configuration = Maps.newHashMap();
        configuration.put("startParam", applicationProperties.getProperty("component.calendar.startParam"));
        configuration.put("endParam", applicationProperties.getProperty("component.calendar.endParam"));
        configuration.put("url", applicationProperties.getProperty("component.calendar.eventSource.href"));
        configuration.put("method", applicationProperties.getProperty("component.calendar.eventSource.method"));
        return new Object[]{configuration};
    }
}
