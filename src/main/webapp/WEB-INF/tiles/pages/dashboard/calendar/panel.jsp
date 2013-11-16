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

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>

<a href="<s:url value="/wizard/NewAppointment"/>" id="dddd">AAAAAAA</a>
<section class="x-calendar">
    <div id="calendar"></div>
</section>
<s:message code="date.months" var="rawMonthNames" scope="page"/>
<c:set var="monthNames" scope="page" value="${fun:split(rawMonthNames, ',')}"/>
<s:message code="date.months.short" var="rawShortMonthNames" scope="page"/>
<c:set var="monthNames" scope="page" value="${fun:split(rawMonthNames, ',')}"/>

<s:message code="date.days" var="rawDays" scope="page"/>
<c:set var="monthNames" scope="page" value="${fun:split(rawMonthNames, ',')}"/>
<s:message code="date.days.short" var="rawShortDays" scope="page"/>
<c:set var="monthNames" scope="page" value="${fun:split(rawMonthNames, ',')}"/>
<script>
    $(document).ready(function () {
        Spring.addDecoration(new Spring.AjaxEventDecoration({ elementId: 'dddd', event: 'onclick', popup: true, params: { mode: "embedded"} }));

        var monthNames = [<c:forEach items="${monthNames}" var="a" varStatus="it">"${a}"<c:if test="${!it.last}">, </c:if></c:forEach>];
        var monthNamesShort = [<c:forEach items="${rawShortMonthNames}" var="a" varStatus="it">"${a}"<c:if test="${!it.last}">, </c:if></c:forEach>];
        var dayNames = [<c:forEach items="${rawDays}" var="a" varStatus="it">"${a}"<c:if test="${!it.last}">, </c:if></c:forEach>];
        var dayNamesShort = [<c:forEach items="${rawShortMonthNames}" var="a" varStatus="it">"${a}"<c:if test="${!it.last}">, </c:if></c:forEach>];
        var $calendar = $('#calendar'),
                parent = $calendar.parent('.x-calendar'),
                pHeight = parent.height();

        $calendar.fullCalendar({
            header         : {
                left  : 'prev,next today',
                center: 'title',
                right : 'month,agendaWeek,agendaDay'
            },
            monthNames     : monthNames,
            dayNames       : dayNames,
            dayNamesShort  : dayNamesShort,
            monthNamesShort: monthNamesShort,
            weekends       : false,
            weekNumbers    : true,
            firstDay       : 1,
            height         : pHeight,
            aspectRatio    : 2.2,
            firstHour      : 8,
            minTime        : 7,
            maxTime        : 21,
            selectHelper   : true,
            selectable     : true,
            editable       : true,
            dayClick       : function (date, allDay, jsEvent, view) {
                $('#dddd').click();
            },
            eventClick     : function (calEvent, jsEvent, view) {
                console.log('Event: ' + calEvent.title);
                console.log('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
                console.log('View: ' + view.name);
            }
        });

    });
</script>