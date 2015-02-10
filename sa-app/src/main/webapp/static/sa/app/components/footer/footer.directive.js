define(
    [
        './footer.module',
        'less!./footer'
    ],
    function footerDirective(module) {
        'use strict';

        return module.directive('sgFooter', function breadcrumbDirective() {
            return {
                restrict   : 'E',
                transclude : true,
                templateUrl: 'app/components/footer/footer.tpl.html'
            }
        });
    }
);