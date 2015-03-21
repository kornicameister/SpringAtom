(function (module) {
    module.controller('BreadcrumbController', ['$scope', 'breadcrumbService', '$stateHelper', ctrl]);

    function ctrl($scope, breadcrumbService, $stateHelper) {
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
            $stateHelper.getCurrentState()
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
}(angular.module('sg.app.components.breadcrumb')));