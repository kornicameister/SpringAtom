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
<%@ taglib prefix="swf2" uri="/WEB-INF/tags/sa/swf.tld" %>

<nav class="navbar navbar-inverse" role="navigation">
	<div class="collapse navbar-collapse" id="{{wizardId}}-collapse">
		<ul class="nav navbar-nav navbar-left">
			<li ng-repeat="step in steps" class="small">
				<a ng-class="{active:step.active}"
				   title="{{step.label}}">
					<i ng-show="step.required" class="glyphicon glyphicon-asterisk"></i>
					<span class="stepIndex">{{step.index + 1}}</span>&nbsp;{{step.label}}
				</a>
			</li>
		</ul>
	</div>
</nav>
<h4 class="center-block">
	{{wizardTitle}}
</h4>


