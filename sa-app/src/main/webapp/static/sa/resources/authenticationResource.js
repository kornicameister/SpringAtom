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

define(
    [
        'config/module'
    ],
    function authenticationResource(app) {
        app.factory('authenticationResource', function userResource($http, $q, $log) {

            var resource = {};

            resource.login = function (username, password) {
                var deferred = $q.defer(),
                    data = "j_username=" + encodeURIComponent(username) + "&j_password=" + encodeURIComponent(password) + "&submit=Login";

                $http.post('/app/authentication', data, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                }).success(function (data) {
                    $log.debug('Successfully logged in user {u}'.format({
                        u: username
                    }));
                    deferred.resolve(data);
                }).error(function (error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            }.bind(resource);

            resource.logout = function () {
                var url = '/app/logout',
                    deferred = $q.defer();

                $http.post(url, {}, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                }).success(function (data) {
                    deferred.resolve(data);
                }).error(function (error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            };

            resource.status = function () {
                throw new Error('Not implemented');
            };

            return resource;
        });
    }
);