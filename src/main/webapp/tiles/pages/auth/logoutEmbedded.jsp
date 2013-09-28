<%@page session="true" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

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

<tiles:importAttribute name="mode" scope="page" toName="formEmbedded"/>
<c:choose>
    <c:when test="formEmbedded == ''">
        <c:set var="formCssClass" value="logout-form"/>
    </c:when>
    <c:otherwise>
        <c:set var="formCssClass" value="logout-embedded-form"/>
    </c:otherwise>
</c:choose>
<section id="logout" class="auth logout ${formCssClass}">
    <s:url value="/app/auth/logout" var="logoutUrl"/>
    <security:authentication property="principal.username" var="userName"/>
    <security:authentication property="principal.person" var="person"/>
    <div class="logout-content">

        <p class="login">
            <i class="icon-color icon-user icon-large"></i>
            <span class="login-name"><c:out value="${userName}"/></span>
        </p>

        <c:if test="person != null">
            <p class="personal-info">
                <security:authentication property="principal.person.information.firstName" var="firstName"/>
                <security:authentication property="principal.person.information.lastName" var="lastName"/>
                <i class="icon-color icon-male icon-large"></i>
                <c:out value="${firstName} ${lastName}"/>
            </p>
        </c:if>

        <p class="logout-submit">
            <a href="<s:url value="${logoutUrl}"/>"
               title="<s:message code="button.logout"/>">
                <button type="submit" class="logout-button"></button>
            </a>
        </p>
    </div>
</section>