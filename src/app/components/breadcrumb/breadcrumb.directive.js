define(
    'app/components/breadcrumb/breadcrumb.directive',
    [
        'app/components/breadcrumb/breadcrumb.module',
        'app/components/breadcrumb/breadcrumb.controller',
        'common/directive/controllerBoundDirective'
    ],
    function breadcrumbDirective(module, controller, ControllerBoundDirective) {
        "use strict";

        return module.directive('sgBreadcrumb', function breadcrumbDirective() {
            return new ControllerBoundDirective({
                restrict   : 'E',
                controller : 'BreadcrumbController',
                templateUrl: 'app/components/breadcrumb/breadcrumb.tpl.html'
            })
        });
    }
);