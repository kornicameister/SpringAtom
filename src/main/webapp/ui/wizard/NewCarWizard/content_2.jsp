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

<div id="sa-wizard-step-body" class="x-wizard-content">
    <swf:renderStepTitle forState="${flowRequestContext.currentState}" cssClass="stepTitle"/>
    <s:eval expression="newCarWizard2.formObjectName" var="modelAttribute" scope="page"/>

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               commandName="${modelAttribute}"
               method="<%=RequestMethod.POST.toString().toLowerCase()%>"
               cssClass="x-form">
        <fieldset>
            <legend><s:message code="scar"/></legend>
            <p>
                <label for="${requestScope.formID}-newBrandModel" class="x-form-label">
                    <span>${requestScope.newBrandModelMsg}</span>
                    <input id="${requestScope.formID}-newBrandModel" name="newBrandModel" type="checkbox">
                </label>
            </p>

            <p>
                <form:label path="brand" cssClass="x-form-label">
                    <span><s:message code="scarmastermanufacturingdata.brand"/></span>
                    <form:select id="${requestScope.formID}-brand"
                                 htmlEscape="true"
                                 cssClass="x-input"
                                 items="${requestScope.brands}"
                                 disabled="true"
                                 path="brand"/>
                </form:label>
                <form:label path="model" cssClass="x-form-label">
                    <span><s:message code="scarmastermanufacturingdata.brand"/></span>
                    <form:select id="${requestScope.formID}-model"
                                 htmlEscape="true"
                                 cssClass="x-input"
                                 items="${requestScope.models}"
                                 disabled="true"
                                 path="model"/>
                </form:label>
            </p>

            <p>
                <form:label path="carMaster" cssClass="x-form-label">
                    <span><s:message code="scarmaster.manufacturingdata"/></span>
                    <form:select id="${requestScope.formID}-carMaster"
                                 htmlEscape="true"
                                 cssClass="x-input"
                                 items="${requestScope.carMasters}"
                                 itemValue="id"
                                 itemLabel="manufacturingData.identity"
                                 path="carMaster"/>
                </form:label>
            </p>
        </fieldset>
        <fieldset>
            <p>
                <form:label path="licencePlate" cssClass="x-form-label">
                    <span><s:message code="scar.licenceplate"/></span>
                    <form:input id="${requestScope.formID}-licencePlate"
                                htmlEscape="true"
                                cssClass="x-input"
                                path="licencePlate"/>
                </form:label>
            </p>
        </fieldset>
        <fieldset>
            <legend><s:message code="scar.owner"/></legend>
            <p>
                <form:label path="owner" cssClass="x-form-label">
                    <span><s:message code="scar.owner"/></span>
                    <form:select id="${requestScope.formID}-owner"
                                 htmlEscape="true"
                                 cssClass="x-input x-input-select"
                                 items="${requestScope.owners}"
                                 itemLabel="ownerIdentity"
                                 itemValue="ownerId"
                                 path="owner"/>
                </form:label>
            </p>
        </fieldset>
        <swf:notificationsBox context="${flowRequestContext}" command="currentFormObject"/>
    </form:form>

    <script type="text/javascript" id="${requestScope.formID}-entity-decorator">
        $(function () {
            var owners = <s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(requestScope.owners)" htmlEscape="false" javaScriptEscape="false"/>;
            var existingCarsMsgHolder = $('#existingCarsMsgHolder');

            var $1 = $('#' + '${requestScope.formID}-owner');
            var $2 = $('#' + '${requestScope.formID}-licencePlate');
            var $4 = $('#' + '${requestScope.formID}-model');
            var $5 = $('#' + '${requestScope.formID}-brand');
            var $6 = $('#' + '${requestScope.formID}-newBrandModel');
            var $7 = $('#' + '${requestScope.formID}-carMaster');

            $6.change(function () {
                console.log('Switching between newBrandModel/persistedBrandModel mode');
                var checkBox = $(this);
                if (checkBox.is(':checked')) {
                    dijit.byId($4.attr('id')).set('disabled', false);
                    dijit.byId($5.attr('id')).set('disabled', false);
                    dijit.byId($7.attr('id')).set('disabled', true);
                } else {
                    dijit.byId($4.attr('id')).set('disabled', true);
                    dijit.byId($5.attr('id')).set('disabled', true);
                    dijit.byId($7.attr('id')).set('disabled', false);
                }
            });

            Spring.addDecoration(new Spring.ElementDecoration({
                elementId  : $1.attr('id'),
                widgetType : 'dijit.form.Select',
                widgetAttrs: {
                    sortByLabel: true,
                    style      : 'min-width:200px;height:15px',
                    onChange   : function (evt) {
                        SA.wizard.NewUserWizard.onOwnerChange.apply(this, [evt, owners]);
                    }
                }
            }));
            Spring.addDecoration(new Spring.ElementDecoration({
                elementId  : $2.attr('id'),
                widgetType : 'dijit.form.TextBox',
                widgetAttrs: {
                    class    : $2.attr('class'),
                    required : true,
                    uppercase: true,
                    trim     : true
                }
            }));
            Spring.addDecoration(new Spring.ElementDecoration({
                elementId  : $4.attr('id'),
                widgetType : 'dijit.form.ComboBox',
                widgetAttrs: {
                    style     : 'width:200px;height:15px',
                    propercase: true,
                    trim      : true
                }
            }));
            Spring.addDecoration(new Spring.ElementDecoration({
                elementId  : $5.attr('id'),
                widgetType : 'dijit.form.ComboBox',
                widgetAttrs: {
                    style     : 'width:200px;height:15px',
                    propercase: true,
                    trim      : true
                }
            }));
            Spring.addDecoration(new Spring.ElementDecoration({
                elementId  : $7.attr('id'),
                widgetType : 'dijit.form.Select',
                widgetAttrs: {
                    style: 'width:200px;height:15px'
                }
            }));
        });
    </script>
</div>
<swf:getDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:getActions forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>