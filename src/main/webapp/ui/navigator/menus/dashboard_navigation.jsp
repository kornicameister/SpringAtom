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

<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<li>
    <i class="fa fa-color fa-dashboard mm-submenu-icon"></i>
    <a href="#">
        <s:message code="label.nav.dashboard"/>
    </a>
    <ul>
        <%-- /app/dashboard/client|mechanic/*/cars--%>
        <s:url value="/app/dashboard/cars" htmlEscape="true"
               var="carsUrl"/>
        <security:authorize url="${carsUrl}">
            <s:message code="label.nav.cars" var="carsLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${carsLabel}" var="carsTooltip"
                       htmlEscape="true"/>
            <li>
                <a href="${carsUrl}" title="${carsTooltip}">
                    <i class="fa fa-truck fa-color"></i>${carsLabel}
                </a>
            </li>
        </security:authorize>
        <%-- /app/dashboard/client|mechanic/*/cars--%>
        <%-- /app/dashboard/client|mechanic/*/calendar--%>
        <s:url value="/app/dashboard/calendar" htmlEscape="true"
               var="calendarUrl"/>
        <security:authorize url="${calendarUrl}">
            <s:message code="label.nav.calendar" var="calendarLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${calendarLabel}" var="calendarTooltip"
                       htmlEscape="true"/>
            <li>
                <a href="${calendarUrl}" title="${calendarTooltip}">
                    <i class="fa fa-calendar fa-color"></i>${calendarLabel}
                </a>
            </li>
        </security:authorize>
        <%-- /app/dashboard/client|mechanic/*/calendar--%>
        <%-- /app/dashboard/client|mechanic/*/reports--%>
        <s:url value="/app/dashboard/reports" htmlEscape="true"
               var="reportsUrl"/>
        <security:authorize url="${reportsUrl}">
            <s:message code="label.nav.reports" var="reportsLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${reportsLabel}" var="reportsTooltip"
                       htmlEscape="true"/>
            <li>
                <a href="${reportsUrl}" title="${reportsTooltip}">
                    <i class="fa fa-briefcase fa-color"></i>${reportsLabel}
                </a>
            </li>
        </security:authorize>
        <%-- /app/dashboard/client|mechanic/*/reports--%>
    </ul>
</li>