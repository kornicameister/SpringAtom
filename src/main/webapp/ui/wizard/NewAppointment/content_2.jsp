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
<%@ page import="org.agatom.springatom.server.model.types.appointment.AppointmentTaskType" %>
<%@ page import="org.springframework.web.bind.annotation.RequestMethod" %>

<div id="sa-wizard-step-body" class="content">
    <h2 class="stepTitle">
        <s:message code="wizard.newAppointment.title" var="title"/>
        <s:message code="wizard.step.title" arguments="${title},2" argumentSeparator=","/>
    </h2>

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               modelAttribute="newTask"
               method="<%=RequestMethod.POST.toString().toLowerCase()%>"
               title="${title}"
               cssClass="x-form">
        <fieldset>
            <legend><s:message code="wizard.newAppointment.tl.label"/></legend>
            <div class="x-multiple-input">
                <div class="x-inputs">
                    <ul id="tasks-container">
                        <s:message code="wizard.newAppointment.tl.taskType.placeholder" var="placeholder"/>
                        <li id="0" data-role="task">
                            <form:select id="${requestScope.formID}-type"
                                         items="<%= AppointmentTaskType.values()%>"
                                         placeholder="${placeholder}"
                                         cssClass="x-input x-input-select"
                                         path="type"/>
                            <form:textarea id="${requestScope.formID}-task"
                                           htmlEscape="true"
                                           cssClass="x-input"
                                           cols="15"
                                           rows="1"
                                           path="task"/>
                            <button id="form-add-row" name="_eventId_addTask" type="submit" data-role="mv-add" class="x-button x-button-add">
                                <i class="icon-plus icon-color-black"></i>
                            </button>
                        </li>
                        <c:forEach items="${requestScope.tasks}" var="task" varStatus="it" begin="0" step="1">
                            <li id="${it.index+1}">
                                <input type="text" value="${task.type}" id="${it.current}-type" readonly/>
                                <textarea id="${it.current}-task" cols="15" rows="1" readonly>${task.task}</textarea>
                                <button id="form-remove-row-${it.index}" name="_eventId_removeTask" type="submit" data-role="mv-add"
                                        class="x-button x-button-remove">
                                    <i class="icon-minus icon-color-black"></i>
                                </button>
                                <script type="text/javascript" id="mv-remove-script">
                                    Spring.addDecoration(new Spring.AjaxEventDecoration({
                                        elementId: 'form-remove-row-${it.index}',
                                        event    : 'onclick',
                                        formId   : '${requestScope.formID}',
                                        popup    : true,
                                        params   : {
                                            fragments: 'wiz.content',
                                            mode     : "embedded",
                                            rtai     : '${it.index}'
                                        }
                                    }));
                                </script>
                            </li>
                        </c:forEach>
                    </ul>
                    <script type="text/javascript" id="mv-add-flow-script">
                        Spring.addDecoration(new Spring.AjaxEventDecoration({
                            elementId: 'form-add-row',
                            event    : 'onclick',
                            formId   : '${requestScope.formID}',
                            popup    : true,
                            params   : {
                                fragments: 'wiz.content',
                                mode     : "embedded"
                            }
                        }));
                    </script>
                </div>
            </div>
        </fieldset>
        <div id="error-box" style="visibility: hidden">
            <form:errors path="*" element="span" htmlEscape="true" cssClass="error-entry"/>
        </div>
    </form:form>
</div>
<swf:applyActionsState forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
<swf:renderErrors forState="${flowRequestContext.currentState}"/>
