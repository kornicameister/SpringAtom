/**
* Created with SpringAtom.
* User: kornicameister
* Date: 2015-02-04
* Time: 11:09 AM
* To change this template use Tools | Templates.
*/
define(
    [
        'lodash'
    ],
    function(_) {
        "use strict";
        return function(){
            // private
            var self = this,
                authenticateOnRun = false,
                stateSecurityEnabled = false;

            /**
             * Enables/Disables state security
             *
             * @param {@boolean} enable, if undefined assumed to be true
             */
            self.enableStateSecurity = enableStateSecurity;
            /**
             * Enables/disable authentication on run phase.
             * If set to true, authentication will be verified on <b>run</b> phase of the 
             * application.
             * 
             * @param {@boolean} enable, if undefined assumed to be true
             */
            self.enableAuthenticateOnRun = enableAuthenticateOnRun;

            // service available
            self.$get = $get;

            function enableStateSecurity(enable){
                stateSecurityEnabled = enable || true;
                return self;
            }
            
            function enableAuthenticateOnRun(enable){
                authenticateOnRun = enable || true;
                return self;
            }

            function $get(){
                var service = {};

                service.isStateSecurityEnabled = _.constant(stateSecurityEnabled);
                service.isOnRunAuthenticationEnabled = _.constant(authenticateOnRun);

                return service;
            }
        }
    }
);