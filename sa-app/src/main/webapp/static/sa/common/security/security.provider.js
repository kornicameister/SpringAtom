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
                stateSecurityEnabled = false;
            
            // config available
            /**
             * Enables/Disables state security
             * 
             * @param {@boolean} enable, if undefined assumed to be true
             */
            self.enableStateSecurity = enableStateSecurity;
            
            // service available
            self.$get = $get;
            
            function enableStateSecurity(enable){
                stateSecurityEnabled = enable || true;
                return self;
            }
            
            function $get(){
                var service = {};
                
                service.isStateSecurityEnabled = isStateSecurityEnabled;
                
                return service;
                
                function isStateSecurityEnabled(){
                    return stateSecurityEnabled;
                }
            }
        }
    }
);