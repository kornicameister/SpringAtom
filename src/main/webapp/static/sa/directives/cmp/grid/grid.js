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
 * Created by trebskit on 2014-08-13.
 */

define(
    [
        'angular',
        'classes/cmp/grid/gridConfiguration',
        // angular
        'services/cmp/grid/grid'
    ],
    function gridDirective(angular, GridConfiguration) {
        var gridDirective = function gridDirective($log, gridService) {
            var defaultUnpack = function _defaultUnpack(conf) {
                    if (!conf) {
                        return undefined;
                    }
                    return new GridConfiguration({
                        id     : conf['builderId'],
                        label  : conf['label'],
                        urls   : conf['urls'],
                        context: conf['value']
                    });
                },
                linkGridDirective = function _linkGridDirective(scope) {
                    var currentStep = 0,
                        stepsTotalCount = 4,
                        origin = scope.origin,
                        conf = scope.conf,
                        unpackedConfiguration,
                        configReady,
                        collectAttributesFromConf = function _collectAttributesFromConf() {
                            var localArray = [];
                            angular.forEach(scope.grid.gridConfig.columns, function (column) {
                                localArray.push({
                                    path: column['field'],
                                    type: column['fieldType']
                                });
                            });
                            return {
                                attributes: localArray
                            }
                        },
                        pushLoadCfg = function _pushLoadCfg() {
                            gridService.loadConfiguration({
                                url    : unpackedConfiguration.getConfigurationUrl(),
                                success: function success(data) {
                                    // safe at this point, in data we must assign data directly
                                    scope.grid.gridConfig.columns = data.columns;
                                    delete data.columns;
                                    angular.extend(scope.grid.gridOptions, data);
                                    // safe at this point, in data we must assign data directly
                                    configReady = true;
                                    currentStep += 1;
                                },
                                failure: function failure(data) {
                                    scope.error = data;
                                }
                            });
                        },
                        bootstrapLoadingData = function bootstrapLoadingData() {
                            if (configReady) {
                                pushLoadData();
                            } else {
                                $log.debug('Config not loaded yet, waiting another 100ms');
                                setTimeout(bootstrapLoadingData, 100);
                            }
                        },
                        pushLoadData = function _pushLoadData() {
                            var cfg = {
                                url    : unpackedConfiguration.getDataUrl(),
                                success: function success(data, page) {
                                    scope.grid.gridConfig.data = data.data;
                                    currentStep += 1;
                                },
                                failure: function failure(data) {
                                    scope.error = data;
                                }
                            };
                            // empty wrapping for request
                            cfg.request = {};
                            angular.extend(cfg.request, unpackedConfiguration.getContext());
                            angular.extend(cfg.request, gridService.getInitialPage());
                            angular.extend(cfg.request, collectAttributesFromConf());
                            gridService.loadData(cfg);
                        },
                        combineAndFinish = function _combineAndFinish() {
                            if (scope.grid.gridConfig.columns && scope.grid.gridConfig.data) {
                                $log.log('Configuration and data loaded, combining');
                                currentStep += 1;
                                scope.$apply();
                            } else {
                                $log.debug('Config/Data not loaded yet, waiting another 100ms');
                                setTimeout(combineAndFinish, 100);
                            }
                        };

                    if (!origin) {
                        $log.debug('No origin set, using grid');
                        origin = 'grid';
                    }
                    if (origin === 'infopage') {
                        unpackedConfiguration = defaultUnpack(conf);
                        $log.debug('Configuration unpacked, grid(id=', unpackedConfiguration.getId(), ')');
                        currentStep += 1;
                    }
                    if (!unpackedConfiguration) {
                        throw new Error('No available GridConfiguration');
                    }

                    scope.grid = {
                        id                : unpackedConfiguration.getId(),
                        title             : unpackedConfiguration.getLabel(),
                        isLoading         : function () {
                            return currentStep !== stepsTotalCount;
                        },
                        getLoadingProgress: function () {
                            var humanized = arguments.length == 1,
                                progress = currentStep / stepsTotalCount;
                            return humanized ? (progress * 100.0) : progress;
                        },
                        gridConfig        : {
                            data   : [],
                            columns: []
                        },
                        gridOptions       : {
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
                    };

                    setTimeout(pushLoadCfg, 2);
                    setTimeout(bootstrapLoadingData, 3);
                    setTimeout(combineAndFinish, 5);
                };

            return {
                restrict   : 'E',
                scope      : {
                    /**
                     * Configuration for the grid.
                     * Must contains conf and data urls
                     */
                    conf  : '=config',
                    origin: '=origin',
                    /**
                     * Function that unpacks attributes
                     */
                    unpack: '&unpack'
                },
                templateUrl: '/app/cmp/grid/generic',
                link       : linkGridDirective
            }
        };

        return {
            name      : 'dynamicGrid',
            definition: ['$log', 'gridService', gridDirective]
        }
    }
);
