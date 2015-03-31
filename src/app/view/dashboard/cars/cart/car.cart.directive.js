angular
    .module('sg.app.view.dashboard.cars')
    .directive('carCart', function () {
        return {
            restrict        : 'E',
            scope           : {
                car       : '=',            // ensure that any changes from upper scope will be reflected in cart,
                selectCart: '&'             // access to the upper level to modify selection of carts
            },
            controller      : 'CarCartController',
            bindToController: true,
            controllerAs    : 'cart',
            templateUrl     : 'app/view/dashboard/cars/cart/car.cart.tpl.html'
        }
    });