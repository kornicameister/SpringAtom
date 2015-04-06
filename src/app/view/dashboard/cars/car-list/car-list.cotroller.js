angular
    .module('sg.app.view.dashboard.cars')
    .controller('CarListController', [
        '$scope',
        'sgCallbacks',
        'cars',
        'loggerFactory',
        function ($scope, sgCallbacks, cars, loggerFactory) {
            var vm = this,
                logger = loggerFactory('CarListController');

            // fields
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