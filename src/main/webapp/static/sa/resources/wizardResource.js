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
    function wizardResource(app, utils) {
        var urls = {
                init      : '/app/cmp/wiz/init/{key}',
                stepInit  : '/app/cmp/wiz/init/{wizard}/step/{step}',
                stepSubmit: '/app/cmp/wiz/submit/{wizard}/step/{key}',
                submit    : '/app/cmp/wiz/submit/{key}',
                /**
                 * Adds some magic number to the end of the url to stop caches.
                 * It is vital due to localization. If server picks up locale
                 * change and wizard resource would be called after it wouldn't have
                 * any effect
                 * @param url the url to adjust
                 * @returns {string}
                 */
                adjust    : function (url) {
                    return url + '?_d=' + utils.now();
                }
            },
            commonHttpConf = {
                cache            : !utils.isDebug(),
                responseType     : 'json',
                transformResponse: function (data) {
                    return data.content;
                }
            },
            httpConf = {
                init      : function (key) {
                    return angular.extend(commonHttpConf, {
                        method: 'GET',
                        url   : urls.adjust(urls.init.format({key: key}))
                    })
                },
                stepInit  : function (wizard, step) {
                    return angular.extend(commonHttpConf, {
                        method: 'GET',
                        url: urls.adjust(urls.stepInit.format({
                            step  : step,
                            wizard: wizard
                        }))
                    })
                },
                stepSubmit: function (key, data) {
                    return angular.extend(commonHttpConf, {
                        method: 'POST',
                        data: data,
                        url   : urls.adjust(urls.stepSubmit.format({key: key}))
                    })
                },
                submit    : function (key, data) {
                    return angular.extend(commonHttpConf, {
                        method: 'POST',
                        data: data,
                        url   : urls.adjust(urls.submit.format({key: key}))
                    })
                }
            },
            resource = function ($log, $http, $q) {
                var priv = {
                        init      : function init(key) {
                            if (!angular.isDefined(key)) {
                                throw new Error('Wizard key is not defined, failed to initialize');
                            }
                            return doRequest(httpConf.init(key));
                        },
                        stepInit: function stepInit(wizard, step) {
                            if (!angular.isDefined(step)) {
                                throw new Error('Step key is not defined, failed to initialize');
                            }
                            if (!angular.isDefined(wizard)) {
                                throw new Error('Wizard key is not defined, failed to initialize');
                            }
                            return doRequest(httpConf.stepInit(wizard, step));
                        },
                        stepSubmit: function stepSubmit(key, data) {
                            if (!angular.isDefined(key)) {
                                throw new Error('Step key is not defined, failed to initialize');
                            }
                            return doRequest(httpConf.stepSubmit(key, data));
                        },
                        submit    : function submit(key, data) {
                            if (!angular.isDefined(key)) {
                                throw new Error('Step key is not defined, failed to initialize');
                            }
                            return doRequest(httpConf.submit(key, data));
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
                    init      : priv.init,
                    stepInit  : priv.stepInit,
                    stepSubmit: priv.stepSubmit,
                    submit    : priv.submit
                }
            };
        app.factory('wizardResource', ['$log', '$http', '$q', resource]);
    }
);
