angular.module('sg.common.resources')
    .factory('CarOwnershipResource', function (Restangular) {
        return Restangular.service('cars');
    });