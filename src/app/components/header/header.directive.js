(function (module) {
    module.directive('sgHeader', function breadcrumbDirective() {
        return {
            restrict        : 'E',
            controller      : 'HeaderController',
            controllerAs    : 'vm',
            bindToController: true,
            scope           : true,
            templateUrl     : 'app/components/header/header.tpl.html'
        }
    });
}(angular.module('sg.app.components.header')));