angular.module('sg.app.view.dashboard.mechanics').config(['$stateHelperProvider', function ($stateHelperProvider) {
    $stateHelperProvider.state({
        name             : 'sg.dashboard.mechanics',
        iconClass        : 'fa-wrench',
        url              : '/mechanics',
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
                return $translate('sg.dashboard.mechanics')
            }]
        },
        views            : {
            'dashboard': {
                template: 'Mechanics'
            }
        }
    })
}]);