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
            vm.activeCartId = undefined; // control which cart is currently active

            // functions
            vm.selectCart = sgCallbacks.cancelEvent(selectCart.bind(vm));

            function selectCart(car) {
                if (!car) {
                    return;
                }
                var oldActiveCarId = vm.activeCartId;
                if (car.id === vm.activeCartId) {
                    vm.activeCartId = undefined
                } else {
                    vm.activeCartId = car.id;
                }
                logger.info('Active cart changed from=' + oldActiveCarId + ' to=' + vm.activeCartId);
            }

        }
    ]);