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

<div class="form-group">
	<label class="col-sm-2 control-label">
		<span>{{labels.tasks}}</span>
	</label>

	<div class="col-sm-9">
		<ul class="list-group">
			<li class="list-group-item" ng-repeat="tt in formData.tasks" style="background: none; border: none">
				<div class="input-group">
					<label class="hidden" for="type-{{$index}}">{{labels.task.type}}</label>
					<select id="type-{{$index}}"
					        class="form-control form-control-feedback"
					        ng-model="tt.type"
					        name="taskType"
					        ng-options="ft.label for ft in taskTypes"></select>
					<span class="input-group-addon">/</span>
					<label class="hidden" for="task-{{$index}}">{{labels.task.task}}</label>
					<input id="task-{{$index}}"
					       class="form-control form-control-feedback"
					       ng-model="tt.task"
					       ng-required="true"
					       ng-maxlength="444"
					       name="taskText"
					       ng-trim="true">
					<span class="input-group-btn" ng-show="buttonsVisibility[$index][0]">
						<add-button title="<s:message code="button.add"/>"
						            ng-click="lh.addTask($event)"></add-button>
					</span>
					<span class="input-group-btn" ng-show="buttonsVisibility[$index][1] || formData.tasks.length > 1">
						<remove-button title="<s:message code="button.delete"/>"
						               ng-click="lh.removeTask($event, $index)"></remove-button>
					</span>
				</div>
			</li>
		</ul>
	</div>

</div>
