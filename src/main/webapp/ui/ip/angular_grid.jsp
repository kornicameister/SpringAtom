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

<style type="text/css">
    .x-dynamic-grid {
        min-height : 200px;
    }
</style>
<div id="{{grid.id}}" class="container-fluid">
    <div ng-show="grid.isLoading()">
        <progressbar max="1" value="grid.getLoadingProgress()" class="progress-striped active" animate="true">
            <span style="color:black; white-space:nowrap;">{{grid.getLoadingProgress(true)}} %</span>
        </progressbar>
    </div>
    <div ng-if="!grid.isLoading()" class="panel">
        <div class="panel-heading x-ip-attr-label">{{grid.title}}</div>
        <div class="x-dynamic-grid"
             ng-class="{table:true,'table-responsive':true,'table-bordered': grid.gridOptions.border}"
             ng-grid="grid.gridOptions"></div>
    </div>
</div>
