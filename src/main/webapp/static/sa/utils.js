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
 * Created by trebskit on 2014-08-11.
 */
define(
    [
        'angular',
        // dependency to create utils class
        'jsface'
    ],
    function utils(angular) {
        var $$window = window,
            _urlParams = function urlParams(url) {
                url = url || window.location.href;
                if (!url || (url.indexOf("?") < 0 && url.indexOf("&") < 0)) {
                    return {};
                }
                url = url.substr(0, url.indexOf('#'));
                var params = url.substr(url.indexOf("?") + 1);
                return _urlDecode(params);
            },
            _urlDecode = function urlDecode(string, overwrite) {
                var obj = {},
                    pairs = string.split('&'),
                    name,
                    value;
                angular.forEach(pairs, function (pair) {
                    pair = pair.split('=');
                    name = decodeURIComponent(pair[0]);
                    value = decodeURIComponent(pair[1]);
                    obj[name] = overwrite || !obj[name] ? value : [].concat(obj[name]).concat(value);
                });
                return obj;
            },
            _isDebug = function isDebug(url) {
                url = url || window.location.href;
                var params = _urlParams(url);
                return params['debug'] || false;
            },
            _getAppUrl = function getAppUrl($window) {
                var href = $window.location.href.split('/');
                return href[0] + '//' + href[2] + '/' + href[3] + '/';
            },
            _now = ($$window['performance'] && $$window['performance']['now']) ? function () {
                return $$window['performance']['now']();
            } : (Date.now || (Date.now = function () {
                return +new Date();
            })),
            _toNgOptions = function toNgOptions(cfg) {
                if (angular.isArray(cfg)) {
                    cfg = {
                        data: cfg
                    };
                }
                var data = cfg.data,
                    localData = [],
                    labelMethod = cfg.getLabel,
                    valueMethod = cfg.getValue;
                if (!angular.isDefined(labelMethod)) {
                    labelMethod = _identityFn
                }
                if (!angular.isDefined(valueMethod)) {
                    valueMethod = _identityFn
                }
                angular.forEach(data, function dataIt(chunk) {
                    localData.push({
                        label: labelMethod.call(this, chunk),
                        value: valueMethod.call(this, chunk)
                    })
                });
                return localData;
            },
            _identityFn = function (arg) {
                return arg
            };

        return Class({
            $singleton  : true,
            /**
             * Returns application URL. Format is: [protocol]://[hostname]/[appName]/
             * @param $window window object
             * @returns {string} href as string
             */
            getAppUrl   : _getAppUrl,
            getUrlParams: _urlParams,
            urlDecode   : _urlDecode,
            isDebug     : _isDebug,
            now         : _now,
            toNgOptions : _toNgOptions,
            abstractFn  : function () {
                var msg = 'abstractFn must be redefined';
                if (_isDebug()) {
                    alert(msg)
                } else {
                    throw new Error(msg);
                }
            },
            emptyFn     : function () {
            },
            identityFn  : _identityFn
        })
    }
);
