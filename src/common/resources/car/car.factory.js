angular
    .module('sg.common.resources.car')
    .factory('CarFactory', ['$firebaseObject', function ($firebaseObject) {
        var carFactory = {};

        carFactory.getOwner = getOwner.bind(carFactory);

        return $firebaseObject.$extend(carFactory);

        function getOwner() {

        }
    }]
);