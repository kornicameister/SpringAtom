angular.module('sg.app.popups.authentication').controller('LogoutPopupController', [
    '$modalInstance',
    'securityService',
    'sgCallbacks',
    function ($modalInstance, securityService, sgCallbacks) {
        var vm = this;

        vm.logout = sgCallbacks.cancelEvent(logout.bind(vm));
        vm.cancel = sgCallbacks.cancelEvent(cancel.bind(vm));

        function logout() {
            securityService.logout().then(function () {
                $modalInstance.close('logout');
            })
        }

        function cancel() {
            $modalInstance.dismiss('cancel');
        }
    }
]);