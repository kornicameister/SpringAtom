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

<li>
    <a href="#menu"><i class="icon-list icon-color"></i>Menu</a>
</li>
<li class="active">
    <s:message code="label.nav.index" var="indexLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${indexLabel}" var="indexTooltip" htmlEscape="true"/>
    <a href="<s:url value="/app/" htmlEscape="true"/>" title="${indexTooltip}">
    <i class="icon-desktop icon-color"></i>${indexLabel}
    </a>
</li>
<li>
    <s:message code="label.nav.free.reports" var="freeReportsLabel"/>
    <s:message code="tooltip.nav" arguments="${freeReportsLabel}" var="freeReportsTooltip"/>
    <a href="<s:url value="/app/reports" htmlEscape="true"/>"
       title="${freeReportsTooltip}">
        <i class="icon-book icon-color"></i>${freeReportsLabel}
    </a>
</li>
<li>
    <s:message code="label.nav.about" var="aboutLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${aboutLabel}" var="aboutTooltip" htmlEscape="true"/>
    <a href="<s:url value="/app/about" htmlEscape="true"/>" title="${aboutTooltip}">
        <i class="icon-question icon-color"></i>${aboutLabel}
    </a>
</li>