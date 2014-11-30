/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]
 *
 * [SpringAtom] is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [SpringAtom] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

/**
 * Created by trebskit on 2014-09-22.
 */
define(
    [
        'underscore',
        // angular injections
        'services/cmp/grid/grid'
    ],
    function gridController(_) {
        return function ($scope, gridService) {
            var self = this;

            self.getConfiguration = function getConfiguration(rawConf) {
                $scope.currentStep += 1;
                return gridService.unpackConfiguration(rawConf);
            };

            self.getData = function getData(rawData) {
                $scope.currentStep += 1;
                return gridService.unpackData(rawData);
            };

            self.getGridConfig = function (conf) {
                var data = self.getData(conf.data),
                    conf = self.getConfiguration(conf.definition);
                $scope.currentStep += 1;
                return _.extend({
                    data: data
                }, conf);
            };

            self.getGridTitle = function (conf) {
                $scope.currentStep += 1;
                return conf.definition.label;
            };
            self.getGridId = function (conf) {
                $scope.currentStep += 1;
                return conf.definition.tableId;
            };

            $scope.currentStep = 0;
            $scope.totalSteps = 5;
            $scope.isLoading = function () {
                return $scope.currentStep !== $scope.totalSteps;
            };
            $scope.getLoadingProgress = function () {
                var humanized = arguments.length == 1,
                    progress = $scope.currentStep / $scope.totalSteps;
                return humanized ? (progress * 100.0) : progress;
            };

            self.getGridOptions = function (conf) {
                return {
                    id         : self.getGridId(conf),
                    title      : self.getGridTitle(conf),
                    gridConfig : self.getGridConfig(conf),
                    gridOptions: {
                        data          : 'grid.gridConfig.data',
                        columnDefs    : 'grid.gridConfig.columns',
                        enablePinning : 'grid.gridConfig.enablePinning',
                        enablePaging  : 'grid.gridConfig.enablePaging',
                        showFooter    : 'grid.gridConfig.showFooter',
                        showFilter    : 'grid.gridConfig.showFilter',
                        showColumnMenu: 'grid.gridConfig.showColumnMenu',
                        pagingOptions : {
                            pageSizes: gridService.getPageSizes(),
                            pageSize : gridService.getDefaultPageSize()
                        }
                    }
                }
            };
        }
    }
);