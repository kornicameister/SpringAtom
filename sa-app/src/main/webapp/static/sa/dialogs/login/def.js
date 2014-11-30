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
 * Created by trebskit on 2014-08-22.
 */
define(
    [
        'config/module',
        'utils',
        // angular injections
        'services/authenticationService'
    ],
    function loginDialog(app, utils) {
        var loginDialog = '/static/sa/dialogs/login/login.html',
            logoutDialog = '/static/sa/dialogs/login/logout.html',
            ctrl = function ctrl($scope, $modalInstance, $log, authenticationService) {

                var successLogin = function successLogin() {
                        $modalInstance.close($scope.formData);
                    },
                    generalFailure = function generalFailure(error) {
                        $modalInstance.close(error);
                        $log.error(error);
                    },
                    successLogout = function () {
                        $modalInstance.close();
                    };

                angular.extend($scope, {
                    debug         : utils.isDebug(),
                    formData      : {
                        username: undefined,
                        password: undefined
                    },
                    login         : function login($form, $event) {
                        $event.preventDefault();
                        var username = $scope.formData.username,
                            password = $scope.formData.password;
                        authenticationService.login(username, password).then(successLogin, generalFailure);
                    },
                    logout        : function ($event) {
                        $event.preventDefault();
                        authenticationService.logout().then(successLogout, generalFailure);
                    },
                    forgotPassword: function forgotPassword($form, $event) {
                        $event.preventDefault();
                    },
                    register      : function register($form, $event) {
                        $event.preventDefault();
                    },
                    cancelLogout  : function ($event) {
                        $event.preventDefault();
                        $modalInstance.dismiss();
                    },
                    cancel        : function cancel($form, $event) {
                        $event.preventDefault();
                        $modalInstance.dismiss($form);
                    }
                });
            };

        app.factory('loginDialog', function service(dialogs) {
            return {
                loginDialog : function () {
                    return dialogs.create(loginDialog, ctrl, {
                        mode: 'login'
                    });
                },
                logoutDialog: function () {
                    return dialogs.create(logoutDialog, ctrl, {
                        mode: 'logout'
                    });
                }
            }
        });
    }
);
