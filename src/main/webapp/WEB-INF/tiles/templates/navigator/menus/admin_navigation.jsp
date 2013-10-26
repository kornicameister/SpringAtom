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
    <s:message code="label.nav.admin.db" var="adminDbLabel" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${adminDbLabel}" var="adminDbLabelTooltip"
               htmlEscape="true"/>
    <a href="<s:url value="/app/admin/db" htmlEscape="true"/>" title="${adminDbLabelTooltip}">
        <i class="icon-archive icon-color"></i>${adminDbLabel}
    </a>
</li>
<li>
    <s:message code="label.nav.admin.language" var="adminLanguage" htmlEscape="true"/>
    <s:message code="tooltip.nav" arguments="${adminLanguage}" var="adminLanguageTooltip"
               htmlEscape="true"/>
    <a href="<s:url value="/app/admin/language" htmlEscape="true"/>"
       title="${adminLanguageTooltip}">
        <i class="icon-flag icon-color"></i>${adminLanguage}
    </a>
</li>