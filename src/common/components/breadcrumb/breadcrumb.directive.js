define(
    [
        './breadcrumb.module',
        './breadcrumb.controller',
        '../../directive/controllerBoundDirective'
    ],
    function breadcrumbDirective(module, controller, ControllerBoundDirective) {
        "use strict";

        return module.directive('sgBreadcrumb', function breadcrumbDirective() {
            return new ControllerBoundDirective({
                restrict   : 'E',
                controller : 'BreadcrumbController',
                templateUrl: 'common/components/breadcrumb/breadcrumb.tpl.html'
            })
        });
    }
);