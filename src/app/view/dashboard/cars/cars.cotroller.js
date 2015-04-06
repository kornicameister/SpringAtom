angular
    .module('sg.app.view.dashboard.cars')
    .controller('CarsController', [
        '$scope',
        '$state',
        function ($scope, $state) {
            var vm = this;

            // functions
            vm.onSearch = onCarSearch.bind(vm);
            vm.goToNewCar = getNavFunction('new-car').bind(vm);
            vm.goToCarList = getNavFunction('car-list').bind(vm);

            function getNavFunction(action) {
                switch (action) {
                    case 'new-car':
                        return function () {
                            $state.go('sg.dashboard.cars.new')
                        };
                    case 'car-list':
                        return function () {
                            $state.go('sg.dashboard.cars.list')
                        }
                }
            }

            function onCarSearch(phrase) {
                console.log(phrase)
            }

        }
    ]);