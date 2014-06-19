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
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="staticResources" value="/app/static"/>

<link rel="stylesheet" media="all" href="<s:theme code="application"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.auth"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.content.header"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.navigator.mmenu"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.navigator.mmenu.extra"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.fonts.fontsAwesome"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.forms"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.modals"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.buttons"/>">
<link rel="stylesheet" media="all" href="<s:theme code="application.wizards"/>">

<link rel="stylesheet" type="text/css" href="http://cdn.sencha.com/ext/gpl/4.2.1/resources/css/ext-all-neptune.css"/>

<link rel="stylesheet" media="all" href="<s:url value="${staticResources}/lib/alertify/themes/alertify.core.css"/>"/>
<link rel="stylesheet" media="all" href="<s:url value="${staticResources}/lib/alertify/themes/alertify.default.css"/>"/>
<link rel="stylesheet" media="all" href="<s:url value="${staticResources}/dijit/themes/tundra/tundra.css" />"/>
<link rel="stylesheet" media="all" href="<s:url value="${staticResources}/fullcalendar/2.0.0/fullcalendar.css"/>"/>
<link rel="stylesheet" media="print"
      href="<s:url value="${staticResources}/fullcalendar/2.0.0/fullcalendar.print.css"/>"/>
<link rel="stylesheet" media="all" href="<s:url value="/app/static/css/fullcalendar-extra.css"/>"/>
<link rel="stylesheet" media="all" href="<s:url value="/app/static/css/infopages.css"/>"/>
<link rel="stylesheet" media="all" href="<s:url value="${staticResources}/sources/css/jquery.dataTables.css" />"/>