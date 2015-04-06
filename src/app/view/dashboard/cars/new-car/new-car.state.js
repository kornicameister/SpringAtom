angular.module('sg.app.view.dashboard.cars').config(['$stateHelperProvider', function ($stateHelperProvider) {
    $stateHelperProvider.state({
        name   : 'sg.dashboard.cars.new',
        url    : '/new',
        resolve: {
            label: ['$translate', function ($translate) {
                return $translate('DASHBOARD_NEW_CAR')
            }]
        },
        views  : {
            'carDashboardView': {
                templateUrl: 'app/view/dashboard/cars/new-car/new-car.tpl.html'
            }
        }
    })
}]);