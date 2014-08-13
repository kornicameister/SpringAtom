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
 * Created by kornicameister on 18.07.14.
 */
define(
    [
        'config/module',
        'classes/cmp/ip/page',
        // angular injections
        'services/cmp/ip/ip'
    ],
    function infoPageController(app, InfoPageComponent) {
        var infoPageController = function _infoPageController($log, $scope, ipService) {
            var me = this,
                currentStep = 0,
                stepsTotalCount = 4,
                rawConfig,
                rawData;

            me.data = undefined;

            me.isLoading = function () {
                return !me.data;
            };
            me.isDebug = function () {
                return false;
            };
            me.getLoadingProgress = function () {
                var humanized = arguments.length == 1,
                    progress = currentStep / stepsTotalCount;
                return humanized ? (progress * 100.0) : progress;
            };

            function combineDataAndFinish() {
                $log.debug('Configuration and data load for infoPage=[{ip}/{key}]'.format({
                    ip : $scope.$parent.infopage.domain,
                    key: $scope.$parent.infopage.key
                }));

                me.data = new InfoPageComponent(rawConfig, rawData);

                rawConfig = undefined;
                rawData = undefined;

                currentStep += 1;
            }

            function loadDataAfterConfig() {

                if (me.error) {
                    $log.error('Cannot load data, found error={error}'.format({
                        error: me.error
                    }));
                    return;
                }

                var config = rawConfig;

                if (!config) {
                    throw new Error('Called without configuration loaded');
                }

                function infoPageDataRequestCreate(ip) {
                    return {
                        method: 'POST',
                        url   : '/app/cmp/data/ip',
                        cache : false,
                        data  : (function buildParams() {
                            var params = {
                                pageId: config['id'],
                                id    : ip['key'],
                                domain: config['domain']
                            };
                            params['version'] = ip['version'] || -1;
                            params['revision'] = ip['revision'] || -1;
                            params['attributes'] = (function () {
                                var localArray = [];
                                angular.forEach(config.content, function (panel) {
                                    angular.forEach(panel.content, function (attribute) {
                                        localArray.push({
                                            path: attribute['path'],
                                            type: attribute['displayAs']
                                        })
                                    });
                                });
                                return localArray;
                            }());
                            currentStep += 1;
                            return params;
                        }())
                    }
                }

                ipService
                    .loadData($scope.$parent.infopage, infoPageDataRequestCreate)
                    .then(function onSuccess(data) {
                        $log.debug('Loaded configuration object in {time} ms, builder = {builder}'.format({
                            time   : moment(data['time']).milliseconds(),
                            builder: data['builtBy']
                        }));
                        currentStep += 1;
                        rawData = data.data;
                        combineDataAndFinish();
                    }, function onError() {
                        me.error = 'ERROR: failed to load batch [config,data]';
                    });
            }

            ipService
                .loadConfiguration($scope.$parent.infopage)
                .then(function onSuccess(data) {
                    $log.debug('Loaded configuration object in {time} ms, builder = {builder}'.format({
                        time   : moment(data['time']).milliseconds(),
                        builder: data['builtBy']
                    }));
                    rawConfig = data.data;
                    currentStep += 1;
                    loadDataAfterConfig();
                }, function onError() {
                    me.error = 'ERROR: failed to load batch [config,data]';
                });
            currentStep += 1;
        };

        app.controller('InfoPageController', ['$log', '$scope', 'ipService', infoPageController])
    }
);
