define(
    [
        'lodash',
        './header.module',
        // angular dependencies
        'common/state/state.module'
    ],
    function headerController(_, module) {
        "use strict";

        return module.controller('HeaderController', ['$scope', '$interval', '$stateBuilder', ctrl]);

        function AuthenticationListeners() {
        }

        function onClickWrapper(func) {
            func();
            return false;
        }

        function ctrl($scope, $interval, $stateBuilder) {

            AuthenticationListeners.prototype.onLogin = function () {
                if (notificationPromise) {
                    return;
                }
                notificationPromise = $interval(function () {

                }, 10000);
            };
            AuthenticationListeners.prototype.onLogout = function () {
                if (notificationPromise) {
                    $interval.cancel(notificationPromise);
                    notificationPromise = undefined;
                }
            };

            var vm = this,
                notificationPromise,
                authenticationListeners = new AuthenticationListeners();

            // API
            vm.authentication = {
                authenticated: false,
                username     : undefined
            };
            vm.stateTitle = '';
            vm.openLoginDialog = _.wrap(openLoginDialog.bind(vm), onClickWrapper);
            vm.openLogoutDialog = _.wrap(openLogoutDialog.bind(vm), onClickWrapper);

            // listeners
            $scope.$on('$authentication.login', authenticationListeners.onLogin.bind(vm));
            $scope.$on('$authentication.logout', authenticationListeners.onLogout.bind(vm));
            $scope.$on('$destroy', destroy.bind(vm));

            // initialize
            initialize();

            function openLoginDialog() {

            }

            function openLogoutDialog() {

            }

            function initialize() {
                $stateBuilder.getStateLabel().then(function (label) {
                    vm.stateTitle = label;
                })
            }

            function destroy() {
                if (notificationPromise) {
                    $interval.cancel(notificationPromise);
                }
            }
        }
    }
);