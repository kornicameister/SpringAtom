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

<div id="sa-wizard-step-body" class="x-wizard-content">
    <swf:renderStepTitle forState="${flowRequestContext.currentState}" cssClass="stepTitle"/>

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

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               commandName="appointment"
               method="<%=RequestMethod.POST.toString().toLowerCase()%>"
               cssClass="x-form">
        <fieldset>
            <form:hidden path="allDay" value="${allDay}"/>
            <legend><s:message code="wizard.NewAppointmentWizard.tf.label"/></legend>
            <p>
                <label class="x-form-label" title="<s:message code="wizard.NewAppointmentWizard.tf.begin.label"/>">
                    <span><s:message code="wizard.NewAppointmentWizard.tf.begin.label"/></span>
                    <form:input id="${requestScope.formID}-begin-date"
                                htmlEscape="true"
                                cssClass="x-input"
                                type="date"
                                value="${begin}"
                                readonly="${readonly}"
                                path="beginDate"/>
                    <form:input id="${requestScope.formID}-begin-time"
                                htmlEscape="true"
                                cssClass="x-input"
                                type="time"
                                value="${beginT}"
                                readonly="${readonly}"
                                path="beginTime"/>
                </label>
            </p>

            <p>
                <label class="x-form-label" title="<s:message code="wizard.NewAppointmentWizard.tf.end.label"/>">
                    <span><s:message code="wizard.NewAppointmentWizard.tf.end.label"/></span>
                    <form:input id="${requestScope.formID}-end-date"
                                htmlEscape="true"
                                cssClass="x-input"
                                type="date"
                                value="${end}"
                                readonly="${readonly}"
                                path="endDate"/>
                    <form:input id="${requestScope.formID}-end-time"
                                htmlEscape="true"
                                cssClass="x-input"
                                type="time"
                                value="${endT}"
                                readonly="${readonly}"
                                path="endTime"/>
                </label>
            </p>
        </fieldset>
        <fieldset>
            <legend><s:message code="wizard.NewAppointmentWizard.tt.label"/></legend>
            <p>
                <label class="x-form-label" title="<s:message code="wizard.NewAppointmentWizard.tt.reporter.label"/>">
                    <span><s:message code="wizard.NewAppointmentWizard.tt.reporter.label"/></span>
                    <form:select id="${requestScope.formID}-reporter"
                                 htmlEscape="true"
                                 cssClass="x-input x-input-select"
                                 items="${requestScope.reporters}"
                                 itemLabel="person.identity"
                                 itemValue="id"
                                 path="reporter"/>
                </label>
            </p>

            <p>
                <label class="x-form-label" title="<s:message code="wizard.NewAppointmentWizard.tt.assignee.label"/>">
                    <span><s:message code="wizard.NewAppointmentWizard.tt.assignee.label"/></span>
                    <s:message code="wizard.NewAppointmentWizard.tt.assignee.placeholder" var="assigneePlaceholder"/>
                    <form:select id="${requestScope.formID}-assignee"
                                 htmlEscape="true"
                                 cssClass="x-input x-input-select"
                                 items="${requestScope.assignees}"
                                 placeholder="${assigneePlaceholder}"
                                 itemLabel="person.identity"
                                 itemValue="id"
                                 path="assignee"/>
                </label>

            </p>

            <p>
                <label class="x-form-label" title="<s:message code="wizard.NewAppointmentWizard.tt.car.label"/>">
                    <span><s:message code="wizard.NewAppointmentWizard.tt.car.label"/></span>
                    <s:message code="wizard.NewAppointmentWizard.tt.car.placeholder" var="carPlaceholder"/>
                    <form:select id="${requestScope.formID}-car"
                                 htmlEscape="true"
                                 cssClass="x-input x-input-select"
                                 items="${requestScope.cars}"
                                 placeholder="${carPlaceholder}"
                                 itemLabel="licencePlate"
                                 itemValue="id"
                                 path="car"/>
                </label>
            </p>
        </fieldset>
        <div id="error-box" style="visibility: hidden">
            <form:errors path="*" element="span" htmlEscape="true" cssClass="error-entry"/>
        </div>
    </form:form>
</div>
<swf:getDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:getActions forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
<swf:renderErrors forState="${flowRequestContext.currentState}"/>