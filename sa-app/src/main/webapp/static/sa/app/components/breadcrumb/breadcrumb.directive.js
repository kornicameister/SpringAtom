define(
    [
        './breadcrumb.module',
        './breadcrumb.controller',
        'less!./breadcrumb'
    ],
    function breadcrumbDirective(module) {
        "use strict";

        return module.directive('sgBreadcrumb', function breadcrumbDirective() {
            return {
                restrict        : 'E',
                controller      : 'BreadcrumbController',
                controllerAs    : 'vm',
                bindToController: true,
                templateUrl     : 'app/components/breadcrumb/breadcrumb.tpl.html'
            }
        });
    }
);