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
        'angular',
        'common/utils',
        'ngDialogs',
        'lodash',
        // angular injections
        'services/authenticationService'
    ],
    function loginDialog(angular, utils) {
        var loginDialog = '/static/sa/common/dialogs/login/login.html',
            logoutDialog = '/static/sa/common/dialogs/login/logout.html',
            modes = {
                login : 1,
                logout: -1
            };

        /**
         * <b>sg.common.dialogs.login</b> is a module containing all functionality
         * view-like functionality required to show and manage login/logout dialog
         *
         * @module sg.common.dialogs.login
         */
        var module = angular.module('sg.common.dialogs.login', ['dialogs.main']);

        /**
         * <b>LoginController</b> is responsible for login/logout management for dialogs
         *
         * @controller LoginController
         */
        module.controller('LoginController', function ctrl($scope, $modalInstance, $log, authenticationService) {

            var successLogin = function successLogin() {
                    $modalInstance.close($scope.formData);
                },
                generalFailure = function generalFailure(error) {
                    $modalInstance.close(error);
                    $log.error(error);
                },
                cancelAction = function ($form, $event) {
                    $event.preventDefault();
                    $modalInstance.dismiss();
                    return false;
                },
                successLogout = function () {
                    $modalInstance.close();
                };

            _.assign($scope, {
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
                cancelLogout  : cancelAction,
                cancel        : cancelAction
            });
        });

        module.constant('LOGIN_MODE', modes.login);
        module.constant('LOGOUT_MODE', modes.logout);

        /**
         * <b>loginDialog</b> factory is a singleton service which allows to easily create given
         * login dialog:
         * - login
         * - logout
         *
         * @singleton
         * @service loginDialog
         */
        module.factory('loginDialog', function loginDialog(dialogs, LOGIN_MODE, LOGOUT_MODE) {
            return {
                loginDialog : function () {
                    return dialogs.create(loginDialog, 'LoginController', {
                        mode: LOGIN_MODE
                    });
                },
                logoutDialog: function () {
                    return dialogs.create(logoutDialog, 'LoginController', {
                        mode: LOGOUT_MODE
                    });
                }
            }
        });

        return module;
    }
);
