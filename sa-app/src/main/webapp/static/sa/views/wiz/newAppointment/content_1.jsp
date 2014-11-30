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

<div class="form-group row">
	<label class="col-sm-2 control-label">
		<span>{{labels.begin}}</span>
	</label>

	<div class="col-sm-9">
		<div class="input-group">
			<input type="date" name="beginDate" class="form-control" ng-model="formData.beginDate" ng-required="true">
			<span class="input-group-addon">/</span>
			<input type="time" name="beginTime" class="form-control" ng-model="formData.beginTime" ng-required="true">
		</div>
	</div>
</div>

<div class="form-group row">
	<label class="col-sm-2 control-label">
		<span>{{labels.end}}</span>
	</label>

	<div class="col-sm-9">
		<div class="input-group">
			<input type="date" name="endDate" class="form-control" ng-model="formData.endDate" ng-required="true">
			<span class="input-group-addon">/</span>
			<input type="time" name="endTime" class="form-control" ng-model="formData.endTime" ng-required="true">
		</div>
	</div>
</div>

<div class="form-group row">
	<label for="reporter" class="col-sm-2 control-label">
		<span>{{labels.reporter}}</span>
	</label>

	<div class="col-sm-9">
		<select id="reporter"
		        name="reporter"
		        class="form-control"
		        placeholder="{{labels.reporter}}"
		        ng-model="formData.reporter"
		        ng-options="ft.label for ft in options.reporters"
		        ng-required="true"></select>
	</div>
</div>

<div class="form-group row">
	<label class="col-sm-2 control-label">
		<span>{{labels.assignee}}</span>
	</label>

	<div class="col-sm-9">
		<select id="assignee"
		        name="assignee"
		        class="form-control"
		        placeholder="{{labels.assignee}}"
		        ng-model="formData.assignee"
		        ng-options="ft.label for ft in options.assignees"
		        ng-required="true"></select>
	</div>
</div>

<div class="form-group row">
	<label class="col-sm-2 control-label">
		<span>{{labels.car}}</span>
	</label>

	<div class="col-sm-9">
		<select id="car"
		        name="car"
		        class="form-control"
		        placeholder="{{labels.car}}"
		        ng-model="formData.car"
		        ng-options="ft.label for ft in options.cars"
		        ng-required="true"></select>
	</div>
</div>
