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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="context"
              type="org.springframework.webflow.execution.RequestContext"
              required="true"
              rtexprvalue="true" %>
<%@ attribute name="command" rtexprvalue="true" required="false" type="java.lang.String" %>

<c:if test="${command == null}">
	<c:set var="command" value="currentFormObject"/>
</c:if>

<c:set var="forState" value="${context.currentState}" scope="page"/>

<s:hasBindErrors name="${command}" htmlEscape="false">
	<s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(errors.allErrors)"
	        var="bindErrors"
	        javaScriptEscape="false"
	        htmlEscape="false"
	        scope="page"/>
</s:hasBindErrors>
<c:if test="${bindErrors == null}">
	<s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(new String[]{})"
	        javaScriptEscape="false"
	        htmlEscape="false"
	        var="bindErrors"/>
</c:if>
<form:errors id="formSpringErrors" element="div" cssClass="hidden" path="*"/>
<wizard-nbox
		errors="<c:out value="${bindErrors}"/>"
		messages="<s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(context.messageContext.allMessages)" htmlEscape="true" javaScriptEscape="true"/>"></wizard-nbox>
