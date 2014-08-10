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

<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>

<form:form id="${requestScope.formID}"
           action="${flowExecutionUrl}"
           commandName="commandBean"
           method="<%=RequestMethod.POST.toString().toLowerCase()%>"
           cssClass="x-form">
	<swf:notificationsBox context="${flowRequestContext}" command="currentFormObject"/>
	<fieldset>
		<legend><s:message code="wizard.NewReportWizard.pickColumns.desc"/></legend>
		<jsp:useBean id="entityToColumn" scope="request" type="java.util.Map"/>
		<jsp:useBean id="colToRenderProp" scope="request" type="java.util.Map"/>
		<c:forEach items="${entityToColumn}" varStatus="loop" var="entry">
			<p class="dataTables_wrapper">
				<label class="x-form-label"
				       title="<s:message code="wizard.NewReportWizard.entity.pickColumnsForEntity"/>">
					<c:set var="reportableEntity" scope="page" value="${entry.key}"/>
					<jsp:useBean id="reportableEntity" scope="page"
					             class="org.agatom.springatom.web.rbuilder.bean.RBuilderEntity"/>
                        <span><s:message code="wizard.NewReportWizard.pickColumns.forEntity"
                                         arguments="${reportableEntity.label}"/></span>
					<table class="dataTable">
						<thead>
						<tr>
							<th>
								Order <%-- localize --%>
							</th>
							<th>
								<s:message code="wizard.NewReportWizard.pickColumns.table.column.label"/>
							</th>
							<th>
								<s:message code="wizard.NewReportWizard.pickColumns.table.column.renderAs"/>
							</th>
							<th>
								Options <%-- localize --%>
							</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${entry.value}" var="column" varStatus="loopColumns">
							<c:set var="reportableColumn" scope="page" value="${column}"/>
							<jsp:useBean id="reportableColumn" scope="page"
							             class="org.agatom.springatom.web.rbuilder.bean.RBuilderColumn"/>
							<form:hidden id="col-${loop.index}-${loopColumns.index}-name"
							             path="entities[${loop.index}].columns[${loopColumns.index}].columnName"/>
							<tr id="row-${reportableColumn.id}">

								<td class="center"> <!-- order -->
									<form:hidden id="col-${loop.index}-${loopColumns.index}-order"
									             path="entities[${loop.index}].columns[${loopColumns.index}].order"/>
                                        <span id="col-${loop.index}-${loopColumns.index}-order-up"><i
		                                        class="fa fa-color-black fa-arrow-circle-o-up"></i></span>
                                        <span id="col-${loop.index}-${loopColumns.index}-order-down"><i
		                                        class="fa fa-color-black fa-arrow-circle-o-down"></i></span>
								</td>

								<td class="center"> <!-- label -->
									<form:input id="col-${loop.index}-${loopColumns.index}-label"
									            readonly="true"
									            path="entities[${loop.index}].columns[${loopColumns.index}].label"/>
								</td>

								<td class="center">  <!-- render as -->
									<s:eval expression="colToRenderProp[reportableColumn.id]" scope="page"
									        var="itemsRenderClass"/>
									<form:select id="col-${loop.index}-${loopColumns.index}-renderClass"
									             items="${itemsRenderClass}"
									             itemValue="targetClassName"
									             itemLabel="label"
									             cssClass="x-input x-input-select"
									             path="entities[${loop.index}].columns[${loopColumns.index}].renderClass"/>
								</td>

								<td class="center"> <!-- render excluded -->
									<p>
										<form:label
												path="entities[${loop.index}].columns[${loopColumns.index}].options.excluded">
											<s:message
													code="wizard.NewReportWizard.pickColumns.table.column.excluded"/>
										</form:label>
										<form:checkbox id="col-${loop.index}-${loopColumns.index}-excluded"
										               path="entities[${loop.index}].columns[${loopColumns.index}].options.excluded"/>
									</p>

									<p>
										<form:label
												path="entities[${loop.index}].columns[${loopColumns.index}].options.groupBy">
											<s:message
													code="wizard.NewReportWizard.pickColumns.table.column.groupBy"/>
										</form:label>
										<form:checkbox id="col-${loop.index}-${loopColumns.index}-groupBy"
										               path="entities[${loop.index}].columns[${loopColumns.index}].options.groupBy"/>
									</p>
								</td>
							</tr>
							<script type="text/javascript" id="editors-${column.id}">
								$(function () {
									Spring.addDecoration(new Spring.ElementDecoration({
										elementId : 'col-${loop.index}-${loopColumns.index}-excluded',
										widgetType: 'dijit.form.CheckBox'
									}));
									Spring.addDecoration(new Spring.ElementDecoration({
										elementId : 'col-${loop.index}-${loopColumns.index}-groupBy',
										widgetType: 'dijit.form.CheckBox'
									}));
									Spring.addDecoration(new Spring.ElementDecoration({
										elementId  : 'col-${loop.index}-${loopColumns.index}-order-up',
										widgetType : 'dijit.form.Button',
										widgetAttrs: {
											onClick: function () {
												var row = $('#' + 'row-${reportableColumn.id}');
												var input = $('#' + 'col-${loop.index}-${loopColumns.index}-order');
												SA.wizard.Helpers.NewReportWizard.onColumnOrderChange(row, input, 'up');
											}
										}
									}));
									Spring.addDecoration(new Spring.ElementDecoration({
										elementId  : 'col-${loop.index}-${loopColumns.index}-order-down',
										widgetType : 'dijit.form.Button',
										widgetAttrs: {
											onClick: function () {
												var row = $('#' + 'row-${reportableColumn.id}');
												var input = $('#' + 'col-${loop.index}-${loopColumns.index}-order');
												SA.wizard.Helpers.NewReportWizard.onColumnOrderChange(row, input, 'down');
											}
										}
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
</form:form>
<swf:applyDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:applyActionsVisibility forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
