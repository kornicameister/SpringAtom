(function () {
    angular.module('sg.common.security')
        .service('securityService', ['$rootScope', '$q', 'securityAuthentication', securityService]);

    function securityService($rootScope, $q, securityAuthentication) {
        var service = this,
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

        function getAuth() {

        }

        function login(credentials, opts) {
            opts = _.defaults(opts, {
                rememberMe: false
            });
            return $q(function (resolve) {
                resolve()
            });
        }

        function logout() {
            return $q(function (resolve) {
                resolve();
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
}());