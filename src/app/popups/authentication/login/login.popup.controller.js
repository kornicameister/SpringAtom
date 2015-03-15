define(
    'app/popups/authentication/login/login.popup.controller',
    [
        'lodash',
        'common/callbacks',
        'app/popups/authentication/authentication.popup.module',
        'common/security/security.service'
    ],
    function loginPopupController(_, callbacks, module) {
        var cancelEventWrapper = callbacks.cancelEvent;

        return module.controller('LoginPopupController', ['$modalInstance', 'securityService', ctrl]);

        function ctrl($modalInstance, securityService) {
            var vm = this;

            vm.username = '';
            vm.password = '';
            vm.rememberMe = false;

            vm.forgotPassword = _.wrap(forgotPassword.bind(vm), cancelEventWrapper);
            vm.register = _.wrap(register.bind(vm), cancelEventWrapper);
            vm.login = _.wrap(login.bind(vm), cancelEventWrapper);
            vm.cancel = _.wrap(cancel.bind(vm), cancelEventWrapper);

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
    }
);