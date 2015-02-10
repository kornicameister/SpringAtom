define(
    [
        '../popup.module'
    ],
    function (module) {
        "use strict";

        return module.factory('authenticationPopup', ['$modal', authenticationPopup])
            .config(['$stickyStateProvider', function ($stickyStateProvider) {

            }]);

        // authentication popups configures as sticky states

        function authenticationPopup($modal) {
            var service = {};

            service.openLoginPopup = openLoginPopup;
            service.openLogoutPopup = openLogoutPopup;
            service.openForgotPasswordPopup = openForgotPasswordPopup;

            return service;
        }
    }
);