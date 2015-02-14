define(
    [
        './navigation.module',
        'less!./navigation'
    ],
    function navigationDirective(module) {
        'use strict';

        return module.directive('sgNavigation', function breadcrumbDirective() {
            return {
                restrict   : 'E',
                scope: true,
                templateUrl: 'app/components/navigation/navigation.tpl.html'
            }
        });
    }
);