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

<div class="form-group">
	<label for="carMaster" class="col-sm-2 control-label">
		<span>{{labels.carMaster}}</span>
	</label>

	<div class="col-sm-8">
		<select id="carMaster"
		        name="carMaster"
		        class="form-control"
		        ng-model="formData.carMaster"
		        ng-options="ft.label for ft in carMasters"></select>
	</div>
	<div class="col-sm-2">
		<button class="btn-default btn-info btn-link"
		        role="link"
		        ng-click="carCtrlActions.newCarMaster($event)"
		        title="Nowa Marka/Model">
			<i class="fa fa-fw fa-inverse fa-plus"></i>
		</button>
	</div>
</div>
<div class="form-group">
	<label for="licencePlate" class="col-sm-2 control-label">
		<span>{{labels.licencePlate}}</span>
	</label>

	<div class="col-sm-9">
		<input id="licencePlate"
		       name="licencePlate"
		       class="form-control"
		       ng-model="formData.licencePlate"
		       ng-pattern="/(\w{2}\s\w{1,5})|(\w{3}\s\w{1,4})/"
		       ng-minlength="3"
		       ng-maxlength="8"
		       ng-required="true">
	</div>
</div>
<div class="form-group">
	<label for="fuelType" class="col-sm-2 control-label">
		<span>{{labels.fuelType}}</span>
	</label>

	<div class="col-sm-9">
		<select id="fuelType"
		        name="fuelType"
		        class="form-control"
		        ng-model="formData.fuelType"
		        ng-options="ft.label for ft in fuelTypes"></select>
	</div>
</div>
<div class="form-group">
	<label for="yearOfProduction" class="col-sm-2 control-label">
		<span>{{labels.year}}</span>
	</label>

	<div class="col-sm-9">
		<select id="yearOfProduction"
		        name="yearOfProduction"
		        class="form-control"
		        ng-model="formData.yearOfProduction"
		        ng-options="year.label for year in years"></select>
	</div>
</div>
