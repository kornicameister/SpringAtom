angular.module('sg.app.view.dashboard.cars').config(['$stateHelperProvider', function ($stateHelperProvider) {
    $stateHelperProvider.state({
        name   : 'sg.dashboard.cars.list',
        url    : '/list',
        resolve: {
            label: ['$translate', function ($translate) {
                return $translate('DASHBOARD_ALL_CARS')
            }]
        },
        views  : {
            'carDashboardView': {
                controller  : 'CarListController',
                controllerAs: 'vm',
                templateUrl : 'app/view/dashboard/cars/car-list/car-list.tpl.html'
            }
        }
    })
}]);