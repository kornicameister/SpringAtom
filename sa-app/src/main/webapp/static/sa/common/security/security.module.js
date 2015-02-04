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
        './security.provider'
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
        return angular.module('sg.security', [])
            .provider('$security', securityProvider)
            .run(['$security', '$rootScope', applySecurityConfiguration]);

        function applySecurityConfiguration($security, $rootScope) {
            if ($security.isStateSecurityEnabled()) {
                $rootScope.$on('$stateChangeStart', function (event,
                                                              toState,
                                                              toStateParams,
                                                              fromState,
                                                              fromStateParams) {

                    if (!_.isUndefined(toState.security) && _.isObject(toState.security)) {
                        // security defined in state, let's wrap it up
                        event.preventDefault = true;
                    }

                });
            }
        }
    }
);