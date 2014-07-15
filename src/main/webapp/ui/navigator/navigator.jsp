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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<nav id="menu" class="navbar navbar-left col-sm-2 navbar-nav" role="navigation">
    <ul class="nav nav-pills nav-stacked">
        <li class="active">
            <s:message code="label.nav.index" var="indexLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${indexLabel}" var="indexTooltip" htmlEscape="true"/>
            <a href="<s:url value="/app/" htmlEscape="true"/>" title="${indexTooltip}">
                <i class="glyphicon glyphicon-home"></i>${indexLabel}
            </a>
        </li>
        <security:authorize url="/app/dashboard/*">
            <li>
                <a href="<s:url value="/app/dashboard/"/>">
                    <i class="fa fa-color fa-dashboard"></i><s:message code="label.nav.dashboard"/>
                </a>
            </li>
        </security:authorize>
        <security:authorize url="/app/garage/*">
            <li>
                <a href="<s:url value="/app/garage/"/>">
                    <i class="glyphicon glyphicon-briefcase"></i><s:message code="label.nav.management"/>
                </a>
            </li>
        </security:authorize>
        <security:authorize url="/app/admin/*">
            <li>
                <a href="<s:url value="/app/admin/"/>">
                    <i class="glyphicon glyphicon-tower"></i><s:message code="label.nav.admin"/>
                </a>
            </li>
        </security:authorize>
        <li>
            <s:message code="label.nav.free.reports" var="freeReportsLabel"/>
            <s:message code="tooltip.nav" arguments="${freeReportsLabel}" var="freeReportsTooltip"/>
            <a href="<s:url value="/app/reports" htmlEscape="true"/>" title="${freeReportsTooltip}">
                <i class="glyphicon glyphicon-book"></i>${freeReportsLabel}
            </a>
        </li>
        <li>
            <s:message code="label.nav.about" var="aboutLabel" htmlEscape="true"/>
            <s:message code="tooltip.nav" arguments="${aboutLabel}" var="aboutTooltip" htmlEscape="true"/>
            <a href="<s:url value="/app/about" htmlEscape="true"/>" title="${aboutTooltip}">
                <i class="glyphicon glyphicon-question-sign"></i>${aboutLabel}
            </a>
        </li>
    </ul>
</nav>