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
    function (_) {
        "use strict";
        return function () {
            // private
            var self = this,
                otherwiseUrl,
                stateSecurityEnabled = false;

            /**
             * Enables/Disables state security
             *
             * @param {@boolean} enable, if undefined assumed to be true
             */
            self.enableStateSecurity = enableStateSecurity;
            /**
             * Provides an otherwise url to be routed to if access has been denied for certain state, action etc.
             *
             * @param {@string} otherwise url
             * @type {otherwise}
             */
            self.otherwise = otherwise;

            // service available
            self.$get = $get;

            function enableStateSecurity(enable) {
                stateSecurityEnabled = _.isUndefined(enable) ? true : enable;
                return self;
            }

            function otherwise(url) {
                otherwiseUrl = url;
                return self;
            }

            function $get() {
                var service = {};

                service.isStateSecurityEnabled = _.constant(stateSecurityEnabled);
                service.getOtherwiseUrl = _.constant(otherwiseUrl);

                return service;
            }
        }
    }
);