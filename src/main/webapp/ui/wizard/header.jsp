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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="swf" tagdir="/WEB-INF/tags/swf" %>
<%@ taglib prefix="swf2" uri="/WEB-INF/tags/sa/swf.tld" %>

<swf2:states flow="${flowRequestContext.activeFlow}">
    <jsp:useBean id="states" scope="page" type="java.util.List"/>
    <c:forEach items="${states}" var="state" varStatus="loop">
        <li>
            <span id="wiz-step-${state}" class="disabled">
                <swf:isStepRequired stateId="${state}" var="stateRequired"/>
                <c:if test="${stateRequired}">
                    <i class="fa fa-star"></i>
                </c:if>
                <label class="stepNumber">${loop.index + 1}</label>
                <span class="stepDesc">
                    <p><s:message code="wizard.step.label" arguments="${loop.index + 1}"/></p>
                    <small><s:message code="wizard.${requestScope.wizardID}.${state}.desc"/></small>
                </span>
            </span>
        </li>
    </c:forEach>
</swf2:states>

