angular
    .module('sg.app.view.dashboard.cars')
    .controller('CarCartController', function ($scope, sgCallbacks) {
        var cart = this;

        cart.car = _.clone($scope.cart.car);
        cart.active = false;
        cart.toggleActive = sgCallbacks.cancelEvent(toggleActive);

        function toggleActive() {
            cart.active = !cart.active;
            $scope.cart.selectCart();
        }
    });