<%--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                 ~
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
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="swf" tagdir="/WEB-INF/tags/swf" %>

<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>

<form:form id="${requestScope.formID}"
           action="${flowExecutionUrl}"
           method="<%=RequestMethod.POST.toString().toLowerCase()%>"
           cssClass="form-horizontal"
           role="form">
	<swf:notificationsBox context="${flowRequestContext}" command="currentFormObject"/>
	<fieldset class="container-fluid" role="contentinfo">
		<legend><s:message code="wizard.NewReportWizard.addMoreEntitiesAndColumns"/></legend>
		<jsp:useBean id="reportConfiguration" scope="request"
		             type="org.agatom.springatom.web.rbuilder.ReportConfiguration"/>

		<c:forEach items="${reportConfiguration.entities}" var="entity" varStatus="loop">
			<p id="${loop.index}">
				<s:message code="wizard.NewReportWizard.info.entitySelected" arguments="${entity.name}"/>
				with <s:eval expression="${entity.columns.size()}"/> columns
			</p>
		</c:forEach>

		<p>
			Click <s:message code="button.next"/>[<s:message code="button.next.short"/>] to go on
		</p>

		<p>
			Click <s:message code="button.previous"/>[<s:message code="button.previous.short"/>] to go back and
			select more columns
		</p>
	</fieldset>
</form:form>

<swf:applyDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:applyActionsVisibility forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
