angular.module('sg.app.popups.authentication').controller('LoginPopupController', [
    '$modalInstance',
    'securityService',
    'sgCallbacks',
    function ($modalInstance, securityService, sgCallbacks) {
        var vm = this;

        vm.username = '';
        vm.password = '';
        vm.rememberMe = false;

        vm.forgotPassword = sgCallbacks.cancelEvent(forgotPassword.bind(vm));
        vm.register = sgCallbacks.cancelEvent(register.bind(vm));
        vm.login = sgCallbacks.cancelEvent(login.bind(vm));
        vm.cancel = sgCallbacks.cancelEvent(cancel.bind(vm));

        function forgotPassword() {
            $modalInstance.close('forgot_password');
        }

        function register() {
            $modalInstance.close('register');
        }

        function login() {
            var credentials = {
                    email   : vm.username,
                    password: vm.password
                },
                opts = {
                    rememberMe: vm.rememberMe
                };
            securityService.login(credentials, opts).then(function () {
                "use strict";
                $modalInstance.close('login');
            });
        }

        function cancel() {
            $modalInstance.dismiss('cancel');
        }
    }
]);

