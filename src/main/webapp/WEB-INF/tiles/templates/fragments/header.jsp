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

<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s"
           uri="http://www.springframework.org/tags" %>

<header id="header" class="main">
    <p><s:message code="label.dashboard.header" htmlEscape="true"/></p>

    <form:form id="header-search-form"
               method="post"
               action="/app/search/global"
               commandName="ssearchcommandbean_global"
               autocomplete="true"
               cssClass="header-search-form header-search-form-cf">
        <s:message code="tooltip.dashboard.header.search" var="phrase_title" htmlEscape="true"/>
        <form:input path="phrase"
                    title="${phrase_title}"
                    maxlength="20"/>
        <button type="submit">
            <i class="icon-search icon-large icon-color"></i>
            <s:message code="label.dashboard.header.search.button" htmlEscape="true"/>
        </button>
    </form:form>

</header>