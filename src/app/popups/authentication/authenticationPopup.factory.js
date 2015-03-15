define(
    'app/popups/authentication/authenticationPopup.factory',
    [
        'lodash',
        'app/popups/authentication/authentication.popup.module',
        'app/popups/authentication/login/login.popup.controller',
        'app/popups/authentication/logout/logout.popup.controller'
    ],
    function (_, module) {
        "use strict";

        return module.factory('authenticationPopup', ['$modal', authenticationPopup]);

        function authenticationPopup($modal) {
            var service = {};

            /**
             * Open login popup that allows user to provide its username and password.
             * @type {openLoginPopup}
             */
            service.openLoginPopup = openLoginPopup;
            /**
             * Opens logout popup to cancel user session
             * @type {openLogoutPopup}
             */
            service.openLogoutPopup = openLogoutPopup;

            function openLoginPopup(cfg) {
                cfg = _.defaults({
                    templateUrl : 'app/popups/authentication/login/login.popup.tpl.html',
                    controller  : 'LoginPopupController',
                    controllerAs: 'vm',
                    size        : 'lg'
                }, cfg || {});
                return $modal.open(cfg)
            }

            function openLogoutPopup(cfg) {
                cfg = _.defaults({
                    templateUrl : 'app/popups/authentication/logout/logout.popup.tpl.html',
                    controller  : 'LogoutPopupController',
                    controllerAs: 'vm',
                    size        : 'sm'
                }, cfg || {});
                return $modal.open(cfg)
            }

            return service;
        }
    }
);