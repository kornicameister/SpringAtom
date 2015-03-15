define(
    'app/components/footer/footer.directive',
    [
        'app/components/footer/footer.module'
    ],
    function footerDirective(module) {
        'use strict';

        return module.directive('sgFooter', function breadcrumbDirective() {
            return {
                restrict   : 'E',
                templateUrl: 'app/components/footer/footer.tpl.html'
            }
        });
    }
);