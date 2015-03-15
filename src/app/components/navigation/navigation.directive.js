define(
    'app/components/navigation/navigation.directive',
    [
        'app/components/navigation/navigation.module',
        'app/components/navigation/navigation.controller',
        'common/directive/controllerBoundDirective'
    ],
    function navigationDirective(module, controller, ControllerBoundDirective) {
        'use strict';

        return module.directive('sgNavigation', function breadcrumbDirective() {
            return new ControllerBoundDirective({
                restrict   : 'E',
                controller : 'NavigationController',
                templateUrl: 'app/components/navigation/navigation.tpl.html'
            })
        });
    }
);