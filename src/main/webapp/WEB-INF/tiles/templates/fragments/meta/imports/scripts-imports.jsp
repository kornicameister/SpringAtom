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

<c:set var="webjars" value="/static"/>
<c:set var="staticLib" value="/static/lib"/>
<c:set var="staticSa" value="/static/js"/>
<c:set var="notyPath" value="${staticLib}/noty/2.1.0"/>

<script type="text/javascript" src="<s:url value="${webjars}/jquery/2.0.3/jquery.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/mmenu/3.2.1/jquery.mmenu.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${webjars}/momentjs/2.2.1/min/moment.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/tinycon.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/midway.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/alertify/alertify.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/spin.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/simpleModal.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/static/dojo/dojo.js" />"></script>
<script type="text/javascript" src="<s:url value="/static/spring/Spring.js.uncompressed.js" />"></script>
<script type="text/javascript" src="<s:url value="/static/spring/Spring-Dojo.js.uncompressed.js" />"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-mmenu.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-auth.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-core.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-breadcrumb.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-wizards.js"/>"></script>
