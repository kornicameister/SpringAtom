define(
    'app/components/header/header.directive',
    [
        'app/components/header/header.module',
        'app/components/header/header.controller',
        'common/directive/controllerBoundDirective'
    ],
    function headerDirective(module, headerCtrl, ControllerBoundDirective) {
        'use strict';

        return module.directive('sgHeader', function breadcrumbDirective() {
            return new ControllerBoundDirective({
                restrict   : 'E',
                controller : 'HeaderController',
                templateUrl: 'app/components/header/header.tpl.html'
            })
        });
    }
);