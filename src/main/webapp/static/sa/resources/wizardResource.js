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
                init    : '/app/cmp/wiz/init/{key}',
                stepInit: '/app/cmp/wiz/init/step/{key}',
                /**
                 * Adds some magic number to the end of the url to stop caches.
                 * It is vital due to localization. If server picks up locale
                 * change and wizard resource would be called after it wouldn't have
                 * any effect
                 * @param url the url to adjust
                 * @returns {string}
                 */
                adjust  : function (url) {
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
                init    : function (key) {
                    return angular.extend(commonHttpConf, {
                        method: 'GET',
                        url   : urls.adjust(urls.init.format({key: key}))
                    })
                },
                stepInit: function (key) {
                    return angular.extend(commonHttpConf, {
                        method: 'GET',
                        url   : urls.adjust(urls.stepInit.format({key: key}))
                    })
                }
            },
            resource = function ($log, $http) {
                var priv = {
                        init    : function init(key) {
                            if (!angular.isDefined(key)) {
                                throw new Error('Wizard key is not defined, failed to initialize');
                            }
                            return doRequest(httpConf.init(key));
                        },
                        stepInit: function stepInit(key) {
                            if (!angular.isDefined(key)) {
                                throw new Error('Steo key is not defined, failed to initialize');
                            }
                            return doRequest(httpConf.stepInit(key));
                        }
                    },
                    doRequest = function doRequest(conf) {
                        return $http(conf);
                    };
                return {
                    init    : priv.init,
                    stepInit: priv.stepInit
                }
            };
        app.factory('wizardResource', ['$log', '$http', resource]);
    }
);
