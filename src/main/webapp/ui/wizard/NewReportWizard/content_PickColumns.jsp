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

<%@ page import="org.agatom.springatom.web.rbuilder.ReportConfiguration" %>
<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="org.springframework.util.StringUtils" %>
<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>

<div id="sa-wizard-step-body" class="content">
    <swf:renderStepTitle forState="${flowRequestContext.currentState}" cssClass="stepTitle"/>

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               commandName="<%=StringUtils.uncapitalize(ClassUtils.getShortName(ReportConfiguration.class))%>"
               method="<%=RequestMethod.POST.toString().toLowerCase()%>"
               cssClass="x-form">
        <fieldset>
            <legend><s:message code="wizard.NewReportWizard.pickColumns.desc"/></legend>
            <jsp:useBean id="entityToColumn" scope="request" type="java.util.Map"/>
            <jsp:useBean id="colToRenderProp" scope="request" type="java.util.Map"/>
            <c:forEach items="${entityToColumn}" varStatus="loop" var="entry">
                <p class="dataTables_wrapper">
                    <label class="x-form-label" title="<s:message code="wizard.NewReportWizard.entity.pickColumnsForEntity"/>">
                        <c:set var="reportableEntity" scope="page" value="${entry.key}"/>
                        <jsp:useBean id="reportableEntity" scope="page" class="org.agatom.springatom.web.rbuilder.bean.RBuilderEntity"/>
                        <span><s:message code="wizard.NewReportWizard.pickColumns.forEntity" arguments="${reportableEntity.label}"/></span>
                        <table class="dataTable">
                            <thead>
                            <tr>
                                <th>
                                    <s:message code="wizard.NewReportWizard.pickColumns.table.column.label"/>
                                </th>
                                <th>
                                    <s:message code="wizard.NewReportWizard.pickColumns.table.column.renderAs"/>
                                </th>
                                <th>
                                    <s:message code="wizard.NewReportWizard.pickColumns.table.column.excluded"/>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${entry.value}" var="column" varStatus="loopColumns">
                                <c:set var="reportableColumn" scope="page" value="${column}"/>
                                <jsp:useBean id="reportableColumn" scope="page" class="org.agatom.springatom.web.rbuilder.bean.RBuilderColumn"/>
                                <form:hidden id="col-${loop.index}-${loopColumns.index}-name"
                                             path="entities[${loop.index}].columns[${loopColumns.index}].columnName"/>
                                <tr>

                                    <td class="center"> <!-- label -->
                                        <form:input id="col-${loop.index}-${loopColumns.index}-label"
                                                    readonly="true"
                                                    path="entities[${loop.index}].columns[${loopColumns.index}].label"/>
                                    </td>

                                    <td>  <!-- render as -->
                                        <s:eval expression="colToRenderProp[reportableColumn.id]" scope="page" var="itemsRenderClass"/>
                                        <form:select id="col-${loop.index}-${loopColumns.index}-renderClass"
                                                     items="${itemsRenderClass}"
                                                     itemValue="targetClassName"
                                                     itemLabel="label"
                                                     cssClass="x-input x-input-select"
                                                     path="entities[${loop.index}].columns[${loopColumns.index}].renderClass"/>
                                    </td>

                                    <td> <!-- render excluded -->
                                        <form:checkbox id="col-${loop.index}-${loopColumns.index}-excluded"
                                                       path="entities[${loop.index}].columns[${loopColumns.index}].excluded"/>
                                    </td>
                                </tr>
                                <script type="text/javascript" id="editors-${column.id}">
                                    $(function () {
                                        Spring.addDecoration(new Spring.ElementDecoration({
                                            elementId : 'col-${loop.index}-${loopColumns.index}-excluded',
                                            widgetType: 'dijit.form.CheckBox'
                                        }));
                                    })
                                </script>
                            </c:forEach>
                            </tbody>
                        </table>
                    </label>
                </p>
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