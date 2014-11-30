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

<%@ page session="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
	<!-- head -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="pl"/>
	<meta name="author" content="kornicameister@gmail.com"/>
	<meta name="robots" content="noindex, nofollow"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>[SA]::SpringAtom</title>

	<script type="text/javascript">
		window.SA_LANG = 'pl'
	</script>

	<!-- css -->

	<!-- required to load like this, otherwise not working -->
	<link rel="stylesheet" media="all" href="<s:url value="/app/static/libs/bootstrap/dist/css/bootstrap.css"/>">
	<link rel="stylesheet" media="all" href="<s:url value="/app/static/libs/bootstrap/dist/css/bootstrap-theme.css"/>">
	<link rel="stylesheet" media="all" href="<s:url value="/app/static/libs/ng-grid/ng-grid.css"/>">
	<link rel="stylesheet" media="all" href="<s:url value="/app/static/font-awesome/4.2.0/css/font-awesome.min.css"/>">
	<!-- required to load like this, otherwise not working -->
	<link rel="stylesheet" media="all" href="<s:url value="/app/wro/css-files.css"/>">
	<link rel="stylesheet" media="all" href="<s:url value="/app/wro/css-libs.css"/>">
</head>

<body>

<div top-header></div>
<navigator class="navbar navbar-left col-sm-2 navbar-nav ui-spinner"
           role="navigation"></navigator>

<div class="col-sm-10 content-wrapper container-fluid">
	<section class="breadcrumb" breadcrumb></section>
	<section class="slide" ui-view></section>
</div>

</body>

<script type="text/javascript"
        data-main="/app/static/sa/main.js"
        src="<s:url value="/app/static/libs/requirejs/require.js"/>"></script>

</html>
