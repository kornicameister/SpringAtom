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
        'angular',
        'jsface'
    ],
    function gridConfiguration(angular) {
        return Class(function GridConfiguration() {

            var getUrl = function _getUrl(which) {
                var url = undefined;
                angular.forEach(this.urls, function (uri) {
                    if (uri.rel === which && !angular.isDefined(url)) {
                        url = uri.href;
                    }
                });
                return url;
            };

            return {
                constructor        : function (cfg) {
                    this.id = cfg.id;
                    this.urls = cfg.urls;
                    this.context = cfg.context;
                    this.label = cfg.label;
                },
                getId              : function () {
                    return this.id;
                },
                getLabel           : function () {
                    return this.label;
                },
                getContext         : function () {
                    return this.context;
                },
                hasContext         : function () {
                    return angular.isDefined(this.context);
                },
                getConfigurationUrl: function () {
                    return getUrl.call(this, 'configuration');
                },
                getDataUrl         : function () {
                    return getUrl.call(this, 'data');
                }
            }
        });
    }
);
