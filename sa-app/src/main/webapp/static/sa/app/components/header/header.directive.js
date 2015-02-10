define(
    [
        './header.module',
        './header.controller',
        'less!./header'
    ],
    function headerDirective(module) {
        'use strict';

        return module.directive('sgHeader', function breadcrumbDirective() {
            return {
                restrict        : 'E',
                scope           : {},
                controller      : 'HeaderController',
                controllerAs    : 'vm',
                bindToController: true,
                transclude      : true,
                templateUrl     : 'app/components/header/header.tpl.html'
            }
        });
    }
);