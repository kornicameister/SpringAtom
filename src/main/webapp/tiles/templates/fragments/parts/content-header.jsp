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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:message code="label.dashboard.header.search.button" htmlEscape="true" var="buttonLabel"/>

<section class="x-content-header">
    <div class="x-breadcrumb">
        <jsp:useBean id="breadcrumbPath" scope="request"
                     type="org.agatom.springatom.web.support.breadcrumbs.beans.SBreadcrumbPath"/>
        <%--
            provide rest of the implementation
        --%>
        <%--
            test
        --%>
        <span class="x-breadcrumb-element">
            <p class="x-crumb">
                <a href="#">Home</a>
            </p>
            <p class="x-breadcrumb-connector"></p>
        </span>
    </div>
    <div class="x-search">
        <form:form method="post"
                   action="/app/search/global"
                   commandName="searchCommandBean"
                   autocomplete="true"
                   cssClass="x-search-form-global">
            <s:message code="tooltip.dashboard.header.search" var="phrase_title" htmlEscape="true"/>
            <form:input path="phrase"
                        title="${phrase_title}"
                        autocomplete="true"
                        maxlength="20"/>
            <form:button value="${buttonLabel}">
                <i class="icon-search icon-large icon-color"></i>
            </form:button>
        </form:form>
    </div>
</section>