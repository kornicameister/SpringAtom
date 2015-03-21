angular
    .module('sg.app.components.navigation')
    .directive('sgNavigation', ['ControllerBoundDirective', function (ControllerBoundDirective) {
        return new ControllerBoundDirective({
            restrict   : 'E',
            controller : 'NavigationController',
            templateUrl: 'app/components/navigation/navigation.tpl.html'
        })
    }]);