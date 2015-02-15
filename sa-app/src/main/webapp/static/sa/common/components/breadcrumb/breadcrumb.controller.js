define(
    [
        './breadcrumb.module',
        './breadcrumb.service'
    ],
    function breadcrumbController(module) {
        "use strict";

        return module.controller('BreadcrumbController', ['$scope', 'breadcrumbService', '$stateBuilder', ctrl]);

        function ctrl($scope, breadcrumbService, $stateBuilder) {
            var vm = this;

            // API of controller
            vm.crumbs = [];

            // listeners
            $scope.$on('$destroy', destroy);
            $scope.$on('$stateChangeSuccess', onStateChangeSuccess);

            initialize();

            function destroy() {
                delete vm.crumbs;
            }

            function initialize() {
                $stateBuilder.getCurrentState()
                    .then(function (state) {
                        breadcrumbService
                            .getBreadcrumbs(state)
                            .then(function (crumbs) {
                                vm.crumbs = crumbs;
                            })
                    })
            }

            function onStateChangeSuccess(event, toState) {
                breadcrumbService.getBreadcrumbs(toState).then(function (crumbs) {
                    vm.crumbs = crumbs;
                });
                return true;
            }
        }
    }
);