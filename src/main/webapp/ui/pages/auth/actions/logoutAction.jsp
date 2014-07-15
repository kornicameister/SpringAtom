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

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<li id="logout-action" class="dropdown">

    <security:authentication property="principal.username" var="userName"/>
    <security:authentication property="principal.person" var="person"/>
    <s:url value="/app/auth/logout" var="logoutUrl"/>

    <c:choose>
        <c:when test="person != null">
            <security:authentication property="principal.person.information.firstName" var="firstName"/>
            <security:authentication property="principal.person.information.lastName" var="lastName"/>
            <c:set var="loggedUserInnerLabel" value="${firstName} ${lastName}"/>
        </c:when>
        <c:otherwise>
            <c:set var="loggedUserInnerLabel" value="${userName}"/>
        </c:otherwise>
    </c:choose>

    <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
        <i class="glyphicon glyphicon-user"></i>&nbsp;<c:out value="${loggedUserInnerLabel}"/>&nbsp;<span
            class="caret"></span>
    </a>
    <ul id="g-account-menu" class="dropdown-menu" role="menu">
        <li>
            <a href="<s:url value="${logoutUrl}"/>" title="<s:message code="button.logout"/>">
                <i class="glyphicon glyphicon-log-out"></i>
            </a>
        </li>
    </ul>

</li>