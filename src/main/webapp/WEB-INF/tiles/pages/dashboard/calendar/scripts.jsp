<%@ page import="org.agatom.springatom.server.model.beans.appointment.SAppointment" %>
<%--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                 ~
  ~                                                                                              ~
  ~ [SpringAtom] is free software: you can redistribute it and/or modify                         ~
  ~ it under the terms of the GNU General Public License as published by                         ~
  ~ the Free Software Foundation, either version 3 of the License, or                            ~
  ~ (at your option) any later version.                                                          ~
  ~                                                                                              ~
  ~ [SpringAtom] is distributed in the hope that it will be useful,                              ~
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of                               ~
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                ~
  ~ GNU General Public License for more details.                                                 ~
  ~                                                                                              ~
  ~ You should have received a copy of the GNU General Public License                            ~
  ~ along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                ~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~--%>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>


<s:message code="date.months" var="rawMonthNames" scope="page"/>
<c:set var="monthNames" scope="page" value="${fun:split(rawMonthNames, ',')}"/>
<s:message code="date.months.short" var="rawShortMonthNames" scope="page"/>
<c:set var="monthNames" scope="page" value="${fun:split(rawMonthNames, ',')}"/>

<s:message code="date.days" var="rawDays" scope="page"/>
<c:set var="monthNames" scope="page" value="${fun:split(rawMonthNames, ',')}"/>
<s:message code="date.days.short" var="rawShortDays" scope="page"/>
<c:set var="monthNames" scope="page" value="${fun:split(rawMonthNames, ',')}"/>

<s:message code="<%=SAppointment.class.getName()%>" var="domainName"/>
<s:message code="sa.msg.objectCreated" var="objectCreatedMsg" arguments="${domainName}"/>

<script type="text/javascript" src="<s:url value='/static/lib/fullcalendar/fullcalendar.js' htmlEscape="true"/>"></script>
<script type="text/javascript" id="calendar-loader">
    $(function () {
        Spring.addDecoration(new Spring.AjaxEventDecoration({ elementId: 'dddd', event: 'onclick', popup: true, params: { mode: "embedded"} }));

        var monthNames = [<c:forEach items="${monthNames}" var="a" varStatus="it">"${a}"<c:if test="${!it.last}">, </c:if></c:forEach>];
        var monthNamesShort = [<c:forEach items="${rawShortMonthNames}" var="a" varStatus="it">"${a}"<c:if test="${!it.last}">, </c:if></c:forEach>];
        var dayNames = [<c:forEach items="${rawDays}" var="a" varStatus="it">"${a}"<c:if test="${!it.last}">, </c:if></c:forEach>];
        var dayNamesShort = [<c:forEach items="${rawShortDays}" var="a" varStatus="it">"${a}"<c:if test="${!it.last}">, </c:if></c:forEach>];
        var $calendar = $('#calendar'),
                parent = $calendar.parent('.x-calendar'),
                pHeight = parent.height();

        $calendar.fullCalendar({
            header             : {
                left  : 'prev,next today',
                center: 'title',
                right : 'month,agendaWeek,agendaDay'
            },
            axisFormat         : 'HH:mm',
            columnFormat       : {
                month: 'ddd',    // Mon
                week : 'ddd M/d', // Mon 9/7
                day  : 'dddd M/d'  // Monday 9/7
            },
            defaultEventMinutes: 30,
            allDayText         : 'Caly dzien',
            monthNames         : monthNames,
            dayNames           : dayNames,
            dayNamesShort      : dayNamesShort,
            monthNamesShort    : monthNamesShort,
            weekends           : false,
            weekNumbers        : true,
            firstDay           : 1,
            height             : pHeight,
            aspectRatio        : 2.2,
            firstHour          : 8,
            minTime            : 7,
            maxTime            : 21,
            selectHelper       : true,
            selectable         : true,
            editable           : true,
            dayClick           : function (date, allDay, jsEvent, view) {
                $('#dddd').click();
            },
            eventClick         : function (calEvent, jsEvent, view) {
                console.log('Event: ' + calEvent.title);
                console.log('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
                console.log('View: ' + view.name);
            },
            eventRender        : function (event, element) {
                SA.core.showSuccess('${objectCreatedMsg}' + ' - ' + event.title);
            }
        });

    });
</script>
<script type="text/javascript" id="event-appender-from-uri">
    var cache = {};
    $(function () {
        var action = $.urlParam('action', true);
        console.log('Action found=' + action);

        function parse(_dd) {
            return $.fullCalendar.parseDate(moment(_dd, ['DD.MM.YY HH:mm', 'YYYY-MM-YY'], true).unix());
        }

        if (action === 'finish') {
            var eventId = $.urlParam('eventId', true),
                    eventBegin = $.urlParam('eventBegin', true),
                    eventEnd = $.urlParam('eventEnd', true);
            if (eventId && eventBegin && eventEnd) {

                if (cache[eventId]) {
                    return;
                }

                var event = {
                    id    : eventId,
                    start : parse(eventBegin),
                    end   : parse(eventEnd),
                    allDay: false,
                    title : 'Appointment=' + decodeURIComponent(eventId)
                };

                $('#calendar').fullCalendar('renderEvent', event, true);

                cache[eventId] = true;
            }
        }
    });
</script>