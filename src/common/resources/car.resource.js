angular.module('sg.common.resources')
    .factory('CarResource', function (Restangular) {
        return Restangular.service('cars');
    });