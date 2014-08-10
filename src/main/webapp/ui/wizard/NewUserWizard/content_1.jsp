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

<form:form id="${requestScope.formID}"
           action="${flowExecutionUrl}"
           commandName="user"
           method="<%=RequestMethod.POST.toString().toLowerCase()%>"
           cssClass="x-form">
	<swf:notificationsBox context="${flowRequestContext}" command="currentFormObject"/>
	<fieldset>
		<legend><s:message code="suser"/></legend>
		<p>
			<form:label path="username" cssClass="x-form-label">
				<span><s:message code="susercredentials.username"/></span>
				<form:input id="${requestScope.formID}-user-login"
				            htmlEscape="true"
				            cssClass="x-input"
				            path="username"/>
			</form:label>
		</p>

		<p>
			<form:label path="password" cssClass="x-form-label">
				<span><s:message code="susercredentials.password"/></span>
				<form:password id="${requestScope.formID}-user-password"
				               htmlEscape="true"
				               cssClass="x-input"
				               path="password"/>
			</form:label>
		</p>
	</fieldset>
	<fieldset>
		<legend><s:message code="sperson"/></legend>
		<p>
			<form:label path="person.firstName" cssClass="x-form-label">
				<span><s:message code="sperson.firstname"/></span>
				<form:input id="${requestScope.formID}-user-person-firstName"
				            htmlEscape="true"
				            cssClass="x-input"
				            path="person.firstName"/>
			</form:label>
		</p>

		<p>
			<form:label path="person.lastName" cssClass="x-form-label">
				<span><s:message code="sperson.lastname"/></span>
				<form:input id="${requestScope.formID}-user-person-firstName"
				            htmlEscape="true"
				            cssClass="x-input"
				            path="person.lastName"/>
			</form:label>
		</p>

		<p>
			<form:label path="person.primaryMail" cssClass="x-form-label">
				<span><s:message code="sperson.primarymail"/></span>
				<form:input id="${requestScope.formID}-user-person-primaryMail"
				            htmlEscape="true"
				            cssClass="x-input"
				            type="email"
				            path="person.primaryMail"/>
			</form:label>
		</p>
	</fieldset>
</form:form>
<swf:applyDynamicActions forState="${flowRequestContext.currentState}"/>
<swf:applyActionsVisibility forState="${flowRequestContext.currentState}"/>
<swf:applyStepsState forState="${flowRequestContext.currentState}"/>
