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
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<nav id="header" class="navbar navbar-inverse navbar-default navbar-static-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="offcanvas" data-target=".sidebar-nav">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <s:message code="label.dashboard.header" htmlEscape="true" var="headerLabel"/>
            <span class="navbar-text">${headerLabel}</span>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <security:authorize access="isFullyAuthenticated()" var="userAuthenticated"/>
                <c:choose>
                    <c:when test="${!userAuthenticated}">
                        <tiles:insertDefinition name="springatom.tiles.auth.action.login" flush="true"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:insertDefinition name="springatom.tiles.auth.action.logout" flush="true"/>
                    </c:otherwise>
                </c:choose>
            </ul>
            <div class="col-sm-3 col-md-3 pull-right">
                <form:form method="post"
                           action="/app/search/global"
                           commandName="searchCommandBean"
                           autocomplete="true"
                           cssClass="form-inline navbar-form"
                           role="form">
                    <s:message code="tooltip.dashboard.header.search" var="phrase_title" htmlEscape="true"/>
                    <s:message code="label.dashboard.header.search.button" htmlEscape="true" var="buttonLabel"/>
                    <form:input path="phrase"
                                title="${phrase_title}"
                                autocomplete="true"
                                cssClass="form-control input-sm"
                                maxlength="20"/>
                    <form:button value="${buttonLabel}" class="btn btn-primary">
                        <i class="glyphicon glyphicon-search"></i>
                    </form:button>
                </form:form>
            </div>
        </div>
    </div>
</nav>