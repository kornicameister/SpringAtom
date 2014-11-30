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
 * Created by trebskit on 2014-08-24.
 */
define(
    [
        'angular'
    ],
    function errorController(angular) {
        return function errorController($rootScope, $scope) {
            var getErrors = function getErrors() {
                    var scopeError = $rootScope.lastError,
                        errors = [],
                        scopeErrors = [];
                    if (angular.isDefined(scopeError)) {
                        scopeErrors = scopeError.error;
                        if (!angular.isArray(scopeError)) {
                            scopeErrors = [scopeErrors];
                        }
                        angular.forEach(scopeErrors, function (err) {
                            errors.push({
                                number : errors.length,
                                message: err['localizedMessage'],
                                trace  : err['stackTrace']
                            });
                        });
                    }
                    return errors;
                },
                getMessage = function getMessage() {
                    var scopeError = $rootScope.lastError,
                        msg = undefined;
                    if (angular.isDefined(scopeError)) {
                        msg = scopeError['message'];
                    }
                    return msg;
                },
                getErrorsGrid = function () {
                    var opt = {};

                    $scope.errors = getErrors();
                    $scope.selectedError = [];

                    angular.extend(opt, {
                        columnDefs          : [
                            {
                                field      : 'number',
                                displayName: 'PK',
                                width      : 40
                            },
                            {
                                field      : 'message',
                                displayName: 'Message'
                            },
                            {
                                field      : 'trace',
                                displayName: 'Stack trace',
                                visible    : false
                            }
                        ],
                        selectedItems       : $scope.selectedError,
                        multiSelect         : false,
                        afterSelectionChange: function (rowItem) {
                            $scope.trace = rowItem.entity.trace;
                        },
                        data                : 'errors'
                    });

                    return opt;
                };

            angular.extend($scope, {
                message   : getMessage(),
                errorsGrid: getErrorsGrid(),
                trace     : []
            });
        }
    }
);
