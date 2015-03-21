(function (module) {
    module.directive('sgFooter', function breadcrumbDirective() {
        return {
            restrict   : 'E',
            templateUrl: 'app/components/footer/footer.tpl.html'
        }
    });
}(angular.module('sg.app.components.footer')));