define(
    [
        './breadcrumb.module',
        './breadcrumb.service'
    ],
    function breadcrumbController(module) {
        "use strict";

        return module.controller('BreadcrumbController', ['$scope', 'breadcrumbService', ctrl]);

        function ctrl($scope, breadcrumbService) {
            var vm = this;

            // API of controller
            vm.crumbs = [];

            // listeners
            $scope.$on('$destroy', onDestroy);

            // callbacks
            breadcrumbService.onBreadcrumbChange(function (crumbs) {
                if (!!crumbs) {
                    return;
                }
                vm.crumbs = crumbs;
            });

            function onDestroy() {
                delete vm.crumbs;
            }
        }
    }
);