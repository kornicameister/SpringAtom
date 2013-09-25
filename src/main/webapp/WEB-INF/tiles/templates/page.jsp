<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page session="true"
         language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         autoFlush="true" %>

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

<!DOCTYPE html>
<html>
<head>
    <%@ include file="fragments/head.jsp" %>
</head>
<body>
<div id="page">
    <div class="content-wrapper midway-vertical midway-horizontal">
        <%@ include file="fragments/header.jsp" %>
        <div class="content nano">
            <div class="breadcrumb nano midway-horizontal">
                <tiles:insertAttribute name="breadcrumb"/>
            </div>
            <div id="content">
                <tiles:insertAttribute name="content"/>
            </div>
            <div class="extra nano midway-horizontal">
                <tiles:insertAttribute name="extra"/>
            </div>
        </div>
        <%@ include file="fragments/footer.jsp" %>
    </div>
    <%@ include file="fragments/navigator.jsp" %>
</div>
<%@ include file="scripts/inline.jspf" %>
</body>
</html>