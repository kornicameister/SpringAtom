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

<div id="sa-wizard-step-body" class="content">
    <h2 class="stepTitle">
        <s:message code="wizard.newAppointment.title" var="title"/>
        <s:message code="wizard.step.title" arguments="${title},2" argumentSeparator=","/>
    </h2>

    <form id="${requestScope.formID}" action="${flowExecutionUrl}" class="x-form">
        <fieldset>
            <legend><s:message code="wizard.newAppointment.tl.label"/></legend>
            <div class="x-multiple-input">
                <div class="x-inputs">
                    <ul>
                        <li id="1" data-role="task">
                            <input name="type"
                                   type="text"
                                   placeholder="<s:message code="wizard.newAppointment.tl.taskType.placeholder"/>"
                                   list="tasks"
                                   class="x-input x-input-select"
                                   required>
                            <input name="task"
                                   type="text"
                                   placeholder="<s:message code="wizard.newAppointment.tl.taskDesc.placeholder"/>"
                                   class="x-input x-input-text">
                            <a href="#" data-role="mv-add" class="x-button x-button-add">
                                <i class="icon-plus icon-color-black"></i>
                            </a>
                            <a href="#" data-ajax="mv-remove" class="x-button x-button-remove">
                                <i class="icon-minus icon-color-black"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <datalist id="tasks"></datalist>
        </fieldset>
    </form>
</div>