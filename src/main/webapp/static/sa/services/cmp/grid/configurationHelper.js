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
        'config/module',
        'utils',
        'momentjs'
    ],
    function configurationHelper(app, utils) {
        var skipRowNumberer = true,
            gridCfgHelper = function gridConfigurationHelper($http, $q, $log) {
                $log.debug('gridConfigurationHelper initializing');
                var priv = {
                        /**
                         * Creates HTTP configuration to be used by angular to bootstrap loading configuration object
                         * @param cfg must contain url property
                         * @returns {{method: string, url: string, cache: boolean}}
                         * @private
                         */
                        getHttpConfiguration: function _getHttpConfiguration(cfg) {
                            return {
                                method: 'GET',
                                url   : priv.getUrl(cfg),
                                cache : true
                            }
                        },
                        /**
                         * Extracts the {@config url} out of cfg. Checks if defined and throws error if not
                         * @param cfg configuration to get url from
                         * @returns {string}
                         * @private
                         */
                        getUrl              : function _getUrl(cfg) {
                            var url = cfg.url;
                            if (!url) {
                                throw new Error('cfg.url is not defined');
                            }
                            $log.debug('gridConfigurationHelper :: url={url}'.format({url: url}));
                            return url;
                        },
                        loadConfiguration   : function _loadConfiguration(cfg) {
                            var whatData,
                                whatDataPromise,
                                httpConf = priv.getHttpConfiguration(cfg);

                            if (whatDataPromise) {
                                return whatDataPromise;
                            }

                            var deferred = $q.defer();
                            whatDataPromise = deferred.promise;

                            if (whatData) {
                                deferred.resolve(whatData);
                            } else {
                                $http(httpConf)
                                    .success(function (data) {
                                        $log.debug('gridConfigurationHelper :: Loaded configuration of the grid');
                                        whatData = data;
                                        deferred.resolve(whatData);
                                    })
                                    .error(function () {
                                        deferred.reject('Unable to load data');
                                    });
                            }

                            return whatDataPromise;
                        }
                    },
                    unpacks = {
                        unpackSingleColumn : function _unpackSingleColumn(col) {
                            if (!col) {
                                throw new Error('col not defined, cannot unpack')
                            } else if (col['xtype'] === 'rownumberer' && skipRowNumberer) {
                                return undefined;
                            }
                            $log.debug('unpackSingleColumn :: unpacking column [id={id}]'.format({id: col['dataIndex']}));
                            return {
                                displayName: col['text'] || col['label'],
                                field      : col['dataIndex'],
                                sortable   : col['sortable'],
                                fieldType  : col['fieldType'] || col['displayAs'],
                                visible    : col['visible'] || true,
                                groupable  : col['groupable'] || true,
                                pinnable   : col['pinnable'] || true
                            }
                        },
                        unpackColumns      : function _unpackColumns(rawColumns, editable) {
                            if (!rawColumns) {
                                throw new Error('rawColumns not defined, cannot unpack')
                            }
                            $log.debug('unpackColumns :: unpacking {size} columns'.format({size: rawColumns.length}));
                            var columns = [],
                                sTime = utils.now();

                            angular.forEach(rawColumns, function (col) {
                                var singleColumn;

                                function canOverrideCellEditable() {
                                    return angular.isDefined(editable) && (!angular.isDefined(singleColumn['enableCellEdit']) && editable !== singleColumn['enableCellEdit']);
                                }

                                if (angular.isDefined((singleColumn = unpacks.unpackSingleColumn(col)))) {
                                    if (canOverrideCellEditable()) {
                                        singleColumn['enableCellEdit'] = editable;
                                    }
                                    columns.push(singleColumn);
                                }
                            });

                            $log.debug('unpackColumns :: completed in {time} ms'.format({time: (utils.now() - sTime)}));

                            return columns;
                        },
                        unpackFeature      : function _unpackFeature(features, ftype) {
                            if (!features || !ftype) {
                                return false;
                            } else if (!angular.isArray(features)) {
                                return false;
                            }
                            var localFtype = ftype.toLowerCase(),
                                found = undefined;
                            angular.forEach(features, function _check(feature) {
                                if (feature['ftype'] == localFtype && !angular.isDefined(found)) {
                                    found = 1;
                                }
                            });
                            return angular.isDefined(found);
                        },
                        unpackConfiguration: function _unpackConfiguration(data) {
                            $log.debug('gridConfigurationHelper :: Unpacking configuration');
                            $log.debug('gridConfigurationHelper :: Loaded configuration object in {time} ms, builder = {builder}'.format({
                                time   : moment(data['time']).milliseconds(),
                                builder: data['builtBy']
                            }));

                            var confBean = data.data,
                                features = confBean['features'],
                                sTime = utils.now();

                            if (!angular.isDefined(confBean)) {
                                throw new Error('Invalid confBean of the grid');
                            }

                            var gridOptions = {
                                collapsible        : confBean['collapsible'] || false,
                                border             : confBean['border'] || false,
                                enableSorting      : confBean['sortableColumns'] || false,
                                tableId            : confBean['tableId'],
                                builderId          : confBean['builderId'],
                                enableCellEdit     : unpacks.unpackFeature(features, 'editable'),
                                enablePinning      : unpacks.unpackFeature(features, 'pinning'),
                                enablePaging       : unpacks.unpackFeature(features, 'paging'),
                                enableRowReordering: unpacks.unpackFeature(features, 'rowReordering'),
                                showFooter         : unpacks.unpackFeature(features, 'showFooter'),
                                showFilter         : unpacks.unpackFeature(features, 'showFilter'),
                                showColumnMenu     : unpacks.unpackFeature(features, 'showColumnMenu'),
                                columns            : unpacks.unpackColumns.call(this, confBean.content, unpacks.unpackFeature(features, 'editable'))
                            };

                            $log.debug('unpackConfiguration :: completed in {time} ms'.format({time: (utils.now() - sTime)}));

                            return gridOptions;
                        }
                    };
                return {
                    loadConfiguration  : priv.loadConfiguration,
                    unpackConfiguration: unpacks.unpackConfiguration
                }
            },
            serviceName = 'gridConfigurationHelper';

        app.factory(serviceName, ['$http', '$q', '$log', gridCfgHelper])
    }
);
