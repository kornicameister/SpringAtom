angular.module('sg.app.view.dashboard.cars').config(['$stateHelperProvider', function ($stateHelperProvider) {
    $stateHelperProvider.state({
        name             : 'sg.dashboard.cars',
        url              : '/cars',
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
                return $translate('sg.dashboard.cars')
            }],
            cars : ['CarService', function (CarService) {
                return CarService.findAll();
            }]
        },
        deepStateRedirect: true,
        sticky           : true,
        views            : {
            'cars': {
                templateUrl : 'app/view/dashboard/cars/cars.tpl.html',
                controllerAs: 'vm',
                controller  : 'CarsController'
            }
        }
    })
}]);