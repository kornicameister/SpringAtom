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
        'config/module'
    ],
    function (app) {

        /**
         * NavigationModel is the class wrapping up actions retrieved from the
         * server. Contains several method that are able to represent such model
         * in formats understandable by directives or stateService
         * @param config configuration object, should contain key {@code key} and {@code model}
         * @constructor NavigationModel constructor
         */
        function NavigationModel(config) {
            this.key = config.key;
            this.model = config.model;
        }

        NavigationModel.prototype = (function initNavigationModelPrototype() {
            var keysToDirective = ['id', 'name', 'mode', 'iconClass', 'label'];

            /**
             * Narrows the navigation [action] model according to the passed {@code keys}
             * @param model original models [as array]
             * @param keys keys to narrows {@code model}
             * @return {Array} array of narrowed models
             */
            function narrowModel(model, keys) {
                var local = [];
                angular.forEach(model, function toDirectiveFC(value) {
                    var localObj = {};
                    angular.forEach(keys, function overKeys(kValue) {
                        localObj[kValue] = value[kValue];
                    });
                    local.push(localObj);
                });
                return local;
            }

            return {
                key         : undefined,
                model       : undefined,
                getKey      : function getKey() {
                    return this.key;
                },
                setKey      : function setKey(key) {
                    this.key = key;
                    return key;
                },
                getModel    : function getModel() {
                    return this.model;
                },
                setModel    : function setModel(model) {
                    this.model = model;
                    return model;
                },
                toDirective : function toDirective() {
                    return narrowModel(this.model, keysToDirective);
                },
                toViewStates: function toViewStates() {
                    var local = [];
                    angular.forEach(this.model, function toViewStatesFC(index, value) {

                    });
                    return local;
                }
            }
        }());

        var service = function navigationService($http, $log, $q) {
            var directiveData = {},
                dataPromise = {},
                /**
                 * Points to the controller where <b>main.navigation</b> actions are defined
                 * @type {string}
                 */
                url = '/app/cmp/actions/model/jsp/';

            function loadNavigationModelByKey(key) {
                $log.log('loading navigationModel by key=' + key);
                if (dataPromise[key]) {
                    $log.debug('dataPromise[' + key + '] is already promised');
                    return dataPromise[key];
                }

                var deferred = $q.defer();
                dataPromise[key] = deferred.promise;

                $log.debug('dataPromise[' + key + '] has been created');

                if (directiveData[key]) {
                    deferred.resolve(directiveData[key]);
                } else {
                    $log.debug('directiveData[' + key + '] GET call');
                    $http.get(url + key)
                        .success(function (data) {
                            directiveData[key] = new NavigationModel({
                                key  : key,
                                model: data
                            });
                            deferred.resolve(directiveData[key]);
                        })
                        .error(function () {
                            deferred.reject('Failed to load data');
                        });
                }

                return dataPromise[key];
            }

            return {
                loadNavigation: function (key) {
                    $log.log('loading navigation by key=' + key);
                    return loadNavigationModelByKey(key);
                }
            }

        };

        app.factory('navigationService', ['$http', '$log', '$q', service])
    }
);
