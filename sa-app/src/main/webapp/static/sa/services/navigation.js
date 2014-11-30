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
        'config/module',
        'underscore',
        // angular injections
        'services/authenticationService',
        'resources/navigationResource'
    ],
    function (app, _) {

        function getAuthoritiesFromSecurity(security) {
            var roles = security.roles;
            if (_.has(roles, "roles")) {
                return getAuthoritiesFromSecurity(roles);
            }
            return roles;
        }

        app.factory('navigationService', function navigationService($rootScope,
                                                                    $log,
                                                                    localStorageService,
                                                                    authenticationService,
                                                                    navigationResource) {
            var service = {
                    getNavigationModel: function (key) {
                        return navigationResource.getActionModel(key);
                    },
                    setNavigatorModel : function (key) {
                        var self = this;
                        this.getNavigationModel(key).then(function (am) {
                            $rootScope.activeActionModel = _.filter(am, self.isActionEnabled);
                        });
                        localStorageService.set(activeActionModel, key);
                    },
                    isActionEnabled   : function (action) {
                        if (_.isString(action)) {
                            var am = navigationResource.getActionModel(localStorageService.get(activeActionModel), true);
                            action = _.findWhere(am, {name: action});
                            if (_.isUndefined(action)) {
                                $log.warn('{action} not found in active action model'.format({
                                    action: action
                                }));
                                return false;
                            }
                        }
                        if (!_.has(action, 'security')) {
                            return true;
                        }
                        var security = action.security;
                        if (!security.authenticated) {
                            return true;
                        }

                        if (_.has(security, 'roles')) {
                            return authenticationService.hasAuthority(getAuthoritiesFromSecurity(security), false);
                        } else {
                            return security.authenticated && authenticationService.getAuthentication().authenticated;
                        }
                    }
                },
            // last active action model
                activeActionModel = 'activeActionModel',
                model = localStorageService.get(activeActionModel);

            if (!_.isUndefined(model)) {
                service.getNavigationModel(model);
            }

            $rootScope.$on('authentication.successful', function () {
                service.setNavigatorModel(localStorageService.get(activeActionModel));
            });
            $rootScope.$on('authentication.terminated', function () {
                service.setNavigatorModel(localStorageService.get(activeActionModel));
            });

            return service
        })
    }
);
