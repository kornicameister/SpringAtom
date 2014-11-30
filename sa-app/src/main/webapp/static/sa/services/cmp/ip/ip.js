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
 * Created by trebskit on 2014-08-14.
 */
define(
    [
        'config/module'
    ],
    function ip(app) {
        var ipService = function ipService($log, $http, $q) {
            $log.debug('ipService loading...');

            var urlTemplate = '/app/cmp/{what}/ip/{domain}/{id}',
                getUrl = function (arg, what) {
                    return urlTemplate.format({
                        domain: arg.domain,
                        id    : arg.key,
                        what  : what
                    });
                },
                getParams = function () {
                    return {
                        method: 'GET',
                        cache : true
                    }
                },
                doLoad = function loadIpCfg(object, what, getParamForRequest) {
                    var whatData,
                        whatDataPromise,
                        methodToBuildParams = getParamForRequest || getParams;

                    $log.debug('Loading ', what, ' for InfoPage');
                    if (!object.key || !object.domain) {
                        throw new Error('Invalid IP ', what, ' data');
                    }

                    if (whatDataPromise) {
                        $log.debug(what, ' promised, key=', object.domain);
                        return whatDataPromise;
                    }

                    var deferred = $q.defer();
                    whatDataPromise = deferred.promise;

                    if (whatData) {
                        deferred.resolve(whatData);
                    } else {
                        $log.debug('Loading ', what, ' for key = ', object.domain);

                        var httpConfiguration = methodToBuildParams.call(this, object, what);
                        if (!httpConfiguration['url']) {
                            httpConfiguration['url'] = getUrl(object, what);
                        }

                        $http(httpConfiguration)
                            .success(function (data) {
                                whatData = data;
                                deferred.resolve(whatData);
                            })
                            .error(function () {
                                deferred.reject('Failed to load {what}'.format({what: what}));
                            });
                    }

                    return whatDataPromise;
                },
                getUiSref = function _getUiSref(href) {
                    var prefix = '/app/cmp/ip/',
                        stripped = href.replace(prefix, '').split('/');
                    return {
                        domain: stripped[0],
                        id    : stripped[1]
                    };
                },
                toInfoPageLink = function (attribute) {
                    $log.debug('toInfoPageLink(attribute=', attribute, ')');
                    if (angular.isObject(attribute)) {

                        var value = attribute.value,
                            linkCfg = {};

                        linkCfg.linkLabel = value.linkLabel;
                        linkCfg.uiSref = getUiSref(value.value);

                        return linkCfg;
                    } else {
                        throw new Error('Attribute(val=', attribute, ') is not InfoPageAttribute');
                    }
                };

            return {
                loadConfiguration: function (arg) {
                    return doLoad(arg, 'config');
                },
                loadData         : function (arg, getParams) {
                    return doLoad(arg, 'data', getParams);
                },
                getInfoPageURI   : getUiSref,
                toInfoPageLink   : toInfoPageLink
            };
        };

        app.factory('ipService', ['$log', '$http', '$q', ipService]);
    }
);
