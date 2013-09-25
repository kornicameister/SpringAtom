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

<%@ taglib prefix="s"
           uri="http://www.springframework.org/tags" %>

<nav id="menu" data-role="navigation">

    <s:escapeBody htmlEscape="true"/>

    <s:message code="label.nav.index" var="indexLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${indexLabel}" var="indexTooltip" htmlEscape="true"/>

    <s:message code="label.nav.user.notifications" var="notificationLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${notificationLabel}" var="notificationTooltip" htmlEscape="true"/>

    <s:message code="label.nav.user.profile" var="userProfileLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${userProfileLabel}" var="userProfileTooltip" htmlEscape="true"/>

    <s:message code="label.nav.user.profile" var="userProfileLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${userProfileLabel}" var="userProfileTooltip" htmlEscape="true"/>

    <s:message code="label.nav.user.cars" var="userCarsLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${userCarsLabel}" var="userCarsTooltip" htmlEscape="true"/>

    <s:message code="label.nav.user.calendar" var="userCalendarLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${userCalendarLabel}" var="userCalendarTooltip" htmlEscape="true"/>

    <s:message code="label.nav.user.settings" var="userSettingsLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${userSettingsLabel}" var="userSettingsTooltip" htmlEscape="true"/>

    <s:message code="label.nav.cars" var="carsLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${carsLabel}" var="carsTooltip" htmlEscape="true"/>

    <s:message code="label.nav.clients" var="clientsLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${clientsLabel}" var="clientsTooltip" htmlEscape="true"/>

    <s:message code="label.nav.calendar" var="calendarLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${calendarLabel}" var="calendarTooltip" htmlEscape="true"/>

    <s:message code="label.nav.reports" var="reportsLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${reportsLabel}" var="reportsTooltip" htmlEscape="true"/>

    <s:message code="label.nav.utilities" var="utilitiesLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${utilitiesLabel}" var="utilitiesTooltip" htmlEscape="true"/>

    <s:message code="label.nav.about" var="aboutLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${aboutLabel}" var="aboutTooltip" htmlEscape="true"/>

    <ul>
        <li>
            <a href="#menu"><i class="icon-list icon-color"></i></a>
        </li>
        <li class="active">
            <a href="<s:url value="/app" htmlEscape="true"/>" title="${indexTooltip}">
                <i class="icon-desktop icon-color"></i>${indexLabel}
            </a>
        </li>

        <!-- currently logged user navigation -->
        <%-- TODO provide different menus for different roles --%>
        <li>
            <i class="icon-heart mm-submenu-icon icon-color"></i><a href="#">John Doe</a>
            <ul>
                <%-- TODO apppend user name to URL --%>
                <li>
                    <a href="<s:url value="/app/user/notifications" htmlEscape="true"/>" title="${notificationTooltip}">
                        <i class="icon-bell icon-color"></i>${notificationLabel}
                    </a>
                </li>
                <li>
                    <a href="<s:url value="/app/user" htmlEscape="true"/>" title="${userProfileTooltip}">
                        <i class="icon-user icon-color"></i>${userProfileLabel}
                    </a>
                </li>
                <li>
                    <a href="<s:url value="/app/user/cars"/>" title="${userCarsTooltip}">
                        <i class="icon-truck icon-color"></i>${userCarsLabel}
                    </a>
                </li>
                <li>
                    <a href="<s:url value="/app/user/calendar"/>" title="${userCalendarTooltip}">
                        <i class="icon-calendar icon-color"></i>${userCalendarLabel}
                    </a>
                </li>
                <li>
                    <a href="<s:url value="/app/user/settings"/>" title="${userSettingsTooltip}">
                        <i class="icon-gear icon-color"></i>${userSettingsLabel}
                    </a>
                </li>
                <%-- apppend user name to URL --%>
            </ul>
        </li>
        <!-- currently logged user navigation -->

        <!-- cars' related navigation -->
        <li>
            <a href="<s:url value="/app/cars" htmlEscape="true"/>" title="${carsTooltip}">
                <i class="icon-truck icon-color"></i>${carsLabel}
            </a>
        </li>
        <!-- cars' related navigation -->

        <li>
            <a href="<s:url value="/app/clients" htmlEscape="true"/>" title="${clientsTooltip}">
                <i class="icon-male icon-color"></i>${clientsLabel}
            </a>
        </li>
        <li>
            <a href="<s:url value="/app/calendar" htmlEscape="true"/>" title="${calendarTooltip}">
                <i class="icon-calendar icon-color"></i>${calendarLabel}
            </a>
        </li>
        <li>
            <a href="<s:url value="/app/reports" htmlEscape="true"/>" title="${reportsTooltip}">
                <i class="icon-briefcase icon-color"></i>${reportsLabel}
            </a>
        </li>
        <li>
            <a href="<s:url value="/app/utilities" htmlEscape="true"/>" title="${utilitiesTooltip}">
                <i class="icon-terminal icon-color"></i>${utilitiesLabel}
            </a>
        </li>
        <li>
            <a href="<s:url value="/app/about" htmlEscape="true"/>" title="${aboutTooltip}">
                <i class="icon-question icon-color"></i>${aboutLabel}
            </a>
        </li>
    </ul>
</nav>