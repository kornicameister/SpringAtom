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

<%@ page session="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <tiles:insertAttribute name="head"/>
    <tiles:insertAttribute name="css" flush="true"/>
    <tiles:insertAttribute name="js" flush="true"/>
</head>
<body class="tundra">
<div id="page">
    <div class="content-wrapper midway-vertical midway-horizontal">
        <header id="header" class="main">
            <s:message code="label.dashboard.header" htmlEscape="true" var="headerLabel"/>
            <p>${headerLabel}</p>

            <div id="authentication-control">
                <security:authorize access="isFullyAuthenticated()" var="userAuthenticated"/>
                <c:choose>
                    <c:when test="${!userAuthenticated}">
                        <tiles:insertDefinition name="springatom.tiles.auth.action.login" ignore="false" flush="true"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:insertDefinition name="springatom.tiles.auth.action.logout" ignore="false" flush="true"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </header>
        <tiles:insertAttribute name="header"/>
        <div class="content">
            <tiles:insertAttribute name="content"/>
        </div>
        <footer id="footer" class="main">
            <p>Footer goes here</p>
        </footer>
    </div>
    <tiles:insertAttribute name="navigator"/>
</div>
</body>
</html>