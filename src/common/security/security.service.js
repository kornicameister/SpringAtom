define(
    [
        'lodash'
    ],
    function (_) {
        "use strict";
        return ['$fireData', '$rootScope', '$q', 'securityAuthentication', securityService];

        function securityService($fireData, $rootScope, $q, securityAuthentication) {
            var authFire = $fireData.auth(),
                service = this,
                authentication;

            /**
             * Login into the application using email and password
             * @param {object} containing email, password
             * @return promise
             * @type {login}
             */
            service.login = login;
            /**
             * Logs out current user from the application
             * @return promise
             * @type {logout}
             */
            service.logout = logout;
            /**
             * Resolves with current authentication information or rejects
             * if user not authenticated
             * @return promise
             * @type {getAuth}
             */
            service.getAuth = getAuth;

            authFire.$onAuth(notifyOnAuthChange);
            notifyOnAuthChange(authFire.$getAuth());

            function getAuth() {
                return authFire.$requireAuth().then(
                    function (auth) {
                        return securityAuthentication(auth)
                    },
                    function () {
                        return securityAuthentication();
                    }
                );
            }

            function login(credentials, opts) {
                opts = _.defaults(opts, {
                    rememberMe: false
                });
                return authFire.$authWithPassword(credentials, opts);
            }

            function logout() {
                return $q(function (resolve) {
                    resolve(authFire.$unauth());
                })
            }

            function notifyOnAuthChange(auth) {
                if (!_.isNull(auth)) {
                    // authenticated
                    $rootScope.$broadcast('$authentication.login', securityAuthentication(auth));
                    authentication = auth;
                } else {
                    // authentication lost
                    if (authentication) {
                        $rootScope.$broadcast('$authentication.logout', securityAuthentication(auth));
                        authentication = undefined;
                    }
                }
            }
        }
    }
);