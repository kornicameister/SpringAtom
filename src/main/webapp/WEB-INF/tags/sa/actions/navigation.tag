<%--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                 ~
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

<%@ attribute name="actionModel" rtexprvalue="true" required="true"
              type="org.agatom.springatom.web.action.model.ActionModel" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:forEach items="${actionModel.content}" var="action" varStatus="it">
    <s:eval expression="T(org.springframework.util.ClassUtils).isAssignableValue(T(org.agatom.springatom.web.action.model.ActionModel),action)"
            var="isActionModel"/>
    <c:choose>
        <c:when test="${isActionModel}">
            <li>
                <i class="fa fa-color fa-folder-open-o mm-submenu-icon"></i>
                <a href="#">${action.label}</a>
                <ul>
                    <c:forEach items="${action.content}" var="innerAction">
                        <s:url value="${innerAction.url}" htmlEscape="true" var="levelUrl" scope="page"/>
                        <security:authorize url="${levelUrl}">
                            <li>
                                <s:eval expression="T(org.springframework.util.StringUtils).uncapitalize(T(org.springframework.util.ClassUtils).getShortName(innerAction.class))"
                                        var="actionType"/>
                                <a id="${innerAction.id}"
                                   href="${levelUrl}"
                                   title="${innerAction.label}"
                                   data-dynamic="true"
                                   data-mode="${actionType}">
                                    <i class="fa fa-circle-o fa-color mm-submenu-iconr"></i>${innerAction.label}
                                </a>
                            </li>
                        </security:authorize>
                    </c:forEach>
                </ul>
            </li>
        </c:when>
        <c:otherwise>
            <s:url value="${action.url}" htmlEscape="true" var="levelUrl" scope="page"/>
            <security:authorize url="${levelUrl}">
                <li>
                    <s:eval expression="T(org.springframework.util.StringUtils).uncapitalize(T(org.springframework.util.ClassUtils).getShortName(action.class))"
                            var="actionType"/>
                    <a id="${action.id}"
                       href="${levelUrl}"
                       title="${action.label}"
                       data-dynamic="true"
                       data-mode="${actionType}">
                        <i class="fa fa-circle-o fa-color"></i>${action.label}
                    </a>
                </li>
            </security:authorize>
        </c:otherwise>
    </c:choose>
</c:forEach>