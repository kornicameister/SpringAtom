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

<section id="infopage_{{domain}}" class="container" ng-controller="InfoPageController as ipCtrl">
    <div ng-show="ipCtrl.isLoading()" class="container">
        <progressbar max="1" value="ipCtrl.getLoadingProgress()" class="progress-striped active" animate="true">
            <span style="color:black; white-space:nowrap;">{{ipCtrl.getLoadingProgress(true)}} %</span>
        </progressbar>
    </div>

    <div id="{{ipCtrl.data.getId()}}" ng-show="!ipCtrl.isLoading()">
        <infopage-header label="ipCtrl.data.getLabel()"></infopage-header>
        <div class="panel panel-info" ng-show="ipCtrl.data.getPanels().length"
             ng-repeat="panel in ipCtrl.data.getPanels()">

            <div class="x-ip-header panel-heading">
                <i ng-show="panel.getIcon()" ng-class="panel.getIcon()"></i>{{panel.getLabel()}}
            </div>

            <div class="panel-body">
                <div class="row" ng-repeat="attribute in panel.getAttributes()">
                    <div ng-switch="attribute.getType()">
                        <div ng-switch-when="tableRequest">
                            <dynamic-grid class="panel" config="attribute.value" origin="'infopage'"
                                          unpack="ipCtrl.unpackGrid(attribute)"></dynamic-grid>
                        </div>
                        <div ng-switch-default>
                            <infopage-attribute attribute="attribute"></infopage-attribute>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div ng-show="ipCtrl.error" class="panel panel-danger">
        <div class="panel-body">
            {{ipCtrl.error}}
        </div>
    </div>
</section>
