define(
    [
        './navigation.module',
        './navigation.controller',
        '../../directive/controllerBoundDirective'
    ],
    function navigationDirective(module, controller, ControllerBoundDirective) {
        'use strict';

        return module.directive('sgNavigation', function breadcrumbDirective() {
            return new ControllerBoundDirective({
                restrict   : 'E',
                controller: 'NavigationController',
                templateUrl: 'common/components/navigation/navigation.tpl.html'
            })
        });
    }
);