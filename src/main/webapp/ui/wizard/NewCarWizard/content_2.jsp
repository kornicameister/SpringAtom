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

<s:eval expression="newCarWizard2.formObjectName" var="modelAttribute" scope="page"/>

<form:form id="${requestScope.formID}"
           action="${flowExecutionUrl}"
           commandName="${modelAttribute}"
           method="<%=RequestMethod.POST.toString().toLowerCase()%>"
           cssClass="form-horizontal"
           role="form">
	<swf:notificationsBox context="${flowRequestContext}" command="currentFormObject"/>
	<fieldset>
		<legend><s:message code="scar"/></legend>
		<div class="form-group">
			<label for="${requestScope.formID}-newBrandModel" class="col-sm-2 control-label">
				<span>${requestScope.newBrandModelMsg}</span>
			</label>

			<div class="col-sm-10">
				<input id="${requestScope.formID}-newBrandModel" name="newBrandModel" type="checkbox"
				       class="checkbox input-lg">
			</div>
		</div>
		<div class="form-group">
			<div class="row">
				<form:label path="brand" cssClass="col-sm-2 control-label">
					<span><s:message code="scarmastermanufacturingdata.brand"/></span>
				</form:label>
				<div class="col-sm-10">
					<form:select id="${requestScope.formID}-brand"
					             htmlEscape="true"
					             cssClass="form-control"
					             items="${requestScope.brands}"
					             disabled="true"
					             path="brand"/>
				</div>
			</div>
			<div class="row">
				<form:label path="model" cssClass="col-sm-2 control-label">
					<span><s:message code="scarmastermanufacturingdata.model"/></span>
				</form:label>
				<div class="col-sm-10">
					<form:select id="${requestScope.formID}-model"
					             htmlEscape="true"
					             cssClass="form-control"
					             items="${requestScope.models}"
					             disabled="true"
					             path="model"/>
				</div>
			</div>

			<script type="text/javascript">
				$(function () {
					var model = $('#' + '${requestScope.formID}-model'),
							brand = $('#' + '${requestScope.formID}-brand'),
							carMaster = $('#' + '${requestScope.formID}-carMaster'),
							newBrandModelCB = $('#' + '${requestScope.formID}-newBrandModel');

					newBrandModelCB.change(function () {
						if ($(this).is(':checked')) {
							model.attr('disabled', false);
							brand.attr('disabled', false);
							carMaster.attr('disabled', true);
						} else {
							model.attr('disabled', true);
							brand.attr('disabled', true);
							carMaster.attr('disabled', false);
						}
					});
				});
			</script>
		</div>
		<div class="form-group">
			<form:label path="carMaster" cssClass="col-sm-2 control-label">
				<span><s:message code="scarmaster.manufacturingdata"/></span>
			</form:label>
			<div class="col-sm-10">
				<form:select id="${requestScope.formID}-carMaster"
				             htmlEscape="true"
				             cssClass="form-control"
				             items="${requestScope.carMasters}"
				             itemValue="id"
				             itemLabel="manufacturingData.identity"
				             path="carMaster"/>
			</div>
		</div>
		<div class="form-group">
			<form:label path="licencePlate" cssClass="col-sm-2 control-label">
				<span><s:message code="scar.licenceplate"/></span>
			</form:label>
			<div class="col-sm-10">
				<form:input id="${requestScope.formID}-licencePlate"
				            htmlEscape="true"
				            cssClass="form-control"
				            path="licencePlate"/>
			</div>
		</div>
		<div class="form-group">
			<form:label path="fuelType" cssClass="col-sm-2 control-label">
				<span><s:message code="scar.fueltype"/></span>
			</form:label>
			<div class="col-sm-10">
				<form:select id="${requestScope.formID}-carMaster-fuelType"
				             htmlEscape="true"
				             cssClass="form-control"
				             items="${requestScope.fuelTypes}"
				             itemLabel="label"
				             itemValue="value"
				             path="fuelType"/>
			</div>
		</div>
		<div class="form-group">
			<form:label path="yearOfProduction" cssClass="col-sm-2 control-label">
				<span><s:message code="scar.yearofproduction"/></span>
			</form:label>
			<div class="col-sm-10">
				<form:select id="${requestScope.formID}-yearProduction"
				             htmlEscape="true"
				             cssClass="form-control"
				             items="${requestScope.vinNumberData.years}"
				             path="yearOfProduction"/>
			</div>
		</div>
	</fieldset>
	<fieldset>
		<legend><s:message code="scar.owner"/></legend>
		<div class="form-group">
			<form:label path="owner" cssClass="col-sm-2 control-label">
				<span><s:message code="scar.owner"/></span>
			</form:label>
			<div class="col-sm-10">
				<form:select id="${requestScope.formID}-owner"
				             htmlEscape="true"
				             cssClass="form-control"
				             items="${requestScope.owners}"
				             itemLabel="ownerIdentity"
				             itemValue="ownerId"
				             path="owner"/>
			</div>
		</div>
	</fieldset>
</form:form>
<swf:applyDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:applyActionsVisibility forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
