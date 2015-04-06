angular
    .module('sg.app.view.dashboard.cars')
    .config(function ($stateHelperProvider, $urlRouterProvider) {
        $stateHelperProvider.state({
            name     : 'sg.dashboard.cars',
            iconClass: 'fa-cab',
            url      : '/cars',
            abstract : true,
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
                    return $translate('sg.dashboard.cars')
                }],
                cars : ['CarResource', function (CarResource) {
                    return CarResource.getList()
                }]
            },
            views    : {
                'dashboard'       : {
                    templateUrl : 'app/view/dashboard/cars/cars.tpl.html',
                    controllerAs: 'vm',
                    controller  : 'CarsController'
                },
                'carDashboardView': {}
            }
        })
    }
);