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

<div class="container-fluid">
	<div class="col-lg-9 col-lg-offset-1">
		<div id="form-container">
			<wizard-header title="{{title}}" steps="header"></wizard-header>
			<form name="wizardForm" ng-submit="hooks.submitForm()" class="form-horizontal" role="form">
				<wizard-nbox errors="errors" messages="messages"></wizard-nbox>
				<section id="form-view" class="well slide" ui-view></section>
			</form>
			<wizard-actions class="btn-toolbar"
			                actions="actions"
			                helper="actionHelper"
			                style="float:right"></wizard-actions>
		</div>
	</div>
	<pre class="col-lg-2" ng-if="debug">
		<code>
			{{formData | json}}
		</code>
	</pre>
</div>
