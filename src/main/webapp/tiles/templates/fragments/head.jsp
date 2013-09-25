<%@ taglib prefix="s"
           uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

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

<head>
    <%-- variables starts here --%>
    <s:url value="/webjars/jquery/2.0.3/jquery.js" scope="application" htmlEscape="true" var="jqueryJs"/>

    <s:url value="/webjars/momentjs/2.1.0/min/moment.min.js" scope="application" htmlEscape="true" var="momentJs"/>
    <s:url value="/static/lib/tinycon.min.js" scope="application" htmlEscape="true" var="tinyIconJS"/>
    <s:url value="/static/lib/midway.min.js" scope="application" htmlEscape="true" var="midwayJS"/>

    <s:url value="/static/lib/mmenu/3.2.1/jquery.mmenu.min.js" scope="application" htmlEscape="true" var="mmenuJs"/>

    <c:set var="notyPath" value="/static/lib/noty/2.1.0/" scope="application"/>
    <s:url value="${notyPath}jquery.noty.js" scope="application" htmlEscape="true" var="notyJs"/>
    <s:url value="${notyPath}layouts/top.js" scope="application" htmlEscape="true" var="notyLayoutJs"/>
    <s:url value="${notyPath}themes/default.js" scope="application" htmlEscape="true" var="notyThemeJs"/>

    <s:url value="/static/lib/searchMeme/jquery.searchMeme.js" scope="application" htmlEscape="true"
           var="searchMemeJs"/>

    <jsp:useBean id="pageTitle" scope="request" type="java.lang.String"/>
    <jsp:useBean id="lang" scope="request" type="java.util.Locale"/>
    <%-- variables starts here --%>

    <%-- head starts here--%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="${lang.language}"/>
    <meta name="author" content="kornicameister@gmail.com"/>
    <meta name="robots" content="noindex, nofollow"/>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes">

    <title><c:out value="${pageTitle}"/></title>

    <link rel="stylesheet" media="all" href="<s:theme code="application" htmlEscape="true" scope="application"/>">
    <link rel="stylesheet" media="all"
          href="<s:theme code="application.navigator.mmenu" htmlEscape="true" scope="application"/>">
    <link rel="stylesheet" media="all"
          href="<s:theme code="application.forms.search.searchMeme" htmlEscape="true" scope="application"/>">
    <link rel="stylesheet" media="all"
          href="<s:theme code="application.fonts.fontsAwesome" htmlEscape="true" scope="application"/>">

    <script type="text/javascript" src="${jqueryJs}"></script>
    <script type="text/javascript" src="${mmenuJs}"></script>
    <script type="text/javascript" src="${momentJs}"></script>
    <script type="text/javascript" src="${tinyIconJS}"></script>
    <script type="text/javascript" src="${midwayJS}"></script>
    <script type="text/javascript" src="${notyJs}"></script>
    <script type="text/javascript" src="${notyLayoutJs}"></script>
    <script type="text/javascript" src="${notyThemeJs}"></script>
    <script type="text/javascript" src="${searchMemeJs}"></script>
    <%-- head starts here--%>
</head>