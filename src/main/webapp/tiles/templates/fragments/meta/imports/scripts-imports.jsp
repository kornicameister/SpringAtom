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

<%@page autoFlush="true" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="webjars" value="/webjars"/>
<c:set var="staticLib" value="/static/lib"/>
<c:set var="staticSa" value="/static/js"/>
<c:set var="notyPath" value="${staticLib}/noty/2.1.0"/>

<s:url value="${webjars}/jquery/2.0.3/jquery.min.js" htmlEscape="true" var="jqueryJs"/>
<s:url value="${webjars}/momentjs/2.1.0/min/moment.min.js" htmlEscape="true" var="momentJs"/>
<s:url value="${staticLib}/tinycon.min.js" htmlEscape="true" var="tinyIconJS"/>
<s:url value="${staticLib}/midway.min.js" htmlEscape="true" var="midwayJS"/>
<s:url value="${staticLib}/mmenu/3.2.1/jquery.mmenu.min.js" htmlEscape="true" var="mmenuJs"/>
<s:url value="${notyPath}/jquery.noty.js" htmlEscape="true" var="notyJs"/>
<s:url value="${notyPath}/layouts/top.js" htmlEscape="true" var="notyLayoutTopJs"/>
<s:url value="${notyPath}/layouts/topCenter.js" htmlEscape="true" var="notyLayoutTopCenterJs"/>
<s:url value="${notyPath}/layouts/center.js" htmlEscape="true" var="notyLayoutCenterJs"/>
<s:url value="${notyPath}/themes/default.js" htmlEscape="true" var="notyThemeJs"/>
<s:url value="${staticSa}/sa-core.js" var="coreJS" htmlEscape="true"/>
<s:url value="${staticSa}/sa-auth.js" var="saAuth" htmlEscape="true"/>
<s:url value="${staticSa}/sa-mmenu.js" var="saMmenu" htmlEscape="true"/>
<s:url value="${staticSa}/sa-breadcrumb.js" var="saBreadcrumb" htmlEscape="true"/>
<s:url value="${staticLib}/spin.min.js" var="spinnerJS" htmlEscape="true"/>

<script type="text/javascript" src="${jqueryJs}"></script>
<script type="text/javascript" src="${mmenuJs}"></script>
<script type="text/javascript" src="${momentJs}"></script>
<script type="text/javascript" src="${tinyIconJS}"></script>
<script type="text/javascript" src="${midwayJS}"></script>
<script type="text/javascript" src="${notyJs}"></script>
<script type="text/javascript" src="${notyLayoutTopJs}"></script>
<script type="text/javascript" src="${notyLayoutCenterJs}"></script>
<script type="text/javascript" src="${notyLayoutTopCenterJs}"></script>
<script type="text/javascript" src="${notyThemeJs}"></script>
<script type="text/javascript" src="${spinnerJS}"></script>
<script type="text/javascript" src="${saMmenu}"></script>
<script type="text/javascript" src="${saAuth}"></script>
<script type="text/javascript" src="${coreJS}"></script>
<script type="text/javascript" src="${saBreadcrumb}"></script>