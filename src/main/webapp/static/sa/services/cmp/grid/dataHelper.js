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
        'momentjs',
        // angular injections
        'services/cmp/grid/pageHelper'
    ],
    function dataHelper(app, utils) {
        var gridDataHelper = function gridDataHelper($http, $q, $log, gridPageHelper) {
            var priv = {
                    getHttpConfiguration: function _createHTTPConfiguration(conf) {
                        var searchText = conf['request']['searchText'] || '',
                            page = conf['request']['page'] || 1,
                            pageSize = conf['request']['limit'] || gridPageHelper.getDefaultPageSize(),
                            url = conf['url'];
                        if (!url || url === '') {
                            throw new Error('URL is not defined, can not proceed');
                        }
                        var retConf = {
                            method: 'POST',
                            cache : false,
                            url   : url,
                            params: {
                                start: page === 0 ? 0 : (page * pageSize),
                                page : page,
                                limit: pageSize
                            }
                        };
                        delete conf['request']['page'];
                        delete conf['request']['limit'];
                        delete conf['request']['searchText'];
                        delete conf['url'];

                        angular.extend(retConf.params, conf.request);

                        return retConf
                    },
                    /**
                     * Loads data for the grid
                     * @param conf configuration of the object. Must at least contain;
                     * <ul>
                     *     <li>url</li>
                     *     <li>page: size, index</li>
                     *     <li>searchText</li>
                     * </ul>
                     * @private
                     */
                    loadData            : function _loadData(conf) {
                        var whatData,
                            whatDataPromise,
                            httpConf = priv.getHttpConfiguration(conf);

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
                                    $log.debug('gridDataHelper :: Loaded data of the grid');
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
                    unpackChunk      : function _unpackChunk(chunk) {
                        if (!chunk) {
                            $log.warn('unpackChunk :: cannot unpack, chunk not defined');
                        }
                        var localChunk = {};
                        angular.forEach(chunk, function (value, key) {
                            localChunk[key] = value['value'];
                        });
                        return localChunk;
                    },
                    unpackDataPackage: function _unpackDataPackage(data) {
                        if (!data) {
                            throw new Error('data not defined, cannot unpack')
                        }
                        $log.debug('unpackDataPackage :: unpacking {size} data objects'.format({size: data.length}));
                        var localData = [],
                            sTime = utils.now();

                        angular.forEach(data, function (chunk) {
                            var singleColumn;
                            if (angular.isDefined((singleColumn = unpacks.unpackChunk(chunk)))) {
                                localData.push(singleColumn);
                            }
                        });

                        $log.debug('unpackDataPackage :: completed in {time} ms'.format({time: (utils.now() - sTime)}));

                        return localData;
                    },
                    unpackData       : function _unpackData(data) {
                        $log.debug('gridDataHelper :: Unpacking data');
                        $log.debug('gridDataHelper :: Loaded configuration object in {time} ms, builder = {builder}'.format({
                            time   : moment(data['time']).milliseconds(),
                            builder: data['builtBy']
                        }));

                        var dataBean = data.data,
                            sTime = utils.now();

                        if (!angular.isDefined(dataBean)) {
                            throw new Error('Invalid dataBean of the grid');
                        }

                        var localData = {
                            data: unpacks.unpackDataPackage(dataBean)
                        };

                        $log.debug('unpackData :: completed in {time} ms'.format({time: (utils.now() - sTime)}));

                        return localData;
                    }
                };

            return {
                loadData  : priv.loadData,
                unpackData: unpacks.unpackData
            }
        };

        app.factory('gridDataHelper', ['$http', '$q', '$log', 'gridPageHelper', gridDataHelper])
    }
);
