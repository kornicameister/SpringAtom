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
<%@ taglib prefix="swf2" uri="/WEB-INF/tags/sa/swf.tld" %>

<jsp:useBean id="wizardID" scope="request" type="java.lang.String"/>
<jsp:useBean id="formID" scope="request" type="java.lang.String"/>

<div id="${wizardID}" class="modal-dialog modal-lg" ng-controller="WizController as wiz">
	<div class="modal-content">

		<!-- closing button -->
		<button type="button" class="close" data-dismiss="modal">
			<span aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></span>
			<span class="sr-only"><s:message code="button.cancel"/></span>
		</button>
		<!-- closing button -->

		<!-- the header -->
		<swf2:statesNg flow="${flowRequestContext.activeFlow}" wizardId="${wizardID}" var="ngModel"/>
		<div class="modal-header">
			<wizard-header title="<s:message code="wizard.${requestScope.wizardID}.title"/>"
			               wizard="${wizardID}"
			               steps="<s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(ngModel)" htmlEscape="true" javaScriptEscape="true"/>"></wizard-header>
		</div>
		<!-- the header -->

		<!-- reloadable view -->
		<div id="${wizardID}-content" class="modal-body">
			<div swf-view>
				<tiles:insertAttribute name="wiz.content" flush="true"/>
			</div>
		</div>
		<!-- reloadable view -->

		<!-- footer -->
		<div class="modal-footer">
			<wizard-actions wizard="${wizardID}"
			                actions="<s:eval expression="@jackson2ObjectFactoryBean.writeValueAsString(requestScope.wizardActions)" htmlEscape="true" javaScriptEscape="true"/>"></wizard-actions>
		</div>
		<!-- footer -->

	</div>
</div>
