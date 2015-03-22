angular
    .module('sg.app.view.dashboard.cars')
    .controller('CarCartController', function ($scope) {
        var cart = this;

        cart.car = $scope.cart.car;
    });