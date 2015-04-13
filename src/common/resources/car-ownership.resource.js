angular.module('sg.common.resources')
    .constant('CAR_OWNERSHIP_RESOURCE_END_POINT', 'car-ownership')
    .factory('CarOwnershipResourceFactory', function (Restangular) {
        function configProvider(configurer) {

        }

        return Restangular.withConfig(configProvider)
    })
    .factory('CarOwnershipResource', function (CarOwnershipResourceFactory, CAR_OWNERSHIP_RESOURCE_END_POINT) {
        return {
            findAll: findAll,
            findOne: findOne,
            findCar: findCar,
            query  : query
        };

        function findCar(ownershipId) {
            return query({
                carRef: ownershipId
            })
        }

        function query(query) {
            return CarOwnershipResourceFactory.all(CAR_OWNERSHIP_RESOURCE_END_POINT).getList(query);
        }

        function findOne(id) {
            return CarOwnershipResourceFactory.one(CAR_OWNERSHIP_RESOURCE_END_POINT, id).get()
        }

        function findAll() {
            return CarOwnershipResourceFactory.all(CAR_OWNERSHIP_RESOURCE_END_POINT).getList()
        }
    });