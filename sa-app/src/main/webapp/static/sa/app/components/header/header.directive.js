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
                controller      : 'HeaderController',
                controllerAs    : 'vm',
                bindToController: true,
                scope: true,
                templateUrl     : 'app/components/header/header.tpl.html'
            }
        });
    }
);