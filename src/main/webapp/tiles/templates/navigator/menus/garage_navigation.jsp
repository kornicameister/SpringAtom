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
    <i class="icon-color mm-submenu-icon icon-magnet"></i>
    <a href="#">
        <s:message code="label.nav.management"/>
    </a>
    <ul>
        <s:url value="/app/dashboard/garage/clients" htmlEscape="true" var="clientsUrl"/>
        <security:authorize url="${clientsUrl}">
            <s:message code="label.nav.clients" var="clientsLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${clientsLabel}" var="clientsTooltip"
                       htmlEscape="true"/>
            <li>
                <a href="${clientsUrl}" title="${clientsTooltip}">
                    <i class="icon-truck icon-color"></i>${clientsLabel}
                </a>
            </li>
        </security:authorize>
        <s:url value="/app/dashboard/garage/cars" htmlEscape="true" var="carsUrl"/>
        <security:authorize url="${carsUrl}">
            <s:message code="label.nav.cars" var="carsLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${carsLabel}" var="carsTooltip"
                       htmlEscape="true"/>
            <li>
                <a href="${carsUrl}" title="${carsTooltip}">
                    <i class="icon-truck icon-color"></i>${carsLabel}
                </a>
            </li>
        </security:authorize>
    </ul>
</li>