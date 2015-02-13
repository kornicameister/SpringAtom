define(
    [
        'lodash',
        './authentication.popup.module',
        './login/login.popup.controller'
    ],
    function (_, module) {
        "use strict";

        return module.factory('authenticationPopup', ['$modal', authenticationPopup]);

        // authentication popups configures as sticky states

        function authenticationPopup($modal) {
            var service = {};

            service.openLoginPopup = openLoginPopup;
            service.openLogoutPopup = openLogoutPopup;
            service.openForgotPasswordPopup = openForgotPasswordPopup;
            
            function openLoginPopup(cfg){
                cfg = _.defaults({
                    templateUrl  : 'app/popups/authentication/login/login.popup.tpl.html',
                    controller   : 'LoginPopupController',
                    controllerAs : 'vm',
                    size         : 'lg'
                }, cfg || {});
                return $modal.open(cfg)
            }
            
            function openLogoutPopup(cfg){
                return $modal.open({
                    
                })
            }
            
            function openForgotPasswordPopup(cfg){
                return $modal.open({
                    
                })
            }

            return service;
        }
    }
);