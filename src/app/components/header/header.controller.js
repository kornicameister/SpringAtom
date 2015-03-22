(function (module) {

    module.controller('HeaderController',
        ['$scope', '$interval', '$stateHelper', 'authenticationPopup', 'sgCallbacks', 'loggerFactory', ctrl]
    );

    function AuthenticationListeners() {
    }

    function ctrl($scope, $interval, $stateHelper, authenticationPopup, sgCallbacks, loggerFactory) {

        AuthenticationListeners.prototype.onLogin = function (event, authentication) {
            vm.authentication = authentication;
            if (notificationPromise || !vm.authentication.authenticated) {
                return;
            }
            notificationPromise = $interval(function () {

            }, 10000);
        };
        AuthenticationListeners.prototype.onLogout = function (event, authentication) {
            vm.authentication = authentication;
            if (notificationPromise) {
                $interval.cancel(notificationPromise);
                notificationPromise = undefined;
            }
        };

        var vm = this,
            logger = loggerFactory('HeaderController'),
            listeners = [],
            notificationPromise,
            authenticationListeners = new AuthenticationListeners();

        // API
        vm.authentication = {
            authenticated: false,
            username     : undefined
        };
        vm.stateTitle = '';
        vm.openLoginDialog = sgCallbacks.cancelEvent(openLoginDialog.bind(vm));
        vm.openLogoutDialog = sgCallbacks.cancelEvent(openLogoutDialog.bind(vm));

        // listeners
        listeners.push($scope.$on('$authentication.login', authenticationListeners.onLogin.bind(vm)));
        listeners.push($scope.$on('$authentication.logout', authenticationListeners.onLogout.bind(vm)));
        listeners.push($scope.$on('$stateChangeSuccess', sgCallbacks.skipAbstractState(onStateChange.bind(vm))));
        listeners.push($scope.$on('$destroy', destroy.bind(vm)));

        // initialize
        getStateLabel();

        function onStateChange(event, state) {
            getStateLabel(state);
        }

        function openLoginDialog() {
            authenticationPopup.openLoginPopup();
        }

        function openLogoutDialog() {
            authenticationPopup.openLogoutPopup();
        }

        function getStateLabel(state) {
            $stateHelper.getStateLabel(state).then(function (label) {
                logger.debug(_.format('Retrieved label {l}', {l: label}));
                vm.stateTitle = label;
            })
        }

        function destroy() {
            if (notificationPromise) {
                $interval.cancel(notificationPromise);
            }
            logger.debug(_.format('Destroying {d} listeners', {d: listeners.length}));
            _.forEachRight(listeners, function (lst) {
                lst();
            });
        }
    }
}(angular.module('sg.app.components.header')));