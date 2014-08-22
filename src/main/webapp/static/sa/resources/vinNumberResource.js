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
 * Created by trebskit on 2014-08-17.
 */
define(
    [
        'config/module',
        'utils'
    ],
    function vinNumberResource(app, utils) {
        var urls = {
                decode: '/app/data/vin/decode/{key}'
            },
            commonHttpConf = {
                cache            : !utils.isDebug(),
                responseType     : 'json',
                transformResponse: function (data) {
                    return data.content;
                }
            },
            httpConf = {
                decode: function (vinNumber) {
                    return angular.extend(commonHttpConf, {
                        method: 'GET',
                        url   : urls.decode.format({key: vinNumber})
                    })
                }
            },
            resource = function ($log, $http, $q) {
                var priv = {
                        /**
                         * Sends <b>Ajax</b> request to the server in order to decode the vinNumber.
                         * Returned data matches <i>VinNumberData</i>.
                         * @param vinNumber vin number to decode
                         */
                        decode: function decode(vinNumber) {
                            if (!angular.isDefined(vinNumber) || !angular.isString(vinNumber) || vinNumber.length < 1) {
                                throw new Error('vinNumber key is not defined, failed to decode');
                            }
                            return doRequest(httpConf.decode(vinNumber));
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
                    };
                return {
                    decode: priv.decode
                }
            };
        app.factory('vinNumberResource', ['$log', '$http', '$q', resource]);
    }
);
