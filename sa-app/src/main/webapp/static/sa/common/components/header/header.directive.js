define(
    [
        './header.module',
        './header.controller'
    ],
    function headerDirective(module) {
        'use strict';

        return module.directive('sgHeader', function breadcrumbDirective() {
            return {
                restrict        : 'E',
                controller      : 'HeaderController',
                controllerAs    : 'vm',
                bindToController: true,
                scope      : true,
                templateUrl: 'common/components/header/header.tpl.html'
            }
        });
    }
);