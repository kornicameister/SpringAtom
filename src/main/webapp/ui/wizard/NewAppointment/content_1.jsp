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

<div id="sa-wizard-step-body" class="content">
    <h2 class="stepTitle">
        <s:message code="wizard.newAppointment.title" var="title"/>
        <s:message code="wizard.step.title" arguments="${title},1" argumentSeparator=","/>
    </h2>

    <form:form id="${requestScope.formID}"
               action="${flowExecutionUrl}"
               commandName="appointment"
               method="post"
               title="${title}"
               cssClass="x-form">
        <fieldset>
            <legend><s:message code="wizard.newAppointment.tf.label"/></legend>
            <p>
                <label class="x-form-label" title="<s:message code="wizard.newAppointment.tf.begin.label"/>">
                    <span><s:message code="wizard.newAppointment.tf.begin.label"/></span>
                    <input class="x-input" id="begin-date" name="begin-date" type="date" autofocus required>
                    <input class="x-input" id="begin-time" name="begin-time" type="time" required>
                </label>
            </p>

            <p>
                <label class="x-form-label" title="<s:message code="wizard.newAppointment.tf.end.label"/>">
                    <span><s:message code="wizard.newAppointment.tf.end.label"/></span>
                    <input class="x-input" id="end-date" name="end-date" type="date" required>
                    <input class="x-input" id="end-time" name="end-time" type="time" required>
                </label>
            </p>
        </fieldset>
        <fieldset>
            <legend><s:message code="wizard.newAppointment.tt.label"/></legend>
            <p>
                <label class="x-form-label" title="<s:message code="wizard.newAppointment.tt.reporter.label"/>">
                    <span><s:message code="wizard.newAppointment.tt.reporter.label"/></span>
                    <security:authorize access="isFullyAuthenticated()" var="isAuthenticated"/>
                    <security:authorize access="hasRole('ROLE_BOSS')" var="isBoss"/>
                    <c:if test="${isAuthenticated}">
                        <c:choose>
                            <c:when test="${isBoss}">
                                <input name="reporter" class="x-input x-input-select"
                                       placeholder="<s:message code="wizard.newAppointment.tt.reporter.placeholder"/>"
                                       required/>
                            </c:when>
                            <c:otherwise>
                                <security:authentication property="principal.person.information" var="ppi"/>
                                <s:bind path="${ppi}" htmlEscape="true">
                                    <input name="reporter"
                                           class="x-input x-input-readonly"
                                           value="<s:transform value="${status.value}"/>" readonly>
                                </s:bind>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </label>
            </p>

            <p>
                <label class="x-form-label" title="<s:message code="wizard.newAppointment.tt.assignee.label"/>">
                    <span><s:message code="wizard.newAppointment.tt.assignee.label"/></span>
                    <input name="assignee" class="x-input x-input-select"
                           placeholder="<s:message code="wizard.newAppointment.tt.assignee.placeholder"/>" required/>
                </label>

            </p>

            <p>
                <label class="x-form-label" title="<s:message code="wizard.newAppointment.tt.car.label"/>">
                    <span><s:message code="wizard.newAppointment.tt.car.label"/></span>
                    <input name="car" class="x-input x-input-select"
                           placeholder="<s:message code="wizard.newAppointment.tt.car.placeholder"/>" required/>
                </label>
            </p>
        </fieldset>
    </form:form>
</div>