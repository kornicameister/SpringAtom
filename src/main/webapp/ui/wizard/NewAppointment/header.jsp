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

<%-- todo processing which steps is currently active and so on --%>
<li>
    <a href="#step-1" class="selected">
        <label class="stepNumber">1</label>
        <span class="stepDesc">
            <p><s:message code="wizard.step.label" arguments="1"/></p>
            <small><s:message code="wizard.newAppointment.step1.desc"/></small>
        </span>
    </a>
</li>
<li>
    <a href="#step-2" class="disabled">
        <label class="stepNumber">2</label>
        <span class="stepDesc">
            <p><s:message code="wizard.step.label" arguments="2"/></p>
            <small><s:message code="wizard.newAppointment.step2.desc"/></small>
        </span>
    </a>
</li>
<li>
    <a href="#step-3" class="disabled">
        <label class="stepNumber">3</label>
        <span class="stepDesc">
            <p><s:message code="wizard.step.label" arguments="3"/></p>
            <small><s:message code="wizard.newAppointment.step2.desc"/></small>
        </span>
    </a>
</li>