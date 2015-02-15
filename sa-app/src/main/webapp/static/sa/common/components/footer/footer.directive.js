define(
    [
        './footer.module'
    ],
    function footerDirective(module) {
        'use strict';

        return module.directive('sgFooter', function breadcrumbDirective() {
            return {
                restrict   : 'E',
                templateUrl: 'common/components/footer/footer.tpl.html'
            }
        });
    }
);