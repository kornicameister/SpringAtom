/**
 * Created with SpringAtom.
 * User: kornicameister
 * Date: 2015-02-04
 * Time: 11:05 AM
 * To change this template use Tools | Templates.
 */
define(
    [
        'angular',
        './security.provider',
        // deps
        '../state/state.module'
    ],
    function securityModule(angular, securityProvider) {
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
        return angular.module('sg.security', ['sg.state'])
            .provider('$security', securityProvider)
            .run(['$security', '$rootScope', '$state', setStateSecurity])
            .run(['$security', checkAuthentication]);

        function checkAuthentication($security){
            if($security.isOnRunAuthenticationEnabled()){

            }
        }

        function setStateSecurity($security, $rootScope, $state) {
            if ($security.isStateSecurityEnabled()) {
                $rootScope.$on('$stateChangeStart', function (event,
                                                              toState,
                                                              toStateParams,
                                                              fromState,
                                                              fromStateParams) {

                    if (toState.security && _.isObject(toState.security)) {
                        event.preventDefault();
                    }

                });
            }
        }
    }
);