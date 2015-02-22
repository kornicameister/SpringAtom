define(
    [
        'lodash',
        './dashboard.module'
    ],
    function dashboardController(_, module) {
        "use strict";
        module.controller('DashboardController', ['$scope', '$state', 'tabs', ctrl]);

        function ctrl($scope, $state, tabs) {
            var vm = this,
                listeners = [];

            vm.tabs = _.forEach(tabs || [], function (tab) {
                tab.active = $state.includes(tab.state)
            });

            listeners.push($scope.$on('$destroy', onDestroy.bind(vm)));
            listeners.push($scope.$on('$stateChangeSuccess', onStateChangeSuccess.bind(vm)));

            initialize();

            function onStateChangeSuccess() {
                vm.tabs = _.forEach(vm.tabs, function (tab) {
                    tab.active = $state.includes(tab.state)
                });
            }

            function onDestroy() {
                _.forEach(listeners, function (lst) {
                    lst();
                })
            }

            function initialize() {
                if (vm.tabs.length) {
                    $state.go(vm.tabs[0].state)
                }
            }
        }
    }
);