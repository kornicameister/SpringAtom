angular.module('sg.app.view.dashboard')
    .controller('DashboardController', function ($scope, $state, tabs) {
        var vm = this,
            listeners = [];

        vm.tabs = tabs || [];
        vm.dashboardTabActive = false;
        vm.dashboardClick = dashboardClick.bind(vm);

        listeners.push($scope.$on('$destroy', onDestroy.bind(vm)));
        listeners.push($scope.$on('$stateChangeSuccess', onStateChangeSuccess.bind(vm)));

        initialize();

        function initialize() {
            onStateChangeSuccess();
        }

        function onStateChangeSuccess() {
            var anyActive = false;
            vm.tabs = _.forEach(tabs || [], function (tab) {
                tab.active = $state.includes(tab.state);
                if (tab.active) {
                    anyActive = true;
                }
            });
            vm.dashboardTabActive = anyActive;
        }

        function dashboardClick(tab) {
            $state.go(tab.state);
            return false;
        }

        function onDestroy() {
            _.forEach(listeners, function (lst) {
                lst();
            })
        }

    }
);