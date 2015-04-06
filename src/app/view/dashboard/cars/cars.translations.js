angular
    .module('sg.app.view.dashboard.cars')
    .config(function ($translateProvider) {
        $translateProvider.translations('pl', {
            DASHBOARD_NEW_CAR : 'Nowy samochód',
            DASHBOARD_ALL_CARS: 'Lista samochodów'
        });
    });