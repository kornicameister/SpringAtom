define(
    [
        './breadcrumb.module',
        './breadcrumb.controller'
    ],
    function breadcrumbDirective(module) {
        "use strict";

        return module.directive('sgBreadcrumb', function breadcrumbDirective() {
            return {
                restrict        : 'E',
                controller      : 'BreadcrumbController',
                controllerAs    : 'vm',
                bindToController: true,
                scope      : true,
                templateUrl: 'common/components/breadcrumb/breadcrumb.tpl.html'
            }
        });
    }
);