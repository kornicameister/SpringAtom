<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                 ~
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

<div class="alert alert-info alert-dismissible" ng-class="{hidden: bindErrors.length === 0}" role="alert">
	<button type="button" class="close" ng-click="closeNBOX($event)">
		<span aria-hidden="true">&times;</span>
		<span class="sr-only"><s:message code="button.close"/></span>
	</button>
	<div class="panel-heading"><s:message code="wizard.notificationPromptMessage"/></div>
	<div class="list-group">
		<a href="#" class="list-group-item" ng-class="br.errClass" ng-repeat="br in bindErrors">
			<h6 class="list-group-item-heading">{{br.severity}}</h6>
				<span class="list-group-item-text">
					{{br.text}}
					<span ng-show="br.rejectedValue">{{br.rejectedValue}}</span>
				</span>
		</a>
	</div>
</div>
