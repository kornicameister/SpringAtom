angular
    .module('sg.app.view.dashboard.cars')
    .controller('CarsController', [
        '$scope',
        'CarService',
        function ($scope, CarService) {
            var vm = this;

            vm.columnClass = 'col-md-4';
            vm.cars = CarService.findAll();

        }
    ]);