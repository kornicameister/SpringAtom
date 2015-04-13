angular.module('sg.common.resources')
    .constant('CARS_RESOURCE_END_POINT', 'cars')
    .factory('CarResourceFactory', function (Restangular) {
        function configProvider(configurer) {

        }

        return Restangular.withConfig(configProvider)
    })
    .factory('CarResource', function (CarResourceFactory, CARS_RESOURCE_END_POINT) {
        return {
            findAll: findAll,
            findOne: findOne,
            query  : query
        };

        function query(query) {
            return CarResourceFactory.all(CARS_RESOURCE_END_POINT).getList(query);
        }

        function findOne(id) {
            return CarResourceFactory.one(CARS_RESOURCE_END_POINT, id).get()
        }

        function findAll() {
            return CarResourceFactory.all(CARS_RESOURCE_END_POINT).getList()
        }
    });