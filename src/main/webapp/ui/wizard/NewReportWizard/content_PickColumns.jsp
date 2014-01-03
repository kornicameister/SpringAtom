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

<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="swf" tagdir="/WEB-INF/tags/swf" %>

<%@ page import="org.agatom.springatom.server.model.beans.report.SReport" %>
<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>

<div id="sa-wizard-step-body" class="content">
    <swf:renderStepTitle forState="${flowRequestContext.currentState}" cssClass="stepTitle"/>

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               commandName="<%=SReport.ENTITY_NAME%>"
               method="<%=RequestMethod.POST.toString().toLowerCase()%>"
               cssClass="x-form">
        <fieldset>
            <legend><s:message code="wizard.NewReportWizard.pickColumns.desc"/></legend>
            <jsp:useBean id="entityToColumn" scope="request" type="java.util.Map"/>
            <c:forEach items="${entityToColumn}" varStatus="loop" var="entry">
                <p>
                    <label class="x-form-label" title="<s:message code="wizard.NewReportWizard.entity.pickColumnsForEntity"/>">
                        <c:set var="reportableEntity" scope="page" value="${entry.key}"/>
                        <jsp:useBean id="reportableEntity" scope="page"
                                     class="org.agatom.springatom.web.rbuilder.bean.ReportableEntity"/>
                        <span>
                            <s:message code="wizard.NewReportWizard.pickColumns.forEntity" arguments="${reportableEntity.label}"/>
                        </span>
                        <form:select id="${requestScope.formID}-${reportableEntity.name}-column-${loop.index}"
                                     items="${entry.value}"
                                     cssClass="x-input x-input-select"
                                     itemLabel="columnName"
                                     itemValue="id"
                                     path="entities[${loop.index}].columns"/>
                    </label>
                </p>
                <script type="text/javascript" id="${requestScope.formID}-entity-decorator">
                    var el = $('#' + '${requestScope.formID}-${reportableEntity.name}-column-${loop.index}');
                    Spring.addDecoration(new Spring.ElementDecoration({
                        elementId  : el.attr('id'),
                        widgetType : 'dijit.form.MultiSelect',
                        widgetAttrs: {
                            class: el.attr('class')
                        }
                    }))
                </script>
            </c:forEach>
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