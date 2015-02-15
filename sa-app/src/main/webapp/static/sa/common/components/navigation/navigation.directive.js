define(
    [
        './navigation.module'
    ],
    function navigationDirective(module) {
        'use strict';

        return module.directive('sgNavigation', function breadcrumbDirective() {
            return {
                restrict   : 'E',
                scope      : true,
                templateUrl: 'common/components/navigation/navigation.tpl.html'
            }
        });
    }
);