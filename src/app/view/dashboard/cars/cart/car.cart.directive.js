angular
    .module('sg.app.view.dashboard.cars')
    .directive('carCart', function () {
        return {
            restrict        : 'E',
            scope           : {
                car         : '@',
                active      : '=',
                toggleActive: '&'
            },
            bindToController: {
                car         : '=',
                active      : '=',
                toggleActive: '&'
            },
            controllerAs    : 'cart',
            controller      : angular.noop,
            templateUrl     : 'app/view/dashboard/cars/cart/car.cart.tpl.html'
        }
    });