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

<swf:stepId flow="${flowRequestContext.activeFlow}" index="0" var="state1"/>
<swf:stepId flow="${flowRequestContext.activeFlow}" index="1" var="state2"/>
<swf:stepId flow="${flowRequestContext.activeFlow}" index="2" var="state3"/>
<swf:isStepRequired stateId="${state1}" var="state1Required"/>
<swf:isStepRequired stateId="${state2}" var="state2Required"/>
<swf:isStepRequired stateId="${state3}" var="state3Required"/>
<li>
    <span id="wiz-step-${state1}" class="disabled">
        <c:if test="${state1Required}">
            <i class="fa fa-star"></i>
        </c:if>
        <label class="stepNumber">1</label>
        <span class="stepDesc">
            <p><s:message code="wizard.step.label" arguments="1"/></p>
            <small><s:message code="wizard.newAppointment.step1.desc"/></small>
        </span>
    </span>
</li>
<li>
    <span id="wiz-step-${state2}" class="disabled">
        <c:if test="${state2Required}">
            <i class="fa fa-star"></i>
        </c:if>
        <label class="stepNumber">2</label>
        <span class="stepDesc">
            <p><s:message code="wizard.step.label" arguments="2"/></p>
            <small><s:message code="wizard.newAppointment.step2.desc"/></small>
        </span>
    </span>
</li>
<li>
    <span id="wiz-step-${state3}" class="disabled">
        <c:if test="${state3Required}">
            <i class="fa fa-star"></i>
        </c:if>
        <label class="stepNumber">3</label>
        <span class="stepDesc">
            <p><s:message code="wizard.step.label" arguments="3"/></p>
            <small><s:message code="wizard.newAppointment.step2.desc"/></small>
        </span>
    </span>
</li>