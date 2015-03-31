angular
    .module('sg.app.view.dashboard.cars')
    .controller('CarsController', [
        '$scope',
        'sgCallbacks',
        'CarService',
        'cars',
        'loggerFactory',
        function ($scope, sgCallbacks, CarService, cars, loggerFactory) {
            var vm = this,
                logger = loggerFactory('CarsController');

            // fields
            vm.columnClass = 'col-md-4';
            vm.cars = cars || [];

            // functions
            vm.selectCart = sgCallbacks.cancelEvent(selectCart.bind(vm));

            function selectCart(car) {
                if (!car) {
                    return;
                }
                // todo remove it
                logger.info('Marking cart for car.id=' + car.id);
            }

        }
    ]);