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
    <i class="fa fa-color mm-submenu-icon fa-magnet"></i>
    <a href="#">
        <s:message code="label.nav.management"/>
    </a>
    <ul>
        <s:url value="/app/garage/lifts" htmlEscape="true" var="liftsUrl"/>
        <security:authorize url="${liftsUrl}">
            <s:message code="label.nav.lifts" var="liftsLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${liftsLabel}" var="liftsTooltip"
                       htmlEscape="true"/>
            <li>
                <a href="${liftsUrl}" title="${liftsTooltip}">
                    <i class="fa fa-anchor fa-color"></i>${liftsLabel}
                </a>
            </li>
        </security:authorize>
        <s:url value="/app/garage/mechanics" htmlEscape="true" var="mechanicsUrl"/>
        <security:authorize url="${mechanicsUrl}">
            <s:message code="label.nav.principals.mechanics" var="mechanics" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${mechanics}" var="mechanicsTooltip"
                       htmlEscape="true"/>
            <li>
                <a href="${mechanicsUrl}" title="${mechanicsTooltip}">
                    <i class="fa fa-wrench fa-color"></i>${mechanics}
                </a>
            </li>
        </security:authorize>
    </ul>
</li>