<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>
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
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="swf" tagdir="/WEB-INF/tags/swf" %>

<s:eval expression="newCarWizard1.formObjectName" var="modelAttribute" scope="page"/>

<form:form id="${requestScope.formID}"
           action="${flowExecutionUrl}"
           commandName="${modelAttribute}"
           method="<%=RequestMethod.POST.toString().toLowerCase()%>"
           cssClass="form-horizontal"
           role="form">
	<swf:notificationsBox context="${flowRequestContext}" command="currentFormObject"/>
	<div class="form-group">
		<s:message code="scar.vinnumber" var="scarVinNumber"/>
		<form:label path="vinNumber" cssClass="col-sm-2 control-label">
			<span class="">${scarVinNumber}</span>
		</form:label>
		<div class="col-sm-10">
			<form:input id="${requestScope.formID}-vinNumber"
			            htmlEscape="true"
			            cssClass="form-control"
			            path="vinNumber"
			            cssErrorClass="form-control-feedback"
			            placeholder="${scarVinNumber}"
			            required="true"/>
		</div>
	</div>
</form:form>
<swf:applyDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:applyActionsVisibility forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
