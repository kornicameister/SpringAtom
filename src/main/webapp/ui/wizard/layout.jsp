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

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="wizardID" scope="request" type="java.lang.String"/>
<jsp:useBean id="formID" scope="request" type="java.lang.String"/>

<jsp:useBean id="finishAction" scope="request" type="java.lang.String"/>
<jsp:useBean id="nextAction" scope="request" type="java.lang.String"/>
<jsp:useBean id="previousAction" scope="request" type="java.lang.String"/>
<jsp:useBean id="cancelAction" scope="request" type="java.lang.String"/>

<div id="${wizardID}" class="x-wizard">
    <div class="x-clear"></div>
    <ul id="${wizardID}-header" class="x-wizard-header">
        <%@ include file="header.jsp" %>
    </ul>
    <div id="${wizardID}-content" class="x-wizard-steps">
        <tiles:insertAttribute name="wiz.content" flush="true"/>
    </div>
    <div id="${wizardID}-actions" class="x-wizard-actions">
        <button id="${finishAction}" type="submit" name="${finishAction}" class="x-wizard-action buttonFinish"><s:message code="button.ok"/></button>
        <button id="${cancelAction}" type="submit" name="${cancelAction}" class="x-wizard-action buttonCancel"><s:message
                code="button.cancel"/></button>
        <button id="${nextAction}" type="submit" name="${nextAction}" class="x-wizard-action buttonNext"><s:message
                code="button.next.short"/></button>
        <button id="${previousAction}" type="submit" name="${previousAction}" class="x-wizard-action buttonPrevious"><s:message
                code="button.previous.short"/></button>
    </div>
    <div class="x-clear"></div>
</div>
<script type="text/javascript">
    SA.wizard.decorateWizard({
        wizardId       : '${wizardID}',
        wizardHeaderId : '${wizardID}-header',
        wizardContentId: '${wizardID}-content',
        wizardActionsId: '${wizardID}-actions'
    });
    SA.wizard.applyWebFlowDecorators(['${cancelAction}', '${finishAction}'], '${requestScope.formID}');
</script>
