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

<jsp:useBean id="calendarInputs"
             type="org.agatom.springatom.web.flows.wizards.wizard.newAppointment.NewAppointmentWizardStep1.CalendarComponentInputs"
             scope="request"/>
<c:if test="${calendarInputs != null}">
	<c:set var="begin" value="${calendarInputs.beginDate}" scope="page"/>
	<c:set var="end" value="${calendarInputs.endDate}" scope="page"/>
	<c:set var="beginT" value="${calendarInputs.beginTime}" scope="page"/>
	<c:set var="endT" value="${calendarInputs.endTime}" scope="page"/>
	<c:set var="readonly" value="${calendarInputs.calendar}" scope="page"/>
	<c:set var="allDay" value="${calendarInputs.allDay}" scope="page"/>
</c:if>

<jsp:useBean id="localizedModel" type="org.agatom.springatom.web.locale.beans.LocalizedClassModel" scope="request"/>

<s:eval expression="localizedModel.getLocalizedAttribute('begin')" var="beginLabel"/>
<s:eval expression="localizedModel.getLocalizedAttribute('end')" var="endLabel"/>
<s:eval expression="localizedModel.getLocalizedAttribute('car')" var="carLabel"/>
<s:eval expression="localizedModel.getLocalizedAttribute('reporter')" var="reporterLabel"/>
<s:eval expression="localizedModel.getLocalizedAttribute('assignee')" var="assigneeLabel"/>

<form:form id="${requestScope.formID}"
           action="${flowExecutionUrl}"
           commandName="appointment"
           method="<%=RequestMethod.POST.toString().toLowerCase()%>"
           cssClass="form-horizontal"
           role="form">
	<swf:notificationsBox context="${flowRequestContext}" command="currentFormObject"/>
	<form:hidden path="allDay" value="${allDay}"/>
	<fieldset class="container-fluid" role="contentinfo">
		<legend><s:message code="wizard.NewAppointmentWizard.tf.label"/></legend>
		<div class="form-group row">
			<label class="col-sm-2 control-label" title="${beginLabel}">
				<span>${beginLabel}</span>
			</label>

			<div class="col-sm-9 input-group">
				<form:input id="${requestScope.formID}-begin-date"
				            htmlEscape="true"
				            cssClass="form-control"
				            type="date"
				            value="${begin}"
				            readonly="${readonly}"
				            path="beginDate"/>
				<div class="input-group-addon">|</div>
				<form:input id="${requestScope.formID}-begin-time"
				            htmlEscape="true"
				            cssClass="form-control"
				            type="time"
				            value="${beginT}"
				            readonly="${readonly}"
				            path="beginTime"/>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label" title="${endLabel}">
				<span>${endLabel}</span>
			</label>

			<div class="col-sm-9 input-group">
				<form:input id="${requestScope.formID}-end-date"
				            htmlEscape="true"
				            cssClass="form-control"
				            type="date"
				            value="${end}"
				            readonly="${readonly}"
				            path="endDate"/>
				<div class="input-group-addon">|</div>
				<form:input id="${requestScope.formID}-end-time"
				            htmlEscape="true"
				            cssClass="form-control"
				            type="time"
				            value="${endT}"
				            readonly="${readonly}"
				            path="endTime"/>
			</div>
		</div>
	</fieldset>
	<fieldset class="container-fluid" role="contentinfo">
		<legend><s:message code="wizard.NewAppointmentWizard.tt.label"/></legend>
		<div class="form-group row">
			<form:label path="reporter" cssClass="col-sm-2 control-label" title="${reporterLabel}">
				<span>${reporterLabel}</span>
			</form:label>
			<div class="col-sm-9">
				<form:select id="${requestScope.formID}-reporter"
				             htmlEscape="true"
				             cssClass="form-control"
				             items="${requestScope.reporters}"
				             itemLabel="person.identity"
				             itemValue="id"
				             path="reporter"/>
			</div>
		</div>
		<div class="form-group row">
			<form:label path="assignee" cssClass="col-sm-2 control-label" title="${assigneeLabel}">
				<span>${assigneeLabel}</span>
			</form:label>
			<s:message code="wizard.NewAppointmentWizard.tt.assignee.placeholder" var="assigneePlaceholder"/>
			<div class="col-sm-9">
				<form:select id="${requestScope.formID}-assignee"
				             htmlEscape="true"
				             cssClass="form-control"
				             items="${requestScope.assignees}"
				             placeholder="${assigneePlaceholder}"
				             itemLabel="person.identity"
				             itemValue="id"
				             path="assignee"/>
			</div>
		</div>

		<div class="form-group row">
			<form:label path="assignee" cssClass="col-sm-2 control-label" title="${carLabel}">
				<span>${carLabel}</span>
			</form:label>
			<div class="col-sm-9">
				<s:message code="wizard.NewAppointmentWizard.tt.car.placeholder" var="carPlaceholder"/>
				<form:select id="${requestScope.formID}-car"
				             htmlEscape="true"
				             cssClass="form-control"
				             items="${requestScope.cars}"
				             placeholder="${carPlaceholder}"
				             itemLabel="licencePlate"
				             itemValue="id"
				             path="car"/>
			</div>
		</div>
	</fieldset>
</form:form>
<swf:applyDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:applyActionsVisibility forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
