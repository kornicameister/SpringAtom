/**
 * Created by trebskit on 2014-10-02.
 */
define(
    [
        'config/module',
        'underscore',
        // angular injections
        'resources/authenticationResource',
        'resources/userResource'
    ],
    function authenticationService(app) {

        "use strict";

        app.factory('authenticationService', function ($rootScope,
                                                       $interval,
                                                       $timeout,
                                                       timeoutDelay,
                                                       authenticationResource,
                                                       userResource) {

            var authentication = {
                    erase: function () {
                        $rootScope.authentication = {
                            authenticated: false
                        };
                    },
                    save : function (auth) {
                        $rootScope.authentication = auth;
                    }
                },
                checkIfAuthenticated = {
                    promise: undefined,
                    func   : function () {
                        userResource.isAuthenticated().then(function (serverAuthenticated) {

                            serverAuthenticated = _.isUndefined(serverAuthenticated) ? false : serverAuthenticated;
                            var clientAuthenticated = $rootScope.authentication.authenticated;

                            if (!(clientAuthenticated || serverAuthenticated)) {
                                // no client saved authentication and server authentication
                            } else if (!clientAuthenticated && serverAuthenticated) {
                                // no client saved authenticated but authenticated on server
                                // set up as if for successful authenticated by calling onLogin.success callback
                                callbacks.onLogin.success();
                            } else if (clientAuthenticated && !serverAuthenticated) {
                                // client has authentication but server no longer does
                                // act similar to the onLogout
                                callbacks.onLogout.func();
                            }

                        });
                    }
                },
                callbacks = {
                    onLogout: {
                        func: function () {
                            $interval.cancel(checkIfAuthenticated.promise);
                            authentication.erase();
                            $rootScope.$broadcast('authentication.terminated');
                        }
                    },
                    onLogin : {
                        success: function () {
                            userResource.getCurrentUser().then(function (user) {
                                authentication.save(user);
                                if (user.authenticated) {
                                    checkIfAuthenticated.promise = $interval(checkIfAuthenticated.func, 10000);
                                    $rootScope.$broadcast('authentication.successful', user);
                                }
                                return user;
                            });
                        },
                        failure: function () {
                            $rootScope.$broadcast('authentication.failed');
                        }
                    }
                };

            authentication.erase();
            $timeout(checkIfAuthenticated.func, timeoutDelay);

            return {
                login            : function login(username, password) {
                    return authenticationResource.login(username, password).then(callbacks.onLogin.success, callbacks.onLogin.failure);
                },
                logout           : function logout() {
                    if (!this.isAuthenticated()) {
                        return;
                    }
                    return authenticationResource.logout().then(callback.onLogout.func, callback.onLogout.func);
                },
                hasAuthority     : function (expectedRoles, defaultDecision) {
                    defaultDecision = _.isUndefined(defaultDecision) ? false : defaultDecision;
                    var authentication = this.getAuthentication(),
                        roles = authentication.authorities;

                    if (!authentication.authenticated) {
                        return defaultDecision;
                    }

                    return _.intersection(expectedRoles, roles || []);
                },
                getAuthorities   : function () {
                    return this.isAuthenticated() ? this.getAuthentication().authorities : [];
                },
                getAuthentication: function () {
                    return $rootScope.authentication;
                },
                getUsername      : function () {
                    if (!this.isAuthenticated()) {
                        return this.getAuthentication().username;
                    }
                },
                isAuthenticated  : function () {
                    return this.getAuthentication().authenticated;
                }
            };
        });
    }
);