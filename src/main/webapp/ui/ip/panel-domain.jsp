<%@ page import="org.agatom.springatom.ip.annotation.DomainInfoPage" %>
<%@ page import="org.springframework.util.StringUtils" %>
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

<jsp:useBean id="ipInfoPage" scope="request" type="org.agatom.springatom.ip.SDomainInfoPage"/>
<jsp:useBean id="ipDataView" scope="request" type="java.lang.String"/>

<c:set var="dos" value="ip-${ipInfoPage.domain.simpleName}"/>

<section id="${dos}"
         class="x-info-page"
         data-pageRel="${ipInfoPage.rel}"
         data-pageType="<%=StringUtils.uncapitalize(DomainInfoPage.class.getSimpleName())%>"
         data-pageContext="${ipInfoPage.domain.simpleName}">
    <script type="text/javascript" id="ip-descriptor-script">
        $(function () {
            $('#' + '${dos}').loadDomainPage({
                view    : '${ipDataView}',
                infoPage: '${ipInfoPage.rel}'
            });
        });
    </script>
</section>