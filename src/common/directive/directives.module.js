define(
    [
        'angular',
        'lodash',
        './controllerBoundDirective'
    ],
    function directiveModule(angular, _, controllerBoundDirective) {
        "use strict";
        var module = angular.module('sg.directives', []);

        module.factory('controllerBoundDirective', _.constant(controllerBoundDirective));

        return module;
    }
);