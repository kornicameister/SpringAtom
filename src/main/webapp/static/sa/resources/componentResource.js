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
 * Created by trebskit on 2014-09-01.
 */
define(
    [
        'config/module',
        'utils',
        'underscore',
        // jsface
        'jsface'
    ],
    function componentResource(app, utils, _) {
        var localCache = {};
        var resource = function ($log, $http, $q) {
            var baseURL = '/app/cmp',
                configBaseURL = baseURL + '/config',
                dataBaseURLR = baseURL + '/data',
                commonHttpConf = {
                    cache            : !utils.isDebug(),
                    responseType     : 'json',
                    transformResponse: function (data) {
                        if (!data.success) {
                            return $q.reject({
                                error  : data.error,
                                message: data.message
                            });
                        }
                        return data.data;
                    }
                },
                configurationHttpConf = _.extend(commonHttpConf, {
                    method: 'GET'
                }),
                configurationHandlers = {
                    other: function (builderID) {
                        return doRequest(_.extend(configurationHttpConf, {
                            url: configBaseURL + '/' + builderID
                        }));
                    },
                    ip   : function (domain, id) {
                        return doRequest(_.extend(configurationHttpConf, {
                            url: '{base}/ip/{domain}/{id}'.format({
                                base  : configBaseURL,
                                domain: domain,
                                id    : id
                            })
                        })).then(function (data) {

                        }, function (error) {

                        })
                    },
                    table: function (builderID) {

                    }
                },
                doRequest = function doRequest(conf) {
                    var directiveData = undefined,
                        dataPromise = undefined;

                    if (dataPromise) {
                        return dataPromise;
                    }

                    var deferred = $q.defer();
                    dataPromise = deferred.promise;

                    if (directiveData) {
                        deferred.resolve(directiveData);
                    } else {
                        $http(conf)
                            .success(function (data) {
                                directiveData = data;
                                deferred.resolve(directiveData);
                            })
                            .error(function () {
                                deferred.reject('Failed to load data');
                            });
                    }

                    return dataPromise;
                },
                getConfigurationHandler = function getConfigurationHandler(handler) {
                    if (_.isUndefined(handler)) {
                        return configurationHandlers.other
                    }
                    return configurationHandlers[handler];
                };
            return {
                getInfoPageDefinition: getConfigurationHandler('ip'),
                getTableDefinition   : getConfigurationHandler('table'),
                getOtherDefinition   : getConfigurationHandler()
            }
        };

        app.factory('componentResource', ['$log', '$http', '$q', resource]);
    }
);