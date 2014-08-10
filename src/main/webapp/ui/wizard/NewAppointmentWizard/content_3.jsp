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
<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>

<s:eval expression="newAppointmentStep2.formObjectName" var="modelAttribute" scope="page"/>
<jsp:useBean id="localizedModel" type="org.agatom.springatom.web.locale.beans.LocalizedClassModel" scope="request"/>
<s:eval expression="localizedModel.getLocalizedAttribute('comment')" var="commentLabel"/>

<form:form id="${requestScope.formID}"
           action="${flowExecutionUrl}"
           commandName="${modelAttribute}"
           method="<%=RequestMethod.POST.toString().toLowerCase()%>"
           cssClass="form-horizontal"
           role="form">
	<swf:notificationsBox context="${flowRequestContext}" command="currentFormObject"/>
	<fieldset class="container-fluid" role="contentinfo">
		<legend><s:message code="wizard.NewAppointmentWizard.comment.label"/></legend>
		<div class="form-group row">
			<form:label path="comment" cssClass="col-sm-2 control-label" title="${commentLabel}">
				<span>${commentLabel}</span>
			</form:label>
			<div class="col-sm-9">
				<form:textarea id="${requestScope.formID}-task"
				               htmlEscape="true"
				               cssClass="form-control"
				               cols="25"
				               rows="10"
				               path="comment"/>
			</div>
		</div>
	</fieldset>
</form:form>

<swf:applyDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:applyActionsVisibility forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
