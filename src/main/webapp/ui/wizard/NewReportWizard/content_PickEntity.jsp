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
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="swf" tagdir="/WEB-INF/tags/swf" %>

<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>

<%--
    PickEntity form is designated to handle selecting several entities.
    Additional JavaScript code is designed to recalculate these entities
    which can not be linked with the one just selected
--%>

<div id="sa-wizard-step-body" class="x-wizard-content">
    <swf:renderStepTitle forState="${flowRequestContext.currentState}" cssClass="stepTitle"/>

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               commandName="commandBean"
               method="<%=RequestMethod.POST.toString().toLowerCase()%>"
               cssClass="x-form">
        <fieldset>
            <legend><s:message code="wizard.NewReportWizard.entity.pickTables"/></legend>
            <p>
                <label class="x-form-label" title="<s:message code="wizard.NewReportWizard.entity.pickTables"/>">
                    <span><s:message code="wizard.NewReportWizard.entity.pickTables"/></span>
                    <form:select id="${requestScope.formID}-entity"
                                 items="${requestScope.entities}"
                                 cssClass="x-input x-input-select"
                                 itemLabel="label"
                                 itemValue="id"
                                 path="entities"/>
                </label>
            </p>
        </fieldset>
        <swf:notificationsBox context="${flowRequestContext}"/>
    </form:form>
    <script type="text/javascript" id="${requestScope.formID}-entity-decorator">
        $(function () {
            SA.wizard.Helpers.NewReportWizard.setEntities('<s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(requestScope.associationInformation)" htmlEscape="false" javaScriptEscape="false"/>');
            var el = $('#' + '${requestScope.formID}-entity');
            Spring.addDecoration(new Spring.ElementDecoration({
                elementId  : el.attr('id'),
                widgetType : 'dijit.form.MultiSelect',
                widgetAttrs: {
                    class     : el.attr('class'),
                    onChange  : SA.wizard.Helpers.NewReportWizard.onEntityPickRecalculateAssociation,
                    onDblClick: SA.wizard.Helpers.NewReportWizard.onEntityPickResetAll
                }
            }));

        });
    </script>
</div>
<swf:getDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:getActions forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>