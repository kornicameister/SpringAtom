angular.module('sg.app.view.dashboard.calendar').config(['$stateHelperProvider', function ($stateHelperProvider) {
    $stateHelperProvider.state({
        name             : 'sg.dashboard.calendar',
        url              : '/calendar',
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
                return $translate('sg.dashboard.calendar')
            }]
        },
        deepStateRedirect: true,
        sticky           : true,
        views            : {
            'calendar': {
                template: 'Calendar'
            }
        }
    })
}]);