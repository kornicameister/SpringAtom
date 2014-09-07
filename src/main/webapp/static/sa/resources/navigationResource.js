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
 * Created by trebskit on 2014-09-07.
 */
define(
    [
        'config/module',
        'underscore',
        'jsface'
    ],
    function navigationResource(app, _) {

        /**
         * NavigationModel is the class wrapping up actions retrieved from the
         * server. Contains several method that are able to represent such model
         * in formats understandable by directives or stateService
         * @param config configuration object, should contain key {@code key} and {@code model}
         * @constructor NavigationModel constructor
         */
        var NavigationModel = Class(function () {
            var keysToDirective = ['id', 'name', 'mode', 'iconClass', 'label'];

            /**
             * Narrows the navigation [action] model according to the passed {@code keys}
             * @param model original models [as array]
             * @param keys keys to narrows {@code model}
             * @return {Array} array of narrowed models
             */
            function narrowModel(model, keys) {
                var local = [];
                _.each(model, function toDirectiveFC(value) {
                    var localObj = {};
                    _.each(keys, function overKeys(kValue) {
                        localObj[kValue] = value[kValue];
                    });
                    local.push(localObj);
                });
                return local;
            }

            return{
                constructor   : function (config) {
                    this.key = config.id;
                    this.model = config.content;
                    this.description = config.description;
                },
                getKey        : function getKey() {
                    return this.key;
                },
                getModel      : function getModel() {
                    return this.model;
                },
                getDescription: function getDescription() {
                    return this.description;
                },
                toDirective   : function toDirective() {
                    return narrowModel(this.model, keysToDirective);
                }
            }
        });

        var helpers = {
            isGetActionModelUrl: function (url) {
                url = url.split('/');
                return url[url.length - 2] === 'model';
            }
        };

        app.factory('navigationResource', function navigationResource(Restangular) {
            var resource = Restangular.withConfig(function configurer(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('cmp/actions');
                RestangularConfigurer.addResponseInterceptor(function responseInterceptor(data,
                                                                                          operation,
                                                                                          what,
                                                                                          url,
                                                                                          response,
                                                                                          deffered) {
                    var actionModel = undefined;
                    try {
                        switch (operation) {
                            case 'get':
                                if (helpers.isGetActionModelUrl(url)) {
                                    actionModel = new NavigationModel(data);
                                }
                                break;
                        }
                    } catch (error) {
                        deffered.reject(error);
                    }
                    return actionModel;
                });
            });

            resource.getActionModel = function (actionModel) {
                return this.one('model', actionModel).get();
            }.bind(resource);

            return resource;
        });
    }
);