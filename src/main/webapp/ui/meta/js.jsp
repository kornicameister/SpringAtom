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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:set var="staticResources" value="/app/static"/>
<c:set var="staticLib" value="${staticResources}/lib"/>
<c:set var="staticSa" value="${staticResources}/js"/>

<jsp:useBean id="lang" scope="request" type="java.util.Locale"/>

<script type="text/javascript" src="<s:url value="${staticResources}/jquery/2.0.3/jquery.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticResources}/momentjs/2.4.0/min/moment.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticResources}/momentjs/2.4.0/lang/${lang.language}.js"/>"></script>
<script type="text/javascript" src="<s:url value='${staticResources}/fullcalendar/1.6.4/fullcalendar.min.js'/>"></script>
<script type="text/javascript" src="<s:url value="${staticResources}/spin-js/1.3.0/spin.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticResources}/dojo/dojo.js" />"></script>
<script type="text/javascript" src="<s:url value="${staticResources}/spring/Spring.js.uncompressed.js" />"></script>
<script type="text/javascript" src="<s:url value="${staticResources}/spring/Spring-Dojo.js.uncompressed.js" />"></script>
<script type="text/javascript" src="<s:url value="${staticResources}/sources/js/jquery.dataTables.min.js" />"></script>

<script type="text/javascript" src="<s:url value="${staticLib}/mmenu/3.2.1/jquery.mmenu.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/tinycon.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/midway.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/alertify/alertify.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticLib}/simpleModal.min.js"/>"></script>

<script type="text/javascript" src="<s:url value="${staticSa}/sa-mmenu.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-auth.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-core.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-breadcrumb.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-wizards.js"/>"></script>
<script type="text/javascript" src="<s:url value="${staticSa}/sa-infopage.js"/>"></script>
