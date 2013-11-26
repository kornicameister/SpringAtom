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

<s:message code="button.next.short" var="nextButton"/>
<s:message code="button.previous.short" var="previousButton"/>
<s:message code="button.ok" var="finishButton"/>

<div id="sa-wizard-step-body" class="content">
    <h2 class="stepTitle">
        <s:message code="wizard.newAppointment.title" var="title"/>
        <s:message code="wizard.step.title" arguments="${title},3" argumentSeparator=","/>
    </h2>

    <form id="${requestScope.formID}" action="${flowExecutionUrl}" class="x-form">
    </form>
</div>
<%@ taglib prefix="swf" uri="http://www.example.org/sa/swf" %>
<script>
    $(function () {
        <swf:transitions state="${flowRequestContext.currentState}" var="transitions"/>
        SA.wizard.applyActionVisibility({
            container: 'div.x-wizard-actions',
            selector : 'button',
            actions  : JSON.parse('${transitions}'),
            first    : true
        });

        $('#' + '${requestScope.formID}' + '-' + '${flowRequestContext.currentState.id}')
                .addClass('selected')
                .removeClass('disabled');
    });
</script>