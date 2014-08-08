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

<%----%>
<%@ tag description="This tags simplifies reapplying visiblity of actions in the wizard" %>
<%@ attribute name="forState"
              required="true"
              rtexprvalue="true"
              type="org.springframework.webflow.definition.StateDefinition"
              description="Current state" %>
<%----%>

<%@ taglib prefix="swf" uri="http://www.example.org/sa/swf" %>
<swf:transitions state="${forState}" var="transitions"/>
<script type="text/javascript" id="${flowRequestContext.currentState.id}-visibility-applier-script">
	var scope = angular.element('wizard-actions').scope(),
			checkAndExecuteIfDefined = function () {
				if (!angular.isDefined(scope.applyActionVisibility)) {
					setTimeout(checkAndExecuteIfDefined, 100);
				} else {
					scope.applyActionVisibility(JSON.parse('${transitions}'));
				}
			};
	checkAndExecuteIfDefined();
</script>
