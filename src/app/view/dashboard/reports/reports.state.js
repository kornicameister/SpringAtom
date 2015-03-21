angular.module('sg.app.view.dashboard.reports').config(['$stateHelperProvider', function ($stateHelperProvider) {
    $stateHelperProvider.state({
        name             : 'sg.dashboard.reports',
        url              : '/reports',
        navigator        : [
            'sg.dashboard.calendar',
            'sg.dashboard.cars',
            'sg.dashboard.clients',
            'sg.dashboard.mechanics',
            'sg.dashboard.lifts',
            'sg.dashboard.reports'
        ],
        resolve          : {
            label: ['$translate', function ($translate) {
                return $translate('sg.dashboard.reports')
            }]
        },
        deepStateRedirect: true,
        sticky           : true,
        views            : {
            'reports': {
                template: 'Reports'
            }
        }
    })
}]);