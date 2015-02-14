define(
    [
        'lodash',
        'common/callbacks',
        '../authentication.popup.module'
    ],
    function loginPopupController(_, callbacks, module) {
        var cancelEventWrapper = callbacks.cancelEvent;

        return module.controller('LoginPopupController', ['$modalInstance', ctrl]);

        function ctrl($modalInstance) {
            var vm = this;

            vm.username = '';
            vm.password = '';
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
                $modalInstance.close('login');
            }

            function cancel() {
                $modalInstance.dismiss('cancel');
            }
        }
    }
);