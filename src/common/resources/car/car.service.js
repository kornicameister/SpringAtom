angular
    .module('sg.common.resources.car')
    .factory('CarService', ['CarFactory', 'sgDataService', function (CarFactory, sgDataService) {

        var single = new CarFactory(sgDataService.firebase('/cars')),
            array = sgDataService.firebase('/cars');


        return {
            findAll: function () {
                // mock code
                var tmp = [];
                for (var i = 0; i < 20; i++) {
                    tmp.push({
                        id          : i,
                        licencePlate: 'E' + i + 'KRZ',
                        vinNumber   : '3N1CB51D82L635711',
                        owner       : 'Michał ' + i
                    })
                }
                return tmp;
            }
        };

    }]);