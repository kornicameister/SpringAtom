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
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="wizardID" scope="request" type="java.lang.String"/>
<jsp:useBean id="formID" scope="request" type="java.lang.String"/>
<jsp:useBean id="finishAction" scope="request" type="java.lang.String"/>
<jsp:useBean id="nextAction" scope="request" type="java.lang.String"/>
<jsp:useBean id="previousAction" scope="request" type="java.lang.String"/>
<jsp:useBean id="cancelAction" scope="request" type="java.lang.String"/>

<div id="${wizardID}" class="x-wizard">
    <div class="x-clear"></div>
    <ul class="x-wizard-header">
        <tiles:insertAttribute name="wiz.header" ignore="false" flush="false"/>
    </ul>
    <div class="x-wizard-steps">
        <tiles:insertAttribute name="wiz.content" ignore="false" flush="true"/>
    </div>
    <div class="x-wizard-actions">
        <button id="${finishAction}" type="submit" name="${finishAction}" class="buttonFinish"><s:message key="button.ok"/></button>
        <button id="${cancelAction}" type="submit" name="${cancelAction}" class="buttonCancel"><s:message key="button.cancel"/></button>
        <button id="${nextAction}" type="submit" name="${nextAction}" class="buttonNext"><s:message key="button.next.short"/></button>
        <button id="${previousAction}" type="submit" name="${previousAction}" class="buttonPrevious"><s:message key="button.previous.short"/></button>
    </div>
    <div class="x-clear"></div>
</div>
<script type="text/javascript">
    var el = ['${nextAction}', '${previousAction}'];
    $.each(el, function (index, val) {
        Spring.addDecoration(new Spring.AjaxEventDecoration({
            elementId: val,
            event    : 'onclick',
            formId   : '${formID}',
            popup    : true,
            params   : {
                fragments: 'wiz.content',
                mode     : "embedded"
            }
        }));
    });
    Spring.addDecoration(new Spring.AjaxEventDecoration({
        elementId: '${cancelAction}',
        event    : 'onclick',
        formId   : '${formID}'
    }));
    Spring.addDecoration(new Spring.AjaxEventDecoration({
        elementId: '${finishAction}',
        event    : 'onclick',
        formId   : '${formID}'
    }));
</script>
