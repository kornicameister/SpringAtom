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
        'underscore',
        'moment',
        // angular injections
        'services/cmp/grid/pageHelper'
    ],
    function dataHelper(app, utils, _) {
        var gridDataHelper = function gridDataHelper($http, $q, $log, gridPageHelper) {
            var unpacks = {
                unpackChunk      : function _unpackChunk(chunk) {
                    if (!chunk) {
                        $log.warn('unpackChunk :: cannot unpack, chunk not defined');
                    }
                    var localChunk = {};
                    _.each(chunk, function (value, key) {
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

                    _.each(data, function (chunk) {
                        var singleColumn;
                        if (!_.isUndefined((singleColumn = unpacks.unpackChunk(chunk)))) {
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

                    var dataBean = _.isArray(data) ? data : (_.has(data, 'data') ? data.data : undefined),
                        sTime = utils.now();

                    if (_.isUndefined(dataBean)) {
                        throw new Error('Invalid dataBean of the grid');
                    }

                    $log.debug('unpackData :: completed in {time} ms'.format({time: (utils.now() - sTime)}));

                    return unpacks.unpackDataPackage(dataBean);
                }
            };

            return {
                unpackData: unpacks.unpackData
            }
        };

        app.factory('gridDataHelper', ['$http', '$q', '$log', 'gridPageHelper', gridDataHelper])
    }
);
