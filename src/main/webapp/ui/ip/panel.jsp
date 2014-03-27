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

<%@ page language="java" session="true" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ip" tagdir="/WEB-INF/tags/ip" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:eval expression="requestScope[T(org.agatom.springatom.web.infopages.InfoPageConstants).INFOPAGE_AVAILABLE]"
        var="ipAvailable"/>

<c:choose>
    <c:when test="${ipAvailable == false}">
        <s:message code="springatom.infopages.noInfoPage"/>
    </c:when>
    <c:otherwise>
        <s:eval expression="requestScope[T(org.agatom.springatom.web.infopages.InfoPageConstants).INFOPAGE_PAGE]"
                var="ipInfoPage"/>
        <c:set var="dos" value="ip-${fn:toLowerCase(ipInfoPage.objectClass.simpleName})"/>
        <section id="${dos}" class="x-info-page">
            <script type="text/javascript" id="ip-descriptor-script">
                $(function () {
                    $('#' + '${dos}').loadDomainPage({
                        view: '<s:eval expression="requestScope[T(org.agatom.springatom.web.infopages.InfoPageConstants).INFOPAGE_VIEW_DATA_TEMPLATE_LINK]"/>'
                    });
                });
            </script>
        </section>
    </c:otherwise>
</c:choose>