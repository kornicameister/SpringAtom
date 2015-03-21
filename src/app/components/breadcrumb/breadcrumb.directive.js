(function (module) {
    module.directive('sgBreadcrumb', ['ControllerBoundDirective', breadcrumbDirective]);

    function breadcrumbDirective(ControllerBoundDirective) {
        return new ControllerBoundDirective({
            restrict   : 'E',
            controller : 'BreadcrumbController',
            templateUrl: 'app/components/breadcrumb/breadcrumb.tpl.html'
        })
    }

}(angular.module('sg.app.components.breadcrumb')));