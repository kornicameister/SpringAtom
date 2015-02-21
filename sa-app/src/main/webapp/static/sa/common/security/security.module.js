define(
    [
        'angular',
        './security.provider',
        './security.service',
        './security.authentication',
        // deps
        '../state/state.module',
        '../resources/user/user.all'
    ],
    function securityModule(angular, securityProvider, securityService, securityAuthentication) {
        "use strict";

        /**
         * @ngdoc module
         *
         * @name sg.security
         * @module sg.security
         * @namespace sg
         * @description
         *
         * <b>sg.security</b> is a global module that contain all required components
         * to provide an information about application security state.
         * Supports serving security services for states, actions, hyperlinks etc.
         */
        return angular.module('sg.security', ['sg.state', 'sg.resources.user'])
            .factory('securityAuthentication', securityAuthentication)
            .service('securityService', securityService)
            .provider('$security', securityProvider)
            .run(['$security', '$rootScope', '$state', setStateSecurity]);

        function setStateSecurity($security, $rootScope, $state) {
            if ($security.isStateSecurityEnabled()) {
                $rootScope.$on('$stateChangeStart', function (event,
                                                              toState,
                                                              toStateParams,
                                                              fromState,
                                                              fromStateParams) {

                    if (toState.security) {
                        event.preventDefault();
                    }

                });
            }
        }
    }
);