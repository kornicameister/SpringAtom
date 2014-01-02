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

<%@ taglib prefix="swf" uri="http://www.example.org/sa/swf" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%----%>
<%@ tag description="Tags renders HTML element containing current step title" %>
<%@ attribute name="forState"
              required="true"
              rtexprvalue="true"
              type="org.springframework.webflow.definition.StateDefinition"
              description="Current state" %>
<%@ attribute name="cssClass"
              required="false"
              rtexprvalue="true"
              type="java.lang.String"
              description="CSS class to apply over title element" %>
<%----%>

<c:choose>
    <c:when test="${cssClass != null}">
        <h2 class="${cssClass}">
    </c:when>
    <c:otherwise>
        <h2>
    </c:otherwise>
</c:choose>
<swf:stateId var="stateId" state="${forState}"/>
<s:message code="wizard.${requestScope.wizardID}.title" var="title"/>
<s:message code="wizard.step.title" arguments="${title},${stateId}" argumentSeparator=","/>
</h2>