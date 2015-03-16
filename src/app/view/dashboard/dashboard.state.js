define(
    'app/view/dashboard/dashboard.state',
    [
        'app/view/dashboard/dashboard.module',
        'app/view/dashboard/dashboard.controller',
        'app/view/dashboard/dashboardTabs.service',
        'common/state/state.stateHelperProvider'
    ],
    function dashboardState(module) {
        "use strict";
        return module.config(['$stateHelperProvider', state]);

        function state($stateHelperProvider) {
            $stateHelperProvider.state({
                name     : 'sg.dashboard',
                url      : '/dashboard',
                navigator: [
                    'sg.dashboard.calendar',
                    'sg.dashboard.cars',
                    'sg.dashboard.clients',
                    'sg.dashboard.mechanics',
                    'sg.dashboard.lifts',
                    'sg.dashboard.reports'
                ],
                resolve  : {
                    label: ['$translate', function ($translate) {
                        return $translate('sg.dashboard')
                    }],
                    tabs : ['$dashboardTabService', function ($dashboardTabService) {
                        return $dashboardTabService.getDashboardTabs();
                    }]
                },
                views    : {
                    'content@': {
                        controller  : 'DashboardController',
                        controllerAs: 'vm',
                        templateUrl : 'app/dashboard/dashboard.tpl.html'
                    }
                }
            })
        }
    }
);