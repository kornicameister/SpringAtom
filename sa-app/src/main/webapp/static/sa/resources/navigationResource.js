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
        'underscore'
    ],
    function navigationResource(app, _) {
        app.factory('navigationResource', function navigationResource(Restangular, localStorageService, $q) {
            var helpers = {
                    isGetActionModelUrl: function (url) {
                        url = url.split('/');
                        return url[url.length - 2] === 'model';
                    },
                    isInCache          : function (actionModel) {
                        return _.indexOf(localStorageService.keys(), actionModel, false) > -1;
                    },
                    updateCache        : function (actionModel, data) {
                        localStorageService.remove(actionModel);
                        localStorageService.set(actionModel, data);
                        return data;
                    },
                    parseModel         : function (model) {
                        return model.content;
                    }
                },
                resource = Restangular.withConfig(function configurer(RestangularConfigurer) {
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
                                        actionModel = helpers.parseModel(data);
                                        actionModel = helpers.updateCache(data.id, actionModel);
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
                var immediateReturn = _.toArray(arguments).slice(1);
                if (_.isUndefined(immediateReturn) || immediateReturn.length === 0) {
                    immediateReturn = false;
                } else {
                    immediateReturn = immediateReturn[0];
                }
                if (helpers.isInCache(actionModel)) {
                    if (immediateReturn) {
                        return localStorageService.get(actionModel);
                    } else {
                        var deferred = $q.defer();
                        setTimeout(function () {
                            deferred.resolve(localStorageService.get(actionModel));
                        }, 1000);
                        return deferred.promise;
                    }
                }
                return this.one('model', actionModel).get();
            }.bind(resource);

            return resource;
        });
    }
);