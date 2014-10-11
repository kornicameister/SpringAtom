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

<%--suppress XmlDuplicatedId --%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<li class="dropdown">
	<a class="dropdown-toggle" role="button" data-toggle="dropdown">
		<i class="{{btnClass}}"></i>&nbsp;{{btnLabel}}&nbsp;
		<span class="caret"></span>
	</a>
	<ul class="dropdown-menu dropdown-menu-right" ng-show="data" role="menu">
		<dropdown-menu-item ng-repeat="action in data.getModel()" ng-switch="action.mode" ng-transclude>
			<li role="presentation" ng-switch-when="linkAction">
				<a id="{{action.id}}" href="{{action.url}}" tabindex="-1" role="menuitem">
					<i ng-show="action.iconClass" ng-class="action.iconClass"></i>{{action.label}}
				</a>
			</li>
			<li role="presentation" class="divider" ng-switch-when="separatorAction"></li>
			<span ng-switch-when="actionModel">
				<li role="presentation" class="dropdown-header">{{action.label}}</li>
				<li role="presentation" ng-repeat="am in action.content" ng-switch="am.mode">
					<a id="{{am.id}}" href="{{am.url}}" tabindex="-1" role="menuitem" ng-switch-when="linkAction">
						<i ng-show="am.iconClass" ng-class="am.iconClass"></i>{{am.label}}
					</a>
					<a id="{{am.id}}"
					   tabindex="-1"
					   role="menuitem"
					   ng-switch-when="wizardAction"
					   ui-sref="{{am.name}}">
						<i ng-show="am.iconClass" ng-class="am.iconClass"></i>
						<i ng-show="!am.iconClass" class="glyphicon glyphicon-tint"></i>
						{{am.label}}
					</a>
				</li>
				<li role="presentation" class="divider"></li>
			</span>
		</dropdown-menu-item>
	</ul>
</li>
