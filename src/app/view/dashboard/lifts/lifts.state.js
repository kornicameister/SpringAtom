angular.module('sg.app.view.dashboard.lifts').config(['$stateHelperProvider', function ($stateHelperProvider) {
    $stateHelperProvider.state({
        name             : 'sg.dashboard.lifts',
        iconClass        : 'fa-arrow-up',
        url              : '/lifts',
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
                return $translate('sg.dashboard.lifts')
            }]
        },
        views            : {
            'dashboard': {
                template: 'Lifts'
            }
        }
    })
}]);