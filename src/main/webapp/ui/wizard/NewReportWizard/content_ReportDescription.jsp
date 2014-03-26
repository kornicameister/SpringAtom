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

<div id="sa-wizard-step-body" class="x-wizard-content">
    <swf:renderStepTitle forState="${flowRequestContext.currentState}" cssClass="stepTitle"/>

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               commandName="commandBean"
               method="<%=RequestMethod.POST.toString().toLowerCase()%>"
               cssClass="x-form">
        <fieldset>
            <legend><s:message code="wizard.NewReportWizard.entity.describeReport"/></legend>
            <p>
                <label class="x-form-label" title="<s:message code="wizard.NewReportWizard.report.title"/>">
                    <span><s:message code="wizard.NewReportWizard.report.title"/></span>
                    <form:input id="${requestScope.formID}-title" htmlEscape="true" cssClass="x-input" path="title"/>
                </label>
                <script type="text/javascript">
                    var el = $('#' + '${requestScope.formID}-title');
                    Spring.addDecoration(new Spring.ElementDecoration({
                        elementId  : el.attr('id'),
                        widgetType : 'dijit.form.TextBox',
                        widgetAttrs: {
                            class: el.attr('class')
                        }
                    }))
                </script>
                <label class="x-form-label" title="<s:message code="wizard.NewReportWizard.report.subtitle"/>">
                    <span><s:message code="wizard.NewReportWizard.report.subtitle"/></span>
                    <form:input id="${requestScope.formID}-subtitle" htmlEscape="true" cssClass="x-input"
                                path="subtitle"/>
                </label>
                <script type="text/javascript">
                    var el = $('#' + '${requestScope.formID}-subtitle');
                    Spring.addDecoration(new Spring.ElementDecoration({
                        elementId  : el.attr('id'),
                        widgetType : 'dijit.form.TextBox',
                        widgetAttrs: {
                            class: el.attr('class')
                        }
                    }))
                </script>
                <label class="x-form-label" title="<s:message code="wizard.NewReportWizard.report.description"/>">
                    <span><s:message code="wizard.NewReportWizard.report.description"/></span>
                    <form:textarea id="${requestScope.formID}-description" htmlEscape="true" cssClass="x-input"
                                   path="description"/>
                </label>
                <script type="text/javascript">
                    var el = $('#' + '${requestScope.formID}-description');
                    Spring.addDecoration(new Spring.ElementDecoration({
                        elementId  : el.attr('id'),
                        widgetType : 'dijit.form.Textarea',
                        widgetAttrs: {
                            class: el.attr('class')
                        }
                    }))
                </script>
            </p>
        </fieldset>
        <swf:notificationsBox context="${flowRequestContext}"/>
    </form:form>
</div>
<swf:getDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:getActions forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>